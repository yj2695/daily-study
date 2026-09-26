package CH04;

/**
 * CH4 문자열
 * 
 * 5) 자연수 뒤집어 배열로 만들기
 * 
 * - 입력받은 숫자를 문자열로 반환
 * - 문자열 뒤집기
 * - 뒤집힌 문자열을 문자의 배열로 반환
 * - 배열의 각 문자를 정수로 반환
 */
public class P05 {
	public int[] solution(long n) {
		String str = Long.toString(n);
		String reversed = new StringBuilder(str).reverse().toString();
		char[] arr = reversed.toCharArray();
		
		int[] result = new int[arr.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = arr[i] - '0';
		}
		
		return result;
	}
}