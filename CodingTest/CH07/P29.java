package CH07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CH7 정렬
 * 
 * 29) 메뉴 리뉴얼
 * 
 * 조합 + HashMap + 재귀
 * 각 손님이 주문한 메뉴에서 가능한 조합을 전부 만들어서, 몇 명이 그 조합을 주문했는지 세는 문제
 * 
 * ① 주문을 알파벳순으로 정렬해서 조합 만들기 : 재귀
 * ③ 조합의 등장 횟수를 세어서 hashmap에 넣기
 * ④ 가장 많이 나온 조합 선택하기
 */
public class P29 {
	
	// 조합별 등장 횟수 : (AB, 1) (AC, 3) (BC, 2) ...
	Map<String, Integer> map = new HashMap<>();
	
	public String[] solution(String[] orders, int[] course) {
		
		//  1. 모든 주문을 확인
		for (String order : orders) {
			char[] chars = order.toCharArray(); // 알파벳순 정렬
			Arrays.sort(chars);
			
			String sortedOrder = new String(chars);
			
			// 2. course 크기의 조합 만들기
			for (int count : course) {
				if (sortedOrder.length() >= count) {
					combination(sortedOrder, 0, count, "");
				}
				
			}
		}
		
		List<String> answer = new ArrayList<>();
		
		for (int count : course) {
			int max = 0;
			
			// 코스 크기의 최대 주문 횟수 찾기 (AB, 1) (AC, 3) => 2개짜리 코스 중 최대 주문 횟수는 3
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				String menu = entry.getKey();
				int orderCount = entry.getValue();
				
				if (menu.length() == count && orderCount >=2) {
					max = Math.max(max, orderCount);
				}
			}
			
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				String menu = entry.getKey();
				int orderCount = entry.getValue();
				
				if (menu.length() == count && orderCount == max) {
					answer.add(menu);
				}
			}
		}
		
		Collections.sort(answer);
		
		return answer.toArray(new String[0]);
	}
	
	// 조합 만들기 (재귀)
	private void combination (String order, int start, int count, String result) {
		// 원하는 개수만큼 뽑았다면 종료
		if (result.length() == count) {
			map.put(result, map.getOrDefault(result, 0) + 1); // hashmap에 넣기
			
			return;
		}
		
		// 다음 메뉴 선택
		for (int i = start; i < order.length(); i++) {
			combination(order, i+1, count, result + order.charAt(i));
		}
	}
	
}
