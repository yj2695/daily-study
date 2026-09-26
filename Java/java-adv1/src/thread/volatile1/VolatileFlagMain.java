package thread.volatile1;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * volatile, 메모리 가시성1~3
 * 
 * runFlag를 사용해서 스레드의 작업을 종료하려고 한다.
 * main 스레드, work 스레드 모두 MyTask 인스턴스에 있는 runFlag를 사용한다.
 * main 스레드에서는 sleep()을 통해 1초 뒤 runFlag를 false로 설정한다.
 * work 스레드는 run() 메서드를 실행하면서 while(runFlag)를 체크하는데, 
 * 이제 runFlag가 false가 되었으므로 "task 종료"를 출력하고 작업을 종료해야 한다.
 * 하지만 실행 결과는 while문에서 빠져나오지 못하고 계속 돈다.
 * 
 * [     main] runFlag = true
 * [     work] task 시작
 * [     main] runFlag를 false로 변경 시도
 * [     main] runFlag = false
 * [     main] main 종료
 * => why?
 * 
 * 실제 메모리의 접근 방식은 조금 다르다.
 * CPU는 처리 성능을 개선하기 위해 중간에 캐시 메모리라는 것을 사용한다.
 * 즉,
 * main 스레드가 돌아가는 CPU 코어1의 캐시 메모리
 * work 스레드가 돌아가는 CPU 코어2의 캐시 메모리
 * 가 존재한다.
 * 
 * 현대 CPU의 대부분은 코어 단위로 캐시 메모리를 각각 보유하고 있다.
 * 각 스레드가 runFlag의 값을 사용하면 CPU는 이 값을 효율적으로 처리하기 위해
 * 먼저 runFlag를 캐시 메모리에 불러온다. 그리고 이후에는 캐시 메모리에 있는 runFlag를 사용한다.
 * 
 * 여기서 중요한 것은 main 스레드의 runFlag를 false로 바꾸었을 때, 캐시 메모리의 runFlag 값만
 * 바뀌고 메인 메모리에는 즉시 반영되지 않는다. 또한 반영되는 시점을 "알 수 없다".
 * 만약 메인 메모리에 반영이 된다고 해도 work 스레드가 그 값을 캐시 메모리에 다시 불러오는 시점도 "알 수 없다".
 * 주로 컨텍스트 스위칭이 일어날 때(스레드가 바뀔 때) 캐시 메모리도 갱신되는데 이 때 반영될 수 있지만 보장은 아니다.
 * 
 * [메모리 가시성(memory visibility)]
 * : 멀티스레드 환경에서 한 스레드가 변경한 값이 다른 스레드에서 언제 보이는지에 대한 문제
 * 
 * 그렇다면 한 스레드에서 변경한 값이 다른 스레드에서 즉시 보이게 하려면 어떻게 해야할까?
 * => 해결 방안은 성능을 약간 포기하는 대신 값을 읽고 쓸 때 모두 메인 메모리에 직접 접근하면 된다.
 * 이 기능은 volatile 키워드로 사용할 수 있다. runFlag를 volatile로 선언했을 때 캐시 메모리를
 * 사용하지 않고 항상 메인 메모리에 직접 접근한다.
 * 
 * [     main] runFlag = true
 * [     work] task 시작
 * [     main] runFlag를 false로 변경 시도
 * [     main] runFlag = false
 * [     main] main 종료
 * [     work] task  종료
 */
public class VolatileFlagMain {
	
	public static void main(String[] args) {
		MyTask task = new MyTask();
		Thread t = new Thread(task, "work");
		log("runFlag = " + task.runFlag);
		t.start();
		
		sleep(1000);
		log("runFlag를 false로 변경 시도");
		task.runFlag = false;
		log("runFlag = " + task.runFlag);
		log("main 종료");
	}
	
	static class MyTask implements Runnable {
//		boolean runFlag = true;
		volatile boolean runFlag = true;
		
		public void run() {
			log("task 시작");
			while(runFlag) {
				// runFlag가 false로 변하면 탈출
			}
			log("task  종료");
		}
	}
}
