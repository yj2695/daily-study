package thread.sync;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock - 대기 중단
 * 
 * boolean tryLock()
 * : 락 획득을 시도하고, 즉시 성공 여부를 반환한다.
 *   만약 다른 스레드가 이미 락을 획득했다면 false 반환,
 *   그렇지 않으면 락을 획득하고 true 반환.
 * 
 * [       t1] 거래 시작: BankAccountV5
 * [       t2] 거래 시작: BankAccountV5
 * [       t1] [검증 시작] 출금액: 800, 잔액: 1000
 * [       t2] [진입 실패] 이미 처리 중인 작업이 있습니다.
 * [     main] t1 state: TIMED_WAITING
 * [     main] t2 state: TERMINATED
 * [       t1] [출금 완료] 출금액: 800, 잔액: 200
 * [       t1] 거래 종료
 * [     main] 최종 잔액: 200
 */
public class BankAccountV5 implements BankAccount{
	
	private int balance;
	
	private final Lock lock = new ReentrantLock();
	
	public BankAccountV5(int initialBalance) {
		this.balance = initialBalance;
	}
	
	@Override
	public boolean withdraw(int amount) {
		log("거래 시작: " + getClass().getSimpleName());
		
		if(!lock.tryLock()) {
			log("[진입 실패] 이미 처리 중인 작업이 있습니다.");
			return false;
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
