package thread.control.interrupt;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * 인터럽트 - 시작1
 * : 특정 스레드의 작업을 중간에 중단하는 방법
 * 1) 변수 사용 : runFlag
 *  - runFlag를 false로 바꿔 작업을 중단시킨다.
 *  - main 스레드가 작업 중단을 지시해도, work 스레드가 즉각 반응하지 않는다.
 *  - work 스레드의 두번째 작업 도중에 작업 중단이 지시되므로 그 다음 턴에 반영됨 
 *  - 작업 중단 지시 2초 정도 이후에 중단 (3초x2 - 4초 = 2초)
 */
public class ThreadStopMainV1 {
	
	public static void main(String[] args) {
		MyTask task = new MyTask();
		Thread thread = new Thread(task, "work");
		thread.start();
		
		sleep(4000);
		log("작업 중단 지시 runFlag = flase");
		task.runFlag = false;
	}
	
	static class MyTask implements Runnable {
		
		volatile boolean runFlag = true;
		
		@Override
		public void  run() {
			while (runFlag) {
				log("작업 중");
				sleep(3000);
			}
			log("자원 정리");
			log("자원 종료");
		}
	}
	
}
