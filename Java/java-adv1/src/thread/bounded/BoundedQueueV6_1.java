package thread.bounded;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Blocks - 대기
 * 
 * [추가] put(e) : 지정된 요소를 큐에 추가할 때까지 대기한다. 큐가 가득 차면 공간이 생길 때까지 대기한다.
 * [제거] take() : 큐에서 요소를 제거하고 반환한다. 큐가 비어있으면 요소가 준비될 때까지 대기한다.
 * [관찰] 해당 사항 없음
 * 
 */
public class BoundedQueueV6_1 implements BoundedQueue {
	
	private BlockingQueue<String> queue;
	
	public BoundedQueueV6_1(int max) {
		this.queue = new ArrayBlockingQueue<>(max);
	}
	
	@Override
	public void put(String data) {
		try {
			queue.put(data);
		} catch (InterruptedException e) {
			throw new RuntimeException();
		}
	}
	
	@Override
	public String take() {
		try {
			return queue.take();
		} catch (InterruptedException e) {
			throw new RuntimeException();
		}
	}
	
	@Override
	public String toString() {
		return queue.toString();
	}
}