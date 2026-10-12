package CH11;

import java.util.Stack;

/**
 * CH11 자주 등장하는 자료 구조 - 스택
 * 
 * 43) 괄호 회전하기
 * https://school.programmers.co.kr/learn/courses/30/lessons/76502
 */
public class P43 {
	public int solution (String s) {
		char[] str = s.toCharArray();
		
		int count = 0;
		for (int offset = 0; offset < str.length; offset++) {
			if (isCorrect(str, offset)) {
				count++;
			}
		}
		
		return count;
	}
	
	private boolean isCorrect(char[] str, int offset) {
		Stack<Character> stack = new Stack();
		
		for (int i = 0; i < str.length; i++) {
			char c = str[(offset + i) % str.length];
			
			switch(c) {
			case '(' -> stack.push(')');
			case '{' -> stack.push('}');
			case '[' -> stack.push(']');
			case ')', '}', ']' -> {
				if (stack.isEmpty()) return false;
				if (stack.pop() != c) return false;
			}
			}
		}
		
		return stack.isEmpty();
	}
}
