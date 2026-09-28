package CH09;

import java.util.HashMap;
import java.util.Map;

/**
 * CH9 해시
 * 
 * 35) A로 B 만들기
 * https://school.programmers.co.kr/learn/courses/30/lessons/120886
 * 
 */
public class P35 {
	public int solution (String before, String after) {
		Map<Character, Integer> map = new HashMap<>();
		
		for (char c : before.toCharArray()) {
			map.put(c, map.getOrDefault(c, 0) + 1);
		}
		
		for (char c : after.toCharArray()) {
			if (!map.containsKey(c)) {
				return 0;
			}
			
			map.put(c, map.get(c) - 1);
		}
		
		for (int count : map.values()) {
			if (count != 0) {
				return 0;
			}
		}
		
		return 1;
	}
}
