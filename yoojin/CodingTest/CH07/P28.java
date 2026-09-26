package CH07;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * CH7 정렬
 * 
 * 28) 가장 큰 수
 */
public class P28 {
	public String solution(int[] numbers) {
		return Arrays.stream(numbers)
				.mapToObj(String::valueOf) // 숫자 -> 문자열
				.sorted((s1, s2) -> { // 두개씩 비교해서 더 큰 조합이 앞에 오도록 정렬
					int original = Integer.parseInt(s1 + s2);
					int reversed = Integer.parseInt(s2 + s1);
					
					return reversed - original;
				})
				.collect(Collectors.joining(""))
				.replaceAll("^0+", "0"); // 0 처리
	}
}
