package CH05;

import java.util.ArrayList;
import java.util.List;

/**
 * CH5 재귀
 * 
 * 16) 하노이의 탑
 * 
 * 1. 상태
 * (n, from, to)
 * n개의 원반을 기둥 from에서 기둥 to로 옮기는 과정
 * 
 * 2. 종료 조건
 * (1, from, to) = [[from, to]]
 * 
 * 3. 점화식
 * (n, from, to) = (n-1, from, empty) + (1, from, to) + (n-1, empty, to)
 * empty = 6-from-to
 */
public class P16 {
	public int[][] solution(int n) {
		List<int[]> process = new ArrayList<>();
		hanoi(n, 1, 3, process);
		
		return process.toArray(new int[0][]);
	}
	
	private void hanoi(int n, int from, int to, List<int[]> process) {
		if (n == 1) {
			process.add(new int[] {from, to});
			return;
		}
		
		int empty = 6 - from - to;
		
		hanoi(n-1, from, empty, process); // ① n-1개를 옆 기둥으로
		hanoi(1, from, to, process);      // ② 가장 큰 원반을 from → to
		hanoi(n-1, empty, to, process);   // ③ n-1개를 옆 기둥 → to
	}
	
}
