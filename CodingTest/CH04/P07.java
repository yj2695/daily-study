package CH04;

/**
 * CH4 문자열
 * 
* 7) 이상한 문자 만들기
* 
* - 문자열의 모든 문자에 대해 반복
*   - 문자가 공백 문자일 경우 
*     - 그대로 이어 붙이기
*     - 다음 등장하는 알파벳은 대문자
*
*   - 문자가 공백 문자가 아닌 경우
*     - 대/소문자 변환하여 이어 붙이기
*     - 다음 등장하는 알파벳의 대/소문자는 현재 반환하는 문자와 반대
*/
public class P07 {
	public String solution(String s) {
		StringBuilder builder = new StringBuilder();
		boolean toUpper = true;
		
		for (char c : s.toCharArray()) {
			if (!Character.isAlphabetic(c)) {
				builder.append(c);
				toUpper = true;
			} else {
				if (toUpper) {
					builder.append(Character.toUpperCase(c));
				} else {
					builder.append(Character.toLowerCase(c));
				}
				toUpper = !toUpper;
			}
		}
		
		return builder.toString();
	}
}