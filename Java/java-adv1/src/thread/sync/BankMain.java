package thread.sync;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * [       t2] 거래 시작: BankAccountV1
 * [       t1] 거래 시작: BankAccountV1
 * [       t2] [검증 시작] 출금액: 800, 잔액: 1000
 * [       t1] [검증 시작] 출금액: 800, 잔액: 1000
 * [       t2] [검증 완료] 출금액: 800, 잔액: 1000
 * [       t1] [검증 완료] 출금액: 800, 잔액: 1000
 * [     main] t1 state: TIMED_WAITING
 * [     main] t2 state: TIMED_WAITING
 * [       t2] [출금 완료] 출금액: 800, 잔액: -600
 * [       t1] [출금 완료] 출금액: 800, 잔액: -600
 * [       t1] 거래 종료
 * [       t2] 거래 종료
 * [     main] 최종 잔액: -600
 * 
 * > 동시성 문제 발생
 * why? 여러 스레드가 함께 사용하는 공유 자원을 여러 단계로 나누어 사용하기 때문
 * 
 * 1. 검증 단계 : 잔액이 출금액보다 많은지 확인한다.
 * 2. 출금 단계 : 잔액을 출금액만큼 줄인다.
 * 
 * 이 로직에는 하나의 큰 가정이 있다
 * : 스레드 하나의 관점에서 출금을 보면 검증 단계에서 확인한 잔액 1000원은 출금 단계에서
 *   계산을 끝마칠 때까지 같은 1000원으로 유지되어야 한다. 
 * 
 * 잔액은 여러 스레드가 함께 사용하는 공유자원이다. 따라서 출금 로직을 수행하는 중간에 
 * 다른 스레드에서 이 값을 얼마든지 변경할 수 있다. 
 * 
 * 한 번에 하나의 스레드만 실행한다면 ? 중간에 다른 스레드가 잔액을 변경하지 않아 안전하게 수행 가능
 * => 검증과 계산 두 단계는 한 번에 하나의 스레드만 실행해야 한다.
 * 
 * 임계 영역 (critical section)
 *  - 여러 스레드가 동시에 접근하면 데이터 불일치나 예상치 못한 동작이 발생할 수 있는 위험하고 중요한 코드 부분을 의미
 *  - 여러 스레드가 동시에 접근해서는 안되는 공유 자원을 접근하거나 수정하는 부분을 의미
 */
public class BankMain {
	
	public static void main(String[] args) throws InterruptedException {
//		BankAccount account = new BankAccountV1(1000);
		
		/**
		 * BankAccountV2의 메서드에 synchronized 키워드 추가
		 * 
		 * [       t1] 거래 시작: BankAccountV2
		 * [       t1] [검증 시작] 출금액: 800, 잔액: 1000
		 * [       t1] [검증 완료] 출금액: 800, 잔액: 1000
		 * [     main] t1 state: TIMED_WAITING
		 * [     main] t2 state: BLOCKED
		 * [       t1] [출금 완료] 출금액: 800, 잔액: 200
		 * [       t1] 거래 종료
		 * [       t2] 거래 시작: BankAccountV2
		 * [       t2] [검증 시작] 출금액: 800, 잔액: 200
		 * [       t2] [검증 실패] 출금액: 800, 잔액: 200
		 * [     main] 최종 잔액: 200
		 * 
		 * 모든 객체(인스턴스)는 내부에 자신만의 락(lock)을 가지고 있다.
		 * 스레드가 synchronized 키워드가 있는 메서드에 진입하려면 반드시
		 * 해당 인스턴스의 락이 있어야 한다.
		 * 
		 * 1) t1 스레드가 먼저 실행되면 먼저 BankAccountV2의 락을 획득한다.
		 * 2) 이후 t2 스레드가 실행되면 락을 획득할 때까지 BLOCKED 상태로 무한정 대기한다.
		 * 3) t1 메서드 호출이 모두 끝나면 락을 반납한다.
		 * 4) t2는 자동으로 락을 획득하고 코드를 실행한다. 이제 잔액이 출금액보다 적으므로 
		 *    검증 로직을 통과하지 못한다.
		 * 5) t2는 메서드를 종료하며 락을 반납한다.
		 * 
		 * > 락을 획득하는 순서는 보장되지 않는다.
		 * > volatile을 사용하지 않아도 synchronized 안에서 접근하는 변수의 메모리 가시성
		 *   문제는 해결된다.
		 */
//		BankAccount account = new BankAccountV2(1000);
		
		/**
		 * synchronized 코드 블럭
		 * : BankAccountV2에서 적용한 synchronized 적용 범위는 메서드 전체이다.
		 *   따라서 여러 스레드가 함께 실행해도 문제없는 "거래 시작", "거래 종료"를 출력하는
		 *   코드도 한 번에 하나의 스레드만 실행할 수 있다.
		 *   자바는 이런 문제를 해결하기 위해 synchronized를 메서드 단위가 아니라,
		 *   특정 코드 블럭에 최적화해서 적용할 수 있는 기능을 제공한다.
		 * 
		 * [       t2] 거래 시작: BankAccountV3
		 * [       t1] 거래 시작: BankAccountV3
		 * [       t2] [검증 시작] 출금액: 800, 잔액: 1000
		 * [       t2] [검증 완료] 출금액: 800, 잔액: 1000
		 * [     main] t1 state: BLOCKED
		 * [     main] t2 state: TIMED_WAITING
		 * [       t2] [출금 완료] 출금액: 800, 잔액: 200
		 * [       t2] 거래 종료
		 * [       t1] [검증 시작] 출금액: 800, 잔액: 200
		 * [       t1] [검증 실패] 출금액: 800, 잔액: 200
		 * [     main] 최종 잔액: 200
		 */
//		BankAccount account = new BankAccountV3(1000);
//		BankAccount account = new BankAccountV4(1000);
//		BankAccount account = new BankAccountV5(1000);
		BankAccount account = new BankAccountV6(1000);
		
		Thread t1 = new Thread(new WithdrawTask(account, 800), "t1");
		Thread t2 = new Thread(new WithdrawTask(account, 800), "t2");
		
		t1.start();
		t2.start();
		
		sleep(500);
		log("t1 state: " + t1.getState());
		log("t2 state: " + t2.getState());
		
		t1.join();
		t2.join();
		log("최종 잔액: " + account.getBalance());
	}
}
