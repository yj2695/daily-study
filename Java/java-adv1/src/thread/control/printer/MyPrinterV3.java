package thread.control.printer;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * 프린터 예제3 - 인터럽트 코드 개선
 * : work 변수 제거 -> Thread.interrupted() 사용
 */
public class MyPrinterV3 {
	
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
			while(!Thread.interrupted()) { // 1) 인터럽트 상태면 빠져나옴 
				// 인터럽트 상태가 아니면 진행
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
