package thread.control.printer;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * 프린터 예제2 - 인터럽트 도입
 */
public class MyPrinterV2 {
	
	public static void main(String[] args) {
		Printer printer = new Printer();
		Thread printerThread = new Thread(printer, "printer");
		printerThread.start();
		
		Scanner userInput = new Scanner(System.in);
		while(true) {
			log("프린터할 문서를 입력하세요. 종료 (q): ");
			String input = userInput.nextLine();
			if(input.equals("q")) {
				// main 스레드는 work 변수도 false로 변경하고, printer 스레드에 인터럽트도 함께 호출하여 반응성이 더 좋아진다.
				printer.work = false; // => 얘 없어도 동작하긴함
				printerThread.interrupt();
				break;
			}
			printer.addJob(input);
		}
	}
	
	static class Printer implements Runnable {
		volatile boolean work = true;
		Queue<String> jobQueue = new ConcurrentLinkedQueue<>();
		
		@Override
		public void run() {
			while(work) { // 1) work=false로 빠져나옴
				if(jobQueue.isEmpty()) {
					continue;
				}
				
				try {
					String job = jobQueue.poll();
					log("출력 시작: " + job + ", 대기 문서: " + jobQueue);
					Thread.sleep(3000); // 2) sleep() 상태에서 빠져나옴
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
