package thread.bounded;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Times Out - 시간 대기
 * 
 * [추가] offer(e, time, unit) : 지정된 요소를 큐에 추가하려고 시도하며,
 *                             지정된 시간 동안 큐가 비워지기를 기다리다가 시간이 초과되면 false를 반환한다.
 * [제거] poll(time, unit) : 큐에서 요소를 제거하고 반환한다.
 *                         큐에 요소가 없다면 지정된 시간 동안 요소가 준비되기를 기다리다가 시간이 초과되면 null을 반환한다.
 * [관찰] 해당 사항 없음
 * 
 */
public class BoundedQueueV6_3 implements BoundedQueue {
	
	private BlockingQueue<String> queue;
	
	public BoundedQueueV6_3(int max) {
		this.queue = new ArrayBlockingQueue<>(max);
	}
	
	@Override
	public void put(String data) {
		try {
			boolean result = queue.offer(data, 1, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public String take() {
		try {
			return queue.poll(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public String toString() {
		return queue.toString();
	}
}