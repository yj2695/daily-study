package thread.sync.test;

import static util.MyLogger.log;

public class SyncTest2Main {
	
	public static void main(String[] args) {
		MyCounter myCounter = new MyCounter();
		
		Runnable task = new Runnable() {
			@Override
			public void run() {
				myCounter.count();
			}
		};
		
		Thread thread1 = new Thread(task, "Thread-1");
		Thread thread2 = new Thread(task, "Thread-2");
		
		thread1.start();
		thread2.start();
	}
	
	static class MyCounter {
		/**
		 * localValue는 지역 변수이다.
		 * 지역 변수는 스레드의 개별 저장 공간인 스택 영역에 생성되므로
		 * 다른 스레드와 공유되지 않는다.
		 * 여기에 synchronized를 사용하면 성능만 느려짐!
		 */
		public void count() {
			int localValue = 0;
			for(int i=0; i<1000; i++) {
				localValue = localValue + 1;
			}
			log("결과: " + localValue);
		}
	}
}