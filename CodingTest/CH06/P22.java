package CH06;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * CH6 완전탐색
 * 
 * 22) 불량 사용자
 */
public class P22 {
	public int solution(String[] user_id, String[] banned_id) {
		// 각 banned_id에 들어갈 수 있는 user_id 후보 목록 구하기
		// bans[0] = [frodo, fradi]
		// bans[1] = [abc123]
		String[][] bans = Arrays.stream(banned_id)
				.map(banned -> banned.replace('*', '.'))
				.map(banned -> Arrays.stream(user_id)
						.filter(id -> id.matches(banned))
						.toArray(String[]::new))
				.toArray(String[][]::new);
		
		// 최종적으로 가능한 조합 개수 세기
		// banSet
		// ├─ {frodo, abc123}
		// └─ {fradi, abc123}
		Set<Set<String>> banSet = new HashSet<>();
		count(0, new HashSet<>(), bans, banSet);
		
		return banSet.size();
	}
	
	// index : 현재 몇 번째 banned_id를 처리하고 있는가
	// banned : 지금까지 선택한 user_id
	
	private void count(int index, Set<String> banned, String[][] bans, Set<Set<String>> banSet) {
		if (index == bans.length) { // 모든 banned_id 처리한 경우 최종 조합 리턴
			banSet.add(new HashSet<>(banned));
			return;
		}
		
		for (String id : bans[index]) {
			if (banned.contains(id)) continue; // 이미 선택한 user_id라면 건너뛰기
			banned.add(id);
			count(index + 1, banned, bans, banSet);
			banned.remove(id); // 백트래킹. 선택 취소
		}
	}
	
}
