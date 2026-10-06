package CH10;

import java.util.Arrays;

/**
 * CH10 동적 프로그래밍
 * 
 * 38) 피보나치 수
 * https://school.programmers.co.kr/learn/courses/30/lessons/12945
 * 
 */
public class P38 {
	
	private final int[] mem = new int[100001];
	
	public int solution(int n) {
		Arrays.fill(mem, -1);
		
		for (int i = 0; i <= n; i++) {
			fibonacci(n);
		}
		
		return fibonacci(n);
	}
	
	private int fibonacci(int n) {
		// 이미 저장한 값 있으면 리턴
		if (mem[n] != -1) return mem[n];
		
		if (n == 0 || n == 1) return n;
		
		return mem[n] = (fibonacci(n-1) + fibonacci(n-2)) % 1234567;
	}
	
}
