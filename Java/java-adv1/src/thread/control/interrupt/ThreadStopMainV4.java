package thread.control.interrupt;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * 인터럽트 - 시작4
 * : 인터럽트 목적 달성 후 인터럽트 상태를 정상으로 되돌리는 방법
 * 4) Thread.Interrupted()
 *  - 스레드가 인터럽트 상태라면 true 반환, 해당 스레드의 인터럽트 상태를 false로 변경
 *  - 스레드가 인터럽트 상태가 아니라면 false 반환, 해당 스레드의 인터럽트 상태 변경x
 *  => 스레드의 인터럽트 상태를 단순히 확인 : isInterrepted()
 *  => 스레드의 상태를 직접 체크해서 사용   : Thread.Interrupted()
 *  
 *    [     work] 작업 중
 *    [     work] 작업 중
 *    [     main] 작업 중단 지시 thread.interrupt()
 *    [     work] 작업 중
 *    [     main] work 스레드 인터럽트 상태1 = true
 *    [     work] work 스레드 인터럽트 상태2 = false
 *    [     work] 자원 정리 시도
 *    [     work] 자원 정리 완료
 *    [     work] 작업 종료
 */
public class ThreadStopMainV4 {
	
	public static void main(String[] args) {
		MyTask task = new MyTask();
		Thread thread = new Thread(task, "work");
		thread.start();
		
		sleep(100); // 0.1초동안 run()의 while 계속돈다
		log("작업 중단 지시 thread.interrupt()");
		thread.interrupt(); // interrupt = true
		log("work 스레드 인터럽트 상태1 = " + thread.isInterrupted());
	}
	
	static class MyTask implements Runnable {
		
		@Override
		public void  run() {
			while(!Thread.interrupted()) { // 인터럽트 상태 변경 o
				log("작업 중");
			}
			
			log("work 스레드 인터럽트 상태2 = " + Thread.currentThread().isInterrupted()); // interrupt가 여전히 true
			
			try {
				log("자원 정리 시도");
				Thread.sleep(1000);
				log("자원 정리 완료");
			} catch (InterruptedException e) {
				log("자원 정리 실패 - 자원 정리 중 인터럽트 발생");
				log("work 스레드 인터럽트 상태3 = " + Thread.currentThread().isInterrupted()); // 예외 발생으로 false가 됨
			}
			
			log("작업 종료");
		}
	}
}
