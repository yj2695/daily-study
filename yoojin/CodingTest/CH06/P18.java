package CH06;

import java.util.stream.IntStream;

/**
 * CH6 완전탐색
 * 
 * 18) 모의고사
 */
public class P18 {
	private static final int[][] RULES = {
			{1, 2, 3, 4, 5},
			{2, 1, 2, 3, 2, 4, 2, 5},
			{3, 3, 1, 1, 2, 2, 4, 4, 5, 5}
	};
	
	public int[] solution(int[] answers) {
		int[] corrects = new int[3]; 
		// corrects[0] → 1번 사람 맞힌 개수
		// corrects[1] → 2번 사람 맞힌 개수
		// corrects[2] → 3번 사람 맞힌 개수
		int max = 0;
		
		for (int problem = 0; problem < answers.length; problem++) {
			int answer = answers[problem]; // 정답 가져오기
			
			for (int person = 0; person < 3; person++) { // 세 사람 검사
				int picked = getPicked(person, problem);
				if (answer == picked) { // 정답과 이 사람의 답 비교
					if (++corrects[person] > max) { // 맞힌 개수 증가 시킨 후 max 와 비교
						max = corrects[person];
					}
				}
			}
		}
		
		final int maxCorrects = max;
		
		return IntStream.range(0, 3) // 0, 1, 2 만들기
				.filter(i -> corrects[i] == maxCorrects) // 최고점과 같은 사람만 남기기
				.map(i -> i + 1) // 사람 번호니까 1 더해준다 (index 0,1,2 -> 1,2,3)
				.toArray(); // 배열로 만들기
	}
	
	private int getPicked(int person, int problem) { // 이 사람이 이 문제에서 몇번찍었는지 확인
		int[] rule = RULES[person]; // 이 사람의 답 찍는 룰 가져오기
		int index = problem % rule.length; // 문제 개수를 룰 개수로 반복
		
		return rule[index];
	}
	
}
