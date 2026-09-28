package CH09;

import java.util.HashMap;
import java.util.Map;

/**
 * CH9 해시
 * 
 * 37) 완주하지 못한 선수
 * https://school.programmers.co.kr/learn/courses/30/lessons/42576
 * 
 */
public class P37 {
	public String solution (String[] participant, String[] completion) {
		Map<String, Integer> map = new HashMap<>();
		
		for (String p : participant) {
			map.put(p, map.getOrDefault(p, 0) + 1);
		}
		
		for (String c : completion) {
			map.put(c, map.get(c) - 1);
			if (map.get(c) - 1 == 0) map.remove(c);
		}
		
		return map.keySet().iterator().next();
	}
}
