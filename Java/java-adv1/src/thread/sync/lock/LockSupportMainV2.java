package thread.sync.lock;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport2
 * 
 * 여기서는 스레드를 깨우기 위한 unpark()를 사용하지 않는다.
 * parkNanos(시간)을 사용하면 지정한 시간 이후에 스레드가 깨어난다.
 * 
 * [ Thread-1] park 시작
 * [     main] Thread-1 state: TIMED_WAITING
 * [ Thread-1] park 종료, state: RUNNABLE
 * [ Thread-1] 인터럽트 상태: false
 * 
 * [BLOCKED vs WAITING]
 * - BLOKKED 상태는 인터럽트가 걸려도 대기 상태를 빠져나오지 못한다.
 * - BLOCKED 상태는 자바의 synchronized에서 락을 획득하기 위해 대기할 때 사용된다.
 * => BLOCKED 상태는 synchronized에서만 사용하는 특별한 대기 상태라고 이해하면 된다.
 * 
 * - WAITING, TIMED_WAITING 상태는 인터럽트가 걸리면 대기 상태를 빠져나와 RUNNABLE 상태로 변한다.
 * - WAITING, TIMED_WAITING 상태는 스레드가 특정 조건이나 시간 동안 대기할 때 발생하는 상태이다.
 * => WAITING, TIMED_WAITING 상태는 범용적으로 활용할 수 있는 대기 상태라고 이해하면 된다.
 * 
 * LockSupport를 활용하면, 무한 대기하지 않는 락 기능을 만들 수 있다.
 * 하지만 이런 기능을 사용하기 위해서 직접 구현해야하는 문제가 있다.
 * => synchronized 처럼 고수준의 기능을 위해 자바는 Lock 인터페이스와 ReentrantLock이라는 구현체로
 *    이미 다 구현해두었다. ReentrantLock은 LockSupport를 활용해서 synchronized의 단점을 극복하면서도
 *    매우 편리하게 임계 영역을 다룰 수 있는 다양한 기능을 제공한다.
 */
public class LockSupportMainV2 {
	
	public static void main(String[] args) {
		Thread thread1 = new Thread(new ParkTask(), "Thread-1");
		thread1.start();
		
		// 잠시 대기하여 Thread-1이 park 상태에 빠질 시간을 준다.
		sleep(100);
		log("Thread-1 state: " + thread1.getState());
	}
	
	static class ParkTask implements Runnable {
		
		@Override
		public void run() {
			log("park 시작");
			LockSupport.parkNanos(2000_000000); // 2초 이후 깨어남
			log("park 종료, state: " + Thread.currentThread().getState());
			log("인터럽트 상태: " + Thread.currentThread().isInterrupted());
		}
	}
	
}
