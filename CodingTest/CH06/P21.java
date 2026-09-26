package CH06;

import java.util.HashSet;
import java.util.Set;

/**
 * CH6 완전탐색
 * 
 * 21) 소수 찾기
 */
public class P21 {
	public int solution(String nums) {
		Set<Integer> primes = new HashSet<>();
		
		// 문자열을 숫자 배열로 바꾼다
		int[] numbers = nums.chars()
				.map(c -> c - '0')
				.toArray();
		
		// acc : 지금까지 만든 숫자
		// numbers : 사용할 숫자들
		// isUsed : 각 숫자를 사용했는지
		// primes : 발견한 소수 저장
		getPrimes(
			0,
			numbers,
			new boolean[numbers.length],
			primes
		);
		
		return primes.size();
	}
	
	private boolean isPrime(int n) {
		if (n <= 1) return false;
		
		for (int i = 2; i * i <= n; i++) {
			if (n % i == 0) return false;
		}
		
		return true;
	}
	
	private void getPrimes(int acc, int[] numbers, boolean[] isUsed, Set<Integer> primes) {
		if (isPrime(acc)) primes.add(acc);
		
		for (int i = 0; i < numbers.length; i++) {
			if (isUsed[i]) continue;
			
			int nextAcc = acc * 10 + numbers[i];
			
			isUsed[i] = true;
			getPrimes(nextAcc, numbers, isUsed, primes);
			// 백트래킹
			// 일단 사용해보고 → 그 길을 다 탐색했으면 → 다시 사용하지 않은 상태로 되돌린다.
			// 17 사용 후 71도 해야되니까
			isUsed[i] = false;
		}
	}
	
}
