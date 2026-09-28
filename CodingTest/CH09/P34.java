package CH09;

import java.util.HashSet;
import java.util.Set;

/**
 * CH8 해시
 * 
 * 34) 중복된 문자 제거
 * https://school.programmers.co.kr/learn/courses/30/lessons/120888
 */
public class P34 {
	public String solution(String myString) {
		Set<Character> str = new HashSet<>();
		
		StringBuilder builder = new StringBuilder();
		for (char c : myString.toCharArray()) {
			// 중복 체그
			if (str.contains(c)) continue;
			str.add(c);
			// 결과 저장
			builder.append(c);
		}
		
		return builder.toString();
	}
}
