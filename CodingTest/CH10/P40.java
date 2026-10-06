package CH10;

import java.util.Arrays;

/**
 * CH10 동적 프로그래밍
 * 
 * 40) 등굣길
 * https://school.programmers.co.kr/learn/courses/30/lessons/42898
 */
public class P40 {
	
	private final int[][] mem = new int[4][3];
	
	public int solution(int m, int n, int[][] puddles) {
		for (int[] row : mem) {
			Arrays.fill(row, -1);
		}
		
		boolean[][] isPuddle = new boolean[n+1][m+1];
		for (int[] p : puddles) {
			isPuddle[p[1]][p[0]] = true;
		}
		
		return count(1, 1, m, n, isPuddle);
	}
	
	private int count(int x, int y, int w, int h, boolean[][] isPuddle) {
		// 범위 넘으면 리턴 0
		if (x>w || y>h) return 0;
		
		// 물 웅덩이면 리턴 0
		if (isPuddle[y][x]) return 0;
		
		// 메모이제이션 했으면 해당 값 리턴
		if (mem[x][y] != -1) return mem[x][y];
		
		// 도착했으면 방법1 리턴
		if (x == w && y == h) return 1;
		
		int total = count(x+1, y, w, h, isPuddle) + count (x, y+1, w, h, isPuddle);
		
		return mem[x][y] = total % 1000000007;
	}
	
}
