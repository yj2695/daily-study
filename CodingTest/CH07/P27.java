package CH07;

import java.util.Arrays;

/**
 * CH7 정렬
 * 
 * 27) 문자열 내 마음대로 정렬하기
 */
public class P27 {
	public String[] solution(String[] strings, int n) {
		Arrays.sort(strings, (s1, s2) -> {
			if (s1.charAt(n) != s2.charAt(n)) { // 서로 다르면 n번째 문자 이용해 정렬
				return s1.charAt(n) - s2.charAt(n);
			}
			
			return s1.compareTo(s2); // n번째 원소가 같다면 사전 순 정렬
		});
		
		return strings;
	}
	
}
