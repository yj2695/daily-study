package thread.bounded;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Throws Exception - 대기 시 예외
 * 
 * [추가] add(e) : 지정된 요소를 큐에 추가하며, 큐가 가득 차면 IllegalStateException 예외를 던진다.
 * [제거] remove() : 큐에서 요소를 제거하며 반환한다. 큐가 비어있으면 NoSuchElementException 예외를 던진다.
 * [관찰] element() : 큐의 머리 요소를 반환하지만, 요소를 큐에서 제거하지 않는다. 큐가 비어있으면 NoSuchElementException 예외를 던진다.
 * 
 */
public class BoundedQueueV6_4 implements BoundedQueue {
	
	private BlockingQueue<String> queue;
	
	public BoundedQueueV6_4(int max) {
		this.queue = new ArrayBlockingQueue<>(max);
	}
	
	@Override
	public void put(String data) {
		queue.add(data);
	}
	
	@Override
	public String take() {
		return queue.remove();
	}
	
	@Override
	public String toString() {
		return queue.toString();
	}
}