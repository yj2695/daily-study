package CH08;

import java.util.Arrays;

/**
 * CH8 이진 탐색
 * 
 * 31) 입국심사
 * https://school.programmers.co.kr/learn/courses/30/lessons/43238
 * 
 * N분 안에 몇 명 처리가 가능할까? 로 전환해서 생각하기
 * ex) 28분이라는 시간이 주어졌다고 하면 몇 명을 처리할 수 있지?
 * 
 * ① 시간을 정하기
 * ② 그 시간 동안 처리 가능한 사람 수를 계산하기
 * ③ n명 이상 처리 가능?
 *   - YES : 시간을 줄이기
 *   - NO : 시간을 늘리기
 * ④ n명을 처리할 수 있는 최소 시간 구하기
 * 
 */
public class P31 {
	public long solution(int n, int[] times) {
		// 가장 짧은 시간
		long left = 0; 
		// 심사 시간이 가장 오래 걸리는 시간 * 사람 수 로 최대 시간 마지노선을 정한다
		long right = (long) Arrays.stream(times).max().getAsInt() * n;
		
		while (left < right) {
			long mid = (left + right) / 2;
			long count = 0;
			
			for (int time :times) {
				count += mid / time;
			}
			
			if (count >= n) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}
		
		return left;
	}
}
