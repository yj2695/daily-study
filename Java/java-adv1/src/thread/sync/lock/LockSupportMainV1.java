package thread.sync.lock;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport1
 * 
 * [synchronized의 단점]
 * - 무한 대기: BLOCKED 상태의 스레드는 락이 풀릴 때까지 무한 대기한다.
 * - 공정성: 락이 돌아왔을 때 BLOCKED 상태의 여러 스레드 중에 어떤 스레드가 락을 획득할 지 알 수 없다.
 * 
 * => 이런 문제를 해결하기 위해 자바 1.5부터 java.util.concurrent라는 라이브러리 패키지가 추가된다.
 * LockSupport를 사용하면 synchronized의 가장 큰 단점인 무한 대기 문제를 해결할 수 있다.
 * 
 * [LockSupport의 대표적인 기능]
 * - park(): 스레드를 WAITING 상태로 변경한다.
 * - parkNanos(nanos): 스레드를 나노초 동안만 TIMED_WAITING 상태로 변경한다.
 * - unpark(thread): WAITING 상태의 대상 스레드를 RUNNABLE 상태로 변경한다.
 */
public class LockSupportMainV1 {
	
	public static void main(String[] args) {
		Thread thread1 = new Thread(new ParkTask(), "Thread-1");
		thread1.start();
		
		// 잠시 대기하여 Thread-1이 park 상태에 빠질 시간을 준다.
		sleep(100);
		log("Thread-1 state: " + thread1.getState());
		
		// WAITING -> RUNNABLE 깨움
		log("main -> unpark(Thread-1)");
//		LockSupport.unpark(thread1); // 1. unpark 사용
		thread1.interrupt();         // 2. interrupt() 사용
	}
	
	static class ParkTask implements Runnable {
		
		@Override
		public void run() {
			log("park 시작");
			LockSupport.park();
			log("park 종료, state: " + Thread.currentThread().getState());
			log("인터럽트 상태: " + Thread.currentThread().isInterrupted());
		}
	}
	
}
