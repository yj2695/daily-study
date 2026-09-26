package thread.bounded;

import java.util.ArrayDeque;
import java.util.Queue;
import static util.MyLogger.log;

public class BoundedQueueV3 implements BoundedQueue {
	
	private final Queue<String> queue = new ArrayDeque<>();
	
	private final int max; // 버퍼에 저장할 수 있는 최대 크기
	
	public BoundedQueueV3(int max) {
		this.max = max;
	}
	
	@Override
	public synchronized void put(String data) {
		while (queue.size() == max) {
			log("[put] 큐가 가득 참, 생산자 대기");
			try {
				wait(); // RUNNABLE -> WATINIG 락 반납
				log("[put] 생산자 깨어남");
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		queue.offer(data);
		log("[put] 생산자 데이터 저장, notify() 호출");
		notify(); // 대기 스레드, WAIT -> BLOCKED
	}
	
	@Override
	public synchronized String take() {
		while (queue.isEmpty()) {
			log("[take] 큐에 데이터가 없음, 소비자 대기");
			try {
				wait(); // RUNNABLE -> WATINIG 락 반납
				log("[take] 소비자 깨어남");
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		String data = queue.poll();
		log("[take] 소비자 데이터 획득, notify() 호출");
		notify(); // 대기 스레드, WAIT -> BLOCKED
		return data;
	}
	
	@Override
	public String toString() {
		return queue.toString();
	}
	
}
/**
 * [생산자 먼저 실행]
 * 
 * [     main] == [생산자 먼저 실행] 시작, BoundedQueueV3 ==
 * 
 * [     main] 생산자 시작
 * [producer1] [생산 시도] data1 -> []
 * [producer1] [put] 생산자 데이터 저장, notify() 호출
 * [producer1] [생산 완료] data1 -> [data1]
 * [producer2] [생산 시도] data2 -> [data1]
 * [producer2] [put] 생산자 데이터 저장, notify() 호출
 * [producer2] [생산 완료] data2 -> [data1, data2]
 * [producer3] [생산 시도] data3 -> [data1, data2]
 * [producer3] [put] 큐가 가득 참, 생산자 대기
 * 
 * 
 * [     main] 현재 상태 출력, 큐 데이터: [data1, data2]
 * [     main] producer1: TERMINATED
 * [     main] producer2: TERMINATED
 * [     main] producer3: WAITING
 * 
 * [     main] 소비자 시작
 * [consumer1] [소비 시도]     ? <- [data1, data2]
 * [consumer1] [take] 소비자 데이터 획득, notify() 호출
 * [consumer1] [소비 완료] data1 <- [data2]
 * [producer3] [put] 생산자 깨어남
 * [producer3] [put] 생산자 데이터 저장, notify() 호출
 * [producer3] [생산 완료] data3 -> [data2, data3]
 * [consumer2] [소비 시도]     ? <- [data2, data3]
 * [consumer2] [take] 소비자 데이터 획득, notify() 호출
 * [consumer2] [소비 완료] data2 <- [data3]
 * [consumer3] [소비 시도]     ? <- [data3]
 * [consumer3] [take] 소비자 데이터 획득, notify() 호출
 * [consumer3] [소비 완료] data3 <- []
 * 
 * [     main] 현재 상태 출력, 큐 데이터: []
 * [     main] producer1: TERMINATED
 * [     main] producer2: TERMINATED
 * [     main] producer3: TERMINATED
 * [     main] consumer1: TERMINATED
 * [     main] consumer2: TERMINATED
 * [     main] consumer3: TERMINATED
 * [     main] == [생산자 먼저 실행] 종료, BoundedQueueV3 ==
 * 
 * ==========================================================================
 *
 *[소비자 먼저 실행]
 *
 * [     main] == [소비자 먼저 실행] 시작, BoundedQueueV3 ==
 * [     main] 소비자 시작
 * [consumer1] [소비 시도]     ? <- []
 * [consumer1] [take] 큐에 데이터가 없음, 소비자 대기
 * [consumer2] [소비 시도]     ? <- []
 * [consumer2] [take] 큐에 데이터가 없음, 소비자 대기
 * [consumer3] [소비 시도]     ? <- []
 * [consumer3] [take] 큐에 데이터가 없음, 소비자 대기
 * 
 * [     main] 현재 상태 출력, 큐 데이터: []
 * [     main] consumer1: WAITING
 * [     main] consumer2: WAITING
 * [     main] consumer3: WAITING
 * 
 * [     main] 생산자 시작
 * [producer1] [생산 시도] data1 -> []
 * [producer1] [put] 생산자 데이터 저장, notify() 호출
 * [producer1] [생산 완료] data1 -> [data1]
 * [consumer1] [take] 소비자 깨어남
 * [consumer1] [take] 소비자 데이터 획득, notify() 호출
 * [consumer2] [take] 소비자 깨어남
 * [consumer1] [소비 완료] data1 <- []
 * [consumer2] [take] 큐에 데이터가 없음, 소비자 대기
 * [producer2] [생산 시도] data2 -> []
 * [producer2] [put] 생산자 데이터 저장, notify() 호출
 * [producer2] [생산 완료] data2 -> [data2]
 * [consumer3] [take] 소비자 깨어남
 * [consumer3] [take] 소비자 데이터 획득, notify() 호출
 * [consumer3] [소비 완료] data2 <- []
 * [consumer2] [take] 소비자 깨어남
 * [consumer2] [take] 큐에 데이터가 없음, 소비자 대기
 * [producer3] [생산 시도] data3 -> []
 * [producer3] [put] 생산자 데이터 저장, notify() 호출
 * [producer3] [생산 완료] data3 -> [data3]
 * [consumer2] [take] 소비자 깨어남
 * [consumer2] [take] 소비자 데이터 획득, notify() 호출
 * [consumer2] [소비 완료] data3 <- []
 * 
 * [     main] 현재 상태 출력, 큐 데이터: []
 * [     main] consumer1: TERMINATED
 * [     main] consumer2: TERMINATED
 * [     main] consumer3: TERMINATED
 * [     main] producer1: TERMINATED
 * [     main] producer2: TERMINATED
 * [     main] producer3: TERMINATED
 * [     main] == [소비자 먼저 실행] 종료, BoundedQueueV3 ==
 * 
 */
