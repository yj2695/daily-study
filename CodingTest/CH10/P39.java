package CH10;

import java.util.Arrays;

/**
 * CH10 동적 프로그래밍
 * 
 * 39) 정수 삼각형
 * https://school.programmers.co.kr/learn/courses/30/lessons/43105
 * 
 */
public class P39 {
	
	private final int[][] mem = new int[501][501];
	
	public int solution(int[][] triangle) {
		for (int[] row : mem) {
			Arrays.fill(row, -1);
		}
		
		return max(0, 0, triangle);
	}
	
	private int max(int x, int y, int[][] triangle) {
		if (y == triangle.length) return 0;
		
		// 메모이제이션 한 값 있으면 리턴
		if (mem[x][y] != -1) return mem[x][y];
		
		return mem[x][y] = triangle[y][x] + Math.max(max(x, y+1, triangle), max(x+1, y+1, triangle));
	}
	
}
