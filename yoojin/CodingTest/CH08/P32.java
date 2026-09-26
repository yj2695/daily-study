package CH08;

import java.util.Arrays;

/**
 * CH8 이진 탐색
 * 
 * 32) 징검다리
 * https://school.programmers.co.kr/learn/courses/30/lessons/43236
 * 
 * ① mid = "최소 거리를 이 정도로 정해보자"
 * ② 바위를 순서대로 확인
 * ③ 현재 바위 사이 거리가 최소 거리 mid 보다 작아?
 *   - YES : 바위 제거
 *   - NO : 바위 유지
 * ④ 제거한 바위 개수가 n개 이하야?
 *   - YES : mid를 더 크게
 *   - NO : mid를 더 작게
 */
public class P32 {
	public int solution(int distance, int[] rocks, int n) {
		Arrays.sort(rocks);
		
		int left = 1;
		int right = distance;
		
		while (left <= right) {
			int mid = (left + right) / 2;
			
			int removed = 0;
			int previous = 0;
			
			for (int rock : rocks) {
				// 현재 바위와 이전 지점 사이의 거리
				int gap = rock - previous;
				
				if (gap < mid) { // 너무 가까우니 바위 제거
					removed++;	
				} else { // 머니까 다음 바위로 넘어간다
					previous = rock;
				}
			}
			
			// 위 for문에서는 중간 바위들 사이 거리만 확인하므로 마지막 바위와 끝 지점 사이의 거리 별도 확인
			if (distance - previous < mid) {
				removed++;
			}
			
			if (removed <=n) { // 제거한게 작으니까 거리를 더 늘려서 다시 확인하기
				left = mid + 1;
			} else {
				right = mid - 1; // 바위 너무 많이 제거하니까 거리 줄여서 다시 확인하기
			}
		}
		
		return right;
	}
}
