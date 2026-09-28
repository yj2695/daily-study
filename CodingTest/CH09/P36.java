package CH09;

import java.util.HashSet;
import java.util.Set;

/**
 * CH9 해시
 * 
 * 36) 없는 숫자 더하기
 * https://school.programmers.co.kr/learn/courses/30/lessons/86051
 * 
 */
public class P36 {
	public int solution (int[] numbers) {
		Set<Integer> set = new HashSet<>();
		for (int v : numbers) {
			set.add(v);
		}
		
		int sum = 0;
		for (int i = 0; i <=9; i++) {
			if (set.contains(i)) continue;
			sum += i;
		}
		
		return sum;
	}
}
