package thread.control.printer;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * 프린터 예제1 - 시작
 * : 인터럽트의 실제 사용 예 알아보기
 * 
 * [프린터 작동]
 * - main 스레드    : 사용자의 입력을 받아서 Printer 인스턴스의 jobQueue에 담는다.
 * - printer 스레드 : jobQueue를 확인한다.
 *    - jobQueue에 내용이 있으면 poll()을 이용해 꺼낸 다음에 출력한다.(3초)
 *    - jobQueue가 비었다면 continue를 사용해서 다시 while문을 반복한다.
 *    - jobQueue에 출력할 내용이 들어올 때까지 계속 확인한다.
 *    
 * [프린터 종료] => q를 입력햇을 때 바로 반응하지 않는 문제 존재
 * - main 스레드    : 사용자가 q를 입력한다. printer.work의 값을 false로 변경한다.
 *   - main 스레드는 while문을 빠져나가고 main 스레드가 종료된다.
 *   
 * - printer 스레드 : while문에서 work의 값이 false인 것을 확인한다.
 *   - printer 스레드는 while문을 빠져나가고, "프린터 종료"를 출력하고 종료된다.
 */
public class MyPrinterV1 {
	
	public static void main(String[] args) {
		Printer printer = new Printer();
		Thread printerThread = new Thread(printer, "printer");
		printerThread.start();
		
		Scanner userInput = new Scanner(System.in);
		while(true) {
			log("프린터할 문서를 입력하세요. 종료 (q): ");
			String input = userInput.nextLine();
			if(input.equals("q")) {
				printer.work = false;
				break;
			}
			printer.addJob(input);
		}
	}
	
	static class Printer implements Runnable {
		// 여러 스레드가 동시에 접근하는 변수에는 volatile 키워드를 붙여야 안전
		volatile boolean work = true;
		// 여러 스레드가 동시에 접근하는 경우 동시성을 지원하는 동시성 컬렉션을 사용해야함
		Queue<String> jobQueue = new ConcurrentLinkedQueue<>();
		
		@Override
		public void run() {
			while(work) {
				if(jobQueue.isEmpty()) {
					continue;
				}
				
				String job = jobQueue.poll();
				log("출력 시작: " + job + ", 대기 문서: " + jobQueue);
				sleep(3000);
				log("출력 완료");
			}
			log("프린터 종료");
		}
		
		public void addJob(String input) {
			jobQueue.offer(input);
		}
	}

}
