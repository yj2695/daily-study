package thread.control.sleep;

/**
 * yield - 양보하기
 * : 특정 스레드가 크게 바쁘지 않은 상황이어서 다른 스레드에 CPU 실행 기회를 양보하고 싶을 수 있다.
 *   이렇게 양보하면 스케줄링 큐에 대기 중인 다른 스레드가 CPU 실행 기회를 더 빨리 얻을 수 있다.
 */
public class YieldMain {
	
	static final int THREAD_COUNT = 1000;
	
	public static void main(String[] args) {
		for (int i=0; i<THREAD_COUNT; i++) {
			Thread thread = new Thread(new MyRunnable());
			thread.start();
		}
	}
	
	static class MyRunnable implements Runnable {
		public void run() {
			for (int i=0; i<10; i++) {
				System.out.println(Thread.currentThread().getName() + " - " + i); // 0~9까지 출력
				
				// 1) empty : 특정 스레드가 쭉 연달아 수행된 다음 다른 스레드가 수행된다.
				
				// 2) sleep : sleep(1)은 스레드의 상태를 1밀리초동안 아주 잠깐 RUNNABLE -> TIMED_WATING 상태로 변경한다.
				// 이렇게 되면 스레드는 CPU 자원을 사용하지 않고, 실행 스케줄링에서 잠시 제외된다. 즉, 먼저 스레드의 상태가 바뀌면서 다른 스레드에 실행을 양보하게 된다.
				// 그래서 결과가 여러 스레드가 혼잡되어 츨력된다.
				// sleep(1);
				
				// 3) yield : Thread.yield() 메서드는 현재 실행 중인 스레드가 자발적으로 CPU를 양보하여 다른 스레드가 실행될 수 있도록 한다.
				// yield() 메서드를 호출한 스레드는 RUNNABLE 샹태를 유지하면서 CPU를 양보한다. 즉, 이 스레드는 다시 스케줄링 큐에 들어가면서 다른
				// 스레드에게 CPU 사용 기회를 넘긴다. RUNNABLE 상태를 유지하기 때문에, 양보할 사람이 없다면 본인 스레드가 계속 실행될 수 있다.
				// 결과값은 empty와 sleep이 섞인 형태로 나타난다.
				// Thread.yield();
			}
		}
	}
	
}
