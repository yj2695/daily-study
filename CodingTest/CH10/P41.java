package CH10;

import java.util.Arrays;

/**
 * CH10 동적 프로그래밍
 * 
 * 41) 사칙연산
 * https://school.programmers.co.kr/learn/courses/30/lessons/1843
 * 
 * solution()
 *    ↓
 * max(전체 식) / min(전체 식)
 *    ↓
 * 식을 연산자 기준으로 둘로 나눔
 *    ↓
 * 왼쪽 최대값 구함 / 왼쪽 최소값 구함
 *    ↓
 * 오른쪽 최대값 또는 최소값 구함
 *    ↓
 * 계산
 *    ↓
 * 가장 큰 값 선택 / 가장 작은 값 선택
 *    ↓
 * 메모장(maxMem)에 저장 / minMem에 저장
 * 
 */
public class P41 {
	public int solution(String[] arr) {
		for (int[] row : maxMem) {
			Arrays.fill(row, Integer.MIN_VALUE);
		}
		
		for (int[] row : minMem) {
			Arrays.fill(row, Integer.MIN_VALUE);
		}
		
		return max(0, arr.length, arr);
	}
	
	private final int[][] maxMem = new int[202][202];
	private int max(int start, int end, String[] arr) {
		// 메모이제이션 있으면 해당값 리턴
		if (maxMem[start][end] != Integer.MIN_VALUE) {
			return maxMem[start][end];
		}
		
		if (end - start == 1) return Integer.parseInt(arr[start]);
		
		int max = Integer.MIN_VALUE;
		for (int i = start + 1; i < end; i +=2) {
			int l = max(start, i, arr);
			int v;
			
			if (arr[i].equals("+")) { // 왼쪽 식 최대값 + 오른쪽 식 최대값
				int r = max(i + 1, end, arr);
				v = l + r;
			} else { // 왼쪽 식 최대값 + 오른쪽 식 최소값
				int r = min(i + 1, end, arr);
				v = l - r;
			}
			
			// 최대값 선택
			if (v > max) max = v;
		}
		
		return maxMem[start][end] = max; // 저장 + 반환
	}
	
	private final int[][] minMem = new int[202][202];
	private int min(int start, int end, String[] arr) {
		if (minMem[start][end] != Integer.MIN_VALUE) {
			return minMem[start][end];
		}
		
		if (end - start == 1) return Integer.parseInt(arr[start]);
		
		int min = Integer.MAX_VALUE;
		for (int i = start + 1; i < end; i+=2) {
			int l = min(start, i, arr);
			int v;
			
			if (arr[i].equals("+")) { // 왼쪽 식 최소값 + 오른쪽 식 최소값
				int r = min(i + 1, end, arr);
				v = l + r;
			} else { // 왼쪽 식 최소값 + 오른쪽 식 최대값
				int r = max(i + 1, end, arr);
				v = l - r;
			}
			
			if (v < min) min = v;
		}
		
		return minMem[start][end] = min;
	}
	
}
