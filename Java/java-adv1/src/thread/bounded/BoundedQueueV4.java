package thread.bounded;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static util.MyLogger.log;

/**
 * Condition : ReentrantLock을 사용하는 스레드가 대기하는 스레드 대기 공간
 * lock.newCondition() 메서드를 호출하면 스레드 대기 공간이 만들어진다.
 * 
 */
public class BoundedQueueV4 implements BoundedQueue {
	
	private final Lock lock = new ReentrantLock();
	private final Condition condition = lock.newCondition();
	
	private final Queue<String> queue = new ArrayDeque<>();
	private final int max; // 버퍼에 저장할 수 있는 최대 크기
	
	public BoundedQueueV4(int max) {
		this.max = max;
	}
	
	@Override
	public void put(String data) {
		lock.lock();
		try {
			while (queue.size() == max) {
				log("[put] 큐가 가득 참, 생산자 대기");
				try {
					// Object.wait()와 유사한 기능.
					// 지정한 condition에 현재 스레드를 대기(WAITING) 상태로 보관한다.
					// 이때 ReentrantLock에서 획득한 락을 반납하고 대기 상태로 condition에 보관된다.
					condition.await();
					log("[put] 생산자 깨어남");
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			queue.offer(data);
			log("[put] 생산자 데이터 저장, signal() 호출");
			// Object.notify()와 유사한 기능
			// 지정한 condition에서 대기중인 스레드를 하나 깨운다.
			// 깨어난 스레드는 condition에서 빠져나온다.
			condition.signal();
		} finally {
			lock.unlock();
		}
	}
	
	@Override
	public String take() {
		lock.lock();
		try {
			while (queue.isEmpty()) {
				log("[take] 큐에 데이터가 없음, 소비자 대기");
				try {
					condition.await();
					log("[take] 소비자 깨어남");
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			String data = queue.poll();
			log("[take] 소비자 데이터 획득, signal() 호출");
			condition.signal();
			return data;
		} finally {
			lock.unlock();
		}
	}
	
	@Override
	public String toString() {
		return queue.toString();
	}
	
}