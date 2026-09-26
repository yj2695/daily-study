package CH04;

/**
 * CH4 문자열
 * 
 * 14) 신규 아이디 추천
 */
public class P14 {
	public String solution(String newId) {
		// 1단계 - 모든 대문자를 대응되는 소문자로 치환
		newId = newId.toLowerCase();
		
		// 2단계 - 알파벳 소문자, 숫자, -, _, .를 제외한 모든 문자 제거
		newId = newId.replaceAll("[^a-z0-9\\-_.]", "");
		
		// 3단계 - 마침표(.)가 2번 이상 연속된 부분을 하나의 마침표(.)로 치환
		newId = newId.replaceAll("\\.+", ".");
		
		// 4단계 - 마침표(.)가 처음이나 끝에 위치한다면 제거
		newId = newId.replaceAll("^\\.+|\\.+$", "");
		
		// 5단계 - 빈 문자열이라면, new_id에 "a" 대입
		if (newId.isEmpty()) {
			newId = "a";
		}
		
		// 6단계 - 길이가 16자 이상이라면, 첫 15개의 문자를 제외한 나머지 문자는 제거
		if (newId.length() >= 16) {
			newId = newId.substring(0, 15);
			newId = newId.replaceAll("\\.+$", ""); // .으로 끝나는 거 한번 더 제거
		}
		
		// 7단계 - 길이가 2자 이하라면, 마지막 문자를 길이가 3이 될 때까지 반복
		while (newId.length() < 3) {
			newId += newId.charAt(newId.length() - 1);
		}
		
		return newId;
	}
}