package CH07;

import java.util.Arrays;

/**
 * CH7 정렬
 * 
 * 23) K번째 수
 */
public class P23 {
	public int[] solution(int[] array, int[][] commands) {
		int[] answer = new int[commands.length];
		
		for (int i=0; i < answer.length; i++) {
			int[] command = commands[i];
			
			int from = command[0] - 1;
			int to = command[1];
			int k = command[2] - 1;
			
			int[] sub = Arrays.copyOfRange(array, from, to);
			Arrays.sort(sub);
			
			answer[i] = sub[k];
		}
		
		return answer;
	}
	
}
