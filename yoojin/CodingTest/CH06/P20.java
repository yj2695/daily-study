package CH06;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * CH6 완전탐색
 * 
 * 20) 수식 최대화
 */
public class P20 {
	private static final String[][] precedences = {
			"+-*".split(""),
			"+*-".split(""),
			"-+*".split(""),
			"-*+".split(""),
			"*+-".split(""),
			"*-+".split(""),
	};
	
	public long solution(String expression) {
		// StringTokenizer(문자열, 구분자) : 긴 문자열을 특정 기준으로 잘라서 하나씩 꺼내주는 도구
		// 문자열을 숫자와 연산자로 나누기
		// true가 구분자인 +, -, *도 버리지 않고 가져온다는 의미
		StringTokenizer tokenizer = new StringTokenizer(expression, "+-*", true);
		
		List<String> tokens = new ArrayList<>();
		
		while(tokenizer.hasMoreTokens()) {
			tokens.add(tokenizer.nextToken());
		}
		
		long max = 0;
		for (String[] precedence : precedences) {
			long value = Math.abs(calculate(new ArrayList<>(tokens), precedence));
			
			if (value > max) {
				max = value;
			}
		}
		
		return max;
	}
	
	private long calculate(long lhs, long rhs, String op) {
		return switch (op) {
		case "+" -> lhs + rhs;
		case "-" -> lhs - rhs;
		case "*" -> lhs * rhs;
		default -> 0;
		};
	}
	
	private long calculate(List<String> tokens, String[] precedence) {
		for (String op : precedence) {
			for (int i = 0; i < tokens.size(); i++) { // ["100", "-", "200", "*", "300"]
				if (tokens.get(i).equals(op)) { // 찾는 연산자인 경우 연산
					long lhs = Long.parseLong(tokens.get(i - 1));
					long rhs = Long.parseLong(tokens.get(i + 1));
					long result = calculate(lhs, rhs, op);
					tokens.remove(i - 1);
					tokens.remove(i - 1);
					tokens.remove(i - 1);
					tokens.add(i - 1, String.valueOf(result));
					i -= 2;
				}
			}
		}
		
		return Long.parseLong(tokens.get(0));
	}
	
}
