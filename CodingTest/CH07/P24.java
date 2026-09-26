package CH07;

import java.util.HashSet;
import java.util.Set;

/**
 * CH7 정렬
 * 
 * 24) 두 개 뽑아서 더하기
 */
public class P24 {
	public int[] solution(int[] numbers) {
		Set<Integer> set = new HashSet<>();
		
		for (int i=0; i < numbers.length; i++) {
			for (int j=i+1; j < numbers.length; j++) {
				set.add(numbers[i] + numbers[j]);
			}
		}
		
		return set.stream().mapToInt(Integer::intValue).sorted().toArray();
	}
	
}
