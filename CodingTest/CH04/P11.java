package CH04;

/**
 * CH4 문자열
 * 
 * 11) 문자열 내 p와 y의 개수
 */
public class P11 {
	boolean solution(String s) {
		s = s.toLowerCase();
		
		int ps = s.length() - s.replace("p", "").length();
		int ys = s.length() - s.replace("y", "").length();
		
		return ps == ys;
	}
	
//	boolean solution(String s) {
//		int ps = 0;
//		int ys = 0;
//		
//		for (char c : s.toCharArray()) {
//			switch (c) {
//			case 'p', 'P' -> ps++;
//			case 'y', 'Y' -> ys++;
//			}
//		}
//		
//		return ps == ys;
//	}
}