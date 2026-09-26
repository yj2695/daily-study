package CH08;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CH8 이진 탐색
 * 
 * 30) 순위 검색
 * https://school.programmers.co.kr/learn/courses/30/lessons/72412
 * 
 * ① 조건(info)을 먼저 정리해서 조건별 점수 목록을 생성한다. 점수 목록은 정렬
 * ② query를 하나씩 처리하면서 조건에 맞는 점수 목록을 찾는다 -> 이 때 이진탐색 사용, x점 이상인 사람 수 계산
 */
public class P30 {
	
	Map<String, List<Integer>> map = new HashMap<>();
	
	public int[] solution(String[] info, String[] query) {
		// info를 Map에 저장
		for (String str : info) {
			String[] data = str.split(" ");
			
			String language = data[0];
			String position = data[1];
			String career = data[2];
			String food = data[3];
			int score = Integer.parseInt(data[4]);
			
			// 가능한 조건 조합 만들어서 점수 list에 추가
			makeKey(language, position, career, food, score);
		}
		
		// 점수 리스트 각각을 정렬
		for (List<Integer> scores : map.values()) {
			Collections.sort(scores);
		}
		
		// query 하나씩 처리
		int[] answer = new int[query.length];
		
		for (int i = 0; i < query.length; i++) {
			String[] data = query[i].split(" ");
			
			// data[1,3,5] = "and"
			String language = data[0];
			String position = data[2];
			String career = data[4];
			String food = data[6];
			int score = Integer.parseInt(data[7]);
			
			String key = language + " " + position + " " + career + " " + food;
			
			// 해당 조건(key)의 점수 list 가져오기
			List<Integer> scores = map.get(key);
			
			if (scores == null) {
				 answer[i] = 0;
				 continue;
			}
			
			// score 이상인 첫번째 위치 찾기
			int index = lowerBound(scores, score);
			
			// 그 위치부터 끝까지가 score 이상
			answer[i] = scores.size() - index;
		}
		
		return answer;
	}
	
	private void makeKey(String language, String position, String career, String food, int score) {
		String[] languages = {language, "-"};
		String[] positions = {position, "-"};
		String[] careers = {career, "-"};
		String[] foods = {food, "-"};
		
		for (String l : languages) {
			for (String p : positions) {
				for (String c : careers) {
					for (String f : foods) {
						String key = l + " " + p + " " + c + " " + f;
						
						// 조합에 따른 점수가 map의 리스트에 없으면 추가
						map.computeIfAbsent(key, k -> new ArrayList<>()).add(score);
					}
				}
			}
		}
	}
	
	private int lowerBound(List<Integer> list, int target) {
		int left = 0;
		int right = list.size();
		
		while (left < right) {
			int mid = (left + right) / 2;
			
			if (list.get(mid) >= target) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}
		
		return left;
	}
	
}
