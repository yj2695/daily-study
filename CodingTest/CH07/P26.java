package CH07;

import java.util.Arrays;

/**
 * CH7 정렬
 * 
 * 26) 문자열 내림차순으로 배치하기
 */
public class P26 {
	public String solution(String s) {
		return s.chars() // 문자 하나하나의 숫자(유니코드)로 바꾸기
				.boxed() // int -> Integer
				.sorted((v1, v2) -> v2 - v1) // 내림차순 정렬
				// 정렬한 문자들을 StringBuilder에 하나씩 붙여서 문자열로 만들기
				.collect(StringBuilder::new, // 문자열을 담을 StringBuilder 하나 생성
						StringBuilder::appendCodePoint, // 문자 붙이기
						StringBuilder::append) // StringBuilder 결과 합치기
				.toString(); // StringBuilder -> String
	}
	
	// 쉬운 방법
	public String solution2(String s) {
		char[] arr = s.toCharArray();
		
		Arrays.sort(arr);
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = arr.length - 1; i >= 0; i--) {
			sb.append(arr[i]);
		}
		
		return sb.toString();
	}
	
}
