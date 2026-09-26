package CH04;

/**
 * CH4 문자열
 * 
* 6) 시저 암호
* 
* - 입력 문자열의 모든 문자에 대해 반복
*   - 알파벳이 아닌 경우 문잘르 그대로 이어 붙이기
*   - 알파벳인 경우 n만큼 밀어 이어 붙이기
*/
public class P06 {
	public String solution(String s, int n) {
		StringBuilder builder = new StringBuilder();
		for (char c : s.toCharArray()) {
			builder.append(push(c, n));
		}
		
		return builder.toString();
	}
	
	private char push(char c, int n) {
		if (!Character.isAlphabetic(c)) {
			return c;
		}
		
		int offset = Character.isUpperCase(c) ? 'A' : 'a';
		int position = c - offset;
		position = (position + n) % ('Z' - 'A' + 1);
		
		return (char) (offset + position);
	}
}