package CH11;

import java.util.Stack;

/**
 * CH11 자주 등장하는 자료 구조 - 스택
 * 
 * 42) 올바른 괄호
 * https://school.programmers.co.kr/learn/courses/30/lessons/12909
 * 
 */
public class P42 {
	public boolean solution (String s) {
		Stack<Character> stack = new Stack();
		
		for (char c : s.toCharArray()) {
			switch (c) {
			case '(' -> stack.push(c);
			case ')' -> {
				if (stack.isEmpty()) return false;
				stack.pop();
			}
			}
		}
		
		return stack.isEmpty();
	}
}
