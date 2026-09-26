package CH07;

import java.util.Arrays;

/**
 * CH7 정렬
 * 
 * 25) H-Index
 * 
 * h = 3이라면, 3번 이상 인용된 논문이 3편 이상 있는가?
 */
public class P25 {
	public int solution(int[] citations) {
		Arrays.sort(citations); // h번 이상인 논문이 몇 개 있는지 쉽게 찾기 위해서 정렬
		
		// 가장 큰 h부터 확인
		for (int h=citations.length; h>=1; h--) {
			if (isValid(citations, h)) return h;
		}
		
		return 0;
	}
	
	private boolean isValid(int[] citations, int h) {
		// 뒤에서 h개를 뽑았을 때, 그 시작점의 값이 h 이상이면 성공
		// [0, 1, 3, 5, 6]
		//   뒤에서 3개
		//         ↓
		// [0, 1 | 3, 5, 6]
		int index = citations.length - h;
		return citations[index] >= h;
	}
	
}
