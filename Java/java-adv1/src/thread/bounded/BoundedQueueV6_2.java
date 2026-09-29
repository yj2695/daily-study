package thread.bounded;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static util.MyLogger.log;

/**
 * Special Value - 대기 시 즉시 반환
 * 
 * [추가] offer(e) : 지정된 요소를 큐에 추가하려고 시도하며, 큐가 가득 차면 false를 반환한다.
 * [제거] poll() : 큐에서 요소를 제거하고 반환한다. 큐가 비어있으면 null을 반환한다.
 * [관찰] peek() : 큐의 머리 요소를 반환하지만, 요소를 큐에서 제거하지 않는다. 큐가 비어있으면 null을 반환한다.
 * 
 */
public class BoundedQueueV6_2 implements BoundedQueue {
	
	private BlockingQueue<String> queue;
	
	public BoundedQueueV6_2(int max) {
		this.queue = new ArrayBlockingQueue<>(max);
	}
	
	@Override
	public void put(String data) {
		boolean result = queue.offer(data);
		log("저장 시도 결과 = " + result);
	}
	
	@Override
	public String take() {
		return queue.poll();
	}
	
	@Override
	public String toString() {
		return queue.toString();
	}
}