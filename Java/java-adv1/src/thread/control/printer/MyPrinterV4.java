package thread.control.printer;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * 프린터 예제4 - yield 도입
 */
public class MyPrinterV4 {
	
	public static void main(String[] args) {
		Printer printer = new Printer();
		Thread printerThread = new Thread(printer, "printer");
		printerThread.start();
		
		Scanner userInput = new Scanner(System.in);
		while(true) {
			log("프린터할 문서를 입력하세요. 종료 (q): ");
			String input = userInput.nextLine();
			if(input.equals("q")) {
				printerThread.interrupt();
				break;
			}
			printer.addJob(input);
		}
	}
	
	static class Printer implements Runnable {
		Queue<String> jobQueue = new ConcurrentLinkedQueue<>();
		
		@Override
		public void run() {
			/**
			 * 인터럽트가 발생하기 전까지 게속 인터럽트의 상태를 체크하고, jobQueue의 상태를 확인한다.
			 * 이 로직은 결과적으로 CPU 자원을 많이 사용하게 된다.
			 * 
			 * 현재 작동하는 스레드가 아주 많다고 가정하면, 그 시간에 다른 스레드들을 더 많이 실행해서
			 * jobQueue에 필요한 작업을 빠르게 만들어 넣어주는 게 더 효율적이다.
			 * 그래서 다음과 같이 jobQueue에 작업이 비어있으면 yield()를 호출해서 작업을 양보한다.
			 */
			while(!Thread.interrupted()) {
				if(jobQueue.isEmpty()) {
					Thread.yield();
					continue;
				}
				
				try {
					String job = jobQueue.poll();
					log("출력 시작: " + job + ", 대기 문서: " + jobQueue);
					Thread.sleep(3000);
					log("출력 완료");
				} catch (InterruptedException e) {
					log("인터럽트!");
					break;
				}
			}
			log("프린터 종료");
		}
		
		public void addJob(String input) {
			jobQueue.offer(input);
		}
	}

}
