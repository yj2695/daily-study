package CH04;

/**
 * CH4 문자열
 * 
 * 13) 문자열 다루기 기본
 */
public class P13 {
	public boolean solution(String s) {
		return s.matches("[0-9]{4} | [0-9]{6}");
	}
	
//	public boolean solution(String s) {
//		if (s.length() != 4 && s.length() != 6) {
//			return false;
//		}
//		
//		for (char c : s.toCharArray) {
//			if (!Character.isDigit(c))
//				return false;
//		}
//		
//		return true;
//	}
}