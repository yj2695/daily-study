package CH11;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * CH11 자주 등장하는 자료 구조 - 큐
 * 
 * 45) 기능 개발
 * https://school.programmers.co.kr/learn/courses/30/lessons/42586
 * 
 * ① 각 기능이 며칠 만에 완성되는지 계산
 *              ↓
 * ② 앞 기능의 배포 날짜를 기준으로 잡음
 *              ↓
 * ③ 뒤 기능이 그 날짜 전에 완성된다?
 *     ↓                  ↓
 *    YES                 NO
 *     ↓                  ↓
 * 같이 배포               새로운 배포
 * 
 */
public class P45 {
	public int[] solution(int[] progresses, int[] speeds) {
		Queue<Integer> q = new LinkedList<>();
		
		for (int i = 0; i < progresses.length; i++) {
			q.add(i);
		}
		
		List<Integer> result = new ArrayList<>();
		
		int days = 0; // 현재 배포 묶음의 기준이 되는 날짜
		int count = 0; // 이번에 같이 배포할 기능의 개수
		
		while(!q.isEmpty()) {
			int index = q.poll();
			
			int expiration = (int) Math.ceil((double) (100 - progresses[index]) / speeds[index]);
			
			if (expiration > days) {
				if (days != 0) {
					result.add(count);
					count = 0;
				}
				days = expiration;
			}
			count++;
		}
		
		result.add(count);
		return result.stream().mapToInt(Integer::intValue).toArray();
	}
}
