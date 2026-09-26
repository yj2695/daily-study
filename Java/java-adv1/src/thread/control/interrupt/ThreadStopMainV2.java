package thread.control.interrupt;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * 인터럽트 - 시작2
 * : 어떻게 하면 sleep() 처럼 스레드가 대기하는 상태에서 스레드를 깨우고,
 *   작업도 빨리 종료할 수 있을까?
 * 2) thread.interrupt(); 사용
 *  - 인터럽트를 사용하면, WAITING, TIMED_WAITING 같은 대기 상태의
 *    스레드를 직접 깨워서, 작동하는 RUNNABLE 상태로 만들 수 있다.
 *    
 *    [     work] 작업 중
 *    [     work] 작업 중
 *    [     main] 작업 중단 지시 thread.interrupt()
 *    [     main] work 스레드 인터럽트 상태1 = false --> 상태가 빠르게 바뀌어 false가 나온걸로 추정
 *    [     work] work 스레드 인터럽트 상태2 = false
 *    [     work] interrupt message = sleep interrupted
 *    [     work] state = RUNNABLE
 *    [     work] 자원 정리
 *    [     work] 자원 종료
 */
public class ThreadStopMainV2 {
	
	public static void main(String[] args) {
		MyTask task = new MyTask();
		Thread thread = new Thread(task, "work");
		thread.start();
		
		sleep(4000);
		log("작업 중단 지시 thread.interrupt()");
		thread.interrupt(); // interrupt = true
		log("work 스레드 인터럽트 상태1 = " + thread.isInterrupted());
	}
	
	static class MyTask implements Runnable {
		
		@Override
		public void  run() {
			try {
				while(true) {
					log("작업 중");
					Thread.sleep(3000); // 인터럽트가 깨운다 -> sleep() 처럼 예외를 던지는 메서드인 경우 예외 발생 
				}
			} catch (InterruptedException e) {
				log("work 스레드 인터럽트 상태2 = " + Thread.currentThread().isInterrupted()); // 인터럽트 처리되므로 interrupt = false
				log("interrupt message = " + e.getMessage());                             // sleep interrupted
				log("state = " + Thread.currentThread().getState());                      // 스레드가 깨어났으므로 RUNNABLE 상태가 됨
			}
			log("자원 정리");
			log("자원 종료");
		}
	}
}
