package thread.control.interrupt;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * 인터럽트 - 시작3
 * : while 시작 부분에서 인터럽트를 체크한다면 더 빨리 반응할 수 있지 않을까?
 * 3) Thread.currentThread().isInterrupted() 사용
 *  - main 스레드에서 인터럽트를 걸면 work 스레드 while문에서 조건이 false가 되면서 while문을 탈출한다.
 *  - 하지만 work 스레드의 인터럽트 상태가 true로 계속 유지된다는 문제가 있다.
 *    그 결과 인터럽트 ture 상태 도중 sleep() 같은 메서드를 만나면 인터럽트 예외가 발생한다.
 *  - 따라서 처음 인터럽트 목적을 달성했을 때 인터럽트 상태를 다시 정상으로 되돌려야 한다.
 *    
 *    [     work] 작업 중
 *    [     work] 작업 중
 *    [     main] 작업 중단 지시 thread.interrupt()
 *    [     main] work 스레드 인터럽트 상태1 = true
 *    [     work] 작업 중
 *    [     work] work 스레드 인터럽트 상태2 = true
 *    [     work] 자원 정리 시도
 *    [     work] 자원 정리 실패 - 자원 정리 중 인터럽트 발생
 *    [     work] work 스레드 인터럽트 상태3 = false
 *    [     work] 작업 종료
 */
public class ThreadStopMainV3 {
	
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
			while(!Thread.currentThread().isInterrupted()) { // 인터럽트 상태 변경은 x
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
