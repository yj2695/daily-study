package CH11;

import java.util.Stack;

/**
 * CH11 자주 등장하는 자료 구조 - 스택
 * 
 * 44) 주식 가격
 * https://school.programmers.co.kr/learn/courses/30/lessons/42584
 * 
 */
public class P44 {
	public int[] solution (int[] prices) {
		int[] answer = new int[prices.length];
		
		Stack<Integer> stack = new Stack(); // 인덱스가 들어감 stack = [0, 1, 2 ...]
		
		// 가격이 떨어진 인덱스 찾기
		for (int i = 0; i < prices.length; i++) {
			while (!stack.isEmpty() && prices[stack.peek()] > prices[i]) {
				int index = stack.pop();
				answer[index] = i - index;
			}
			
			stack.push(i);
		}
		
		// 가격이 떨어지지 않고 유지된 시간 구하기
		while (!stack.isEmpty()) {
			int index = stack.pop();
			answer[index] = prices.length - index - 1;
		}
		
		return answer;
	}
}
