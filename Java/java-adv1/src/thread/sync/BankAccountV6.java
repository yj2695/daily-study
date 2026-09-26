package thread.sync;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock - 대기 중단
 * 
 * boolean tryLock(long time, TimeUnit unit)
 * : 주어진 시간 동안 락 획득을 시도한다.
 *   주어진 시간 안에 락을 획득하면 true 반환,
 *   주어진 시간이 지나도 락을 획득하지 못한 경우 false 반환.
 *   이 메서드는 대기 중 인터럽트가 발생하면 
 *   InterruptedException이 발생하며 락 획득을 포기한다.
 *
 * [       t1] 거래 시작: BankAccountV6
 * [       t2] 거래 시작: BankAccountV6
 * [       t1] [검증 시작] 출금액: 800, 잔액: 1000
 * [     main] t1 state: TIMED_WAITING // sleep(1000)
 * [     main] t2 state: TIMED_WAITING // tryLock(500)
 * [       t2] [진입 실패] 이미 처리 중인 작업이 있습니다.
 * [       t1] [출금 완료] 출금액: 800, 잔액: 200
 * [       t1] 거래 종료
 * [     main] 최종 잔액: 200
 */
public class BankAccountV6 implements BankAccount{
	
	private int balance;
	
	private final Lock lock = new ReentrantLock();
	
	public BankAccountV6(int initialBalance) {
		this.balance = initialBalance;
	}
	
	@Override
	public boolean withdraw(int amount) {
		log("거래 시작: " + getClass().getSimpleName());
		
		try {
			if(!lock.tryLock(500, TimeUnit.MILLISECONDS)) {
				log("[진입 실패] 이미 처리 중인 작업이 있습니다.");
				return false;
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		
		try {
			log("[검증 시작] 출금액: " + amount + ", 잔액: " + balance);
			if(balance < amount) {
				log("[검증 실패] 출금액: " + amount + ", 잔액: " + balance);
				return false;
			}
			
			sleep(1000); // 출금에 걸리는 시간으로 가정
			balance = balance - amount;
			log("[출금 완료] 출금액: " + amount + ", 잔액: " + balance);
		} finally {
			lock.unlock(); // ReentrantLock 이용하여 lock 해제
		}
		log("거래 종료");
		return true;
	}
	
	@Override
	public int getBalance() {
		lock.lock(); // ReentrantLock 이용하여 lock을 걸기
		try {
			return balance;
		} finally {
			lock.unlock(); // ReentrantLock 이용하여 lock 해제
		}
	}
	
}
