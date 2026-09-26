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
public class BoundedQueueV5 implements BoundedQueue {
	
	private final Lock lock = new ReentrantLock();
	
	// Condition 분리
	private final Condition producerCond = lock.newCondition();
	private final Condition consumerCond = lock.newCondition();
	
	private final Queue<String> queue = new ArrayDeque<>();
	private final int max; // 버퍼에 저장할 수 있는 최대 크기
	
	public BoundedQueueV5(int max) {
		this.max = max;
	}
	
	@Override
	public void put(String data) {
		lock.lock();
		try {
			while (queue.size() == max) {
				log("[put] 큐가 가득 참, 생산자 대기");
				try {
					// 생산자 전용 스레드 대기 공간에 보관
					producerCond.await();
					log("[put] 생산자 깨어남");
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			queue.offer(data);
			log("[put] 생산자 데이터 저장, consumerCond.signal() 호출");
			consumerCond.signal();
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
					// 소비자 전용 스레드 대기 공간에 보관
					consumerCond.await();
					log("[take] 소비자 깨어남");
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			String data = queue.poll();
			log("[take] 소비자 데이터 획득, producerCond.signal() 호출");
			producerCond.signal();
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