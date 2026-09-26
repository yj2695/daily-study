package CH04;

/**
 * CH4 문자열
 * 
* 9) 3진법 뒤집기
* 
* - 정수를 3진법으로 변환
* - 변환된 문자열 뒤집기
* - 뒤집은 문자열을 정수로 변환
*/
public class P09 {
	public int solution(int n) {
		String str = Integer.toString(n, 3);
		String reversed = new StringBuilder(str).reverse().toString();
		return Integer.valueOf(reversed, 3);
	}
}