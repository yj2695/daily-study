package thread.sync.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock - 이론
 * 
 * 자바는 1.0부터 존재한 synchronized 와 BLOCKED 상태를 통한 임계 영역 관리의 한계를 극복하기 위해
 * 자바 1.5부터 Lock 인터페이스와 ReentrantLock 구현체를 제공한다.
 * 
 * [Lock 인터페이스 제공 메서드]
 * - void lock()
 *  : 락을 획득한다. 이미 다른 스레드가 획득햇다면, 락이 풀릴 때 까지 대기한다(WAITING).
 *    인터럽트에 응답하지 않는다.
 *  
 * - void lockInterruptibly()
 *  : 락 획득을 시도하되, 다른 스레드가 인터럽트할 수 있도록 한다.
 *    만약 다른 스레드가 이미 락을 획득했다면, 현재 스레드는 락을 획득할 때까지 대기한다.
 *    대기 중 인터럽트가 발생하면 InterruptedException이 발생하며 락 획득을 포기한다.
 *  
 * - boolean tryLock()
 *  : 락 획득을 시도하고, 즉시 성공 여부를 반환한다.
 *    만약 다른 스레드가 이미 락을 획득했다면 false 반환, 그렇지 않으면 락 획득하고 true 반환.
 *  
 * - boolean tryLock(long time, TimeUnit unit)
 *  : 주어진 시간 동안 락 획득을 시도한다.
 *    주어진 시간 안에 락 획득하면 true, 못하면 false를 반환한다.
 *    대기 중 인터럽트 발생하면 InterruptedException이 발생하며 락 획득을 포기한다.
 *  
 *  - void unlock()
 *  : 락을 해제한다. 대기중인 스레드 중 하나가 락을 획득하게 된다.
 *    락을 획득한 스레드가 호출해야하며 그러지 않으면 IllegalMonitorStateException이 발생할 수 있다.
 *  
 *  - Condition newCondition()
 *  : Conditino 객체를 생성하여 반환한다. Condition 객체는 락과 결합되어 사용되며, 스레드가 특정 조건을
 *    기다리거나 신호를 받을 수 있도록 한다.
 *
 * [공정성]
 * Lock 인터페이스의 대표적인 구현체로 ReentrantLock이 있는데,
 * 이 클래스는 스레드가 공정하게 락을 얻을 수 있는 모드를 제공한다. 
 * 
 * - 비공정 모드 (기본 모드) : 락을 풀었을 때, 대기 중인 스레드 중 아무나 락을 획득할 수 있다.
 * - 공정 모드 : 락을 요청한 순서대로 스레드가 락을 획득할 수 있다.
 */
public class ReentrantLockEx {
	
	// 비공정 모드 락
	private final Lock nonFairLock = new ReentrantLock();
	
	// 공정 모드 락
	private final Lock fairLock = new ReentrantLock(true);
	
	public void nonFairLockTest() {
		nonFairLock.lock();
		try {
			// 임계 영역
		} finally {
			nonFairLock.unlock();
		}
	}
	
	public void fairLockTest() {
		fairLock.lock();
		try {
			// 임계 영역
		} finally {
			fairLock.unlock();
		}
	}
	
}
