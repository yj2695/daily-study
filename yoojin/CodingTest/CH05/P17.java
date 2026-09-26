package CH05;

import java.util.ArrayList;
import java.util.List;

/**
 * CH5 재귀
 * 
 * 17) 모음 사전
 * 
 * 1. 상태
 * (word)
 * word로 시작하는 모든 단어
 * 
 * 2. 종료 조건
 * 길이가 5인 word = word
 * 
 * 3. 점화식
 * (word) = [word] + (word+'A') + (word+'E') + (word+'I') + (word+'O') + (word+'U')
 */
public class P17 {
	private static final char[] CHARS = "AEIOU".toCharArray();
	
	public int solution(String word) {
		List<String> words = new ArrayList<>();
		
		generate("", words);
		
		return words.indexOf(word);
	}
	
	private void generate(String word, List<String> words) {
		words.add(word);
		
		if (word.length() == 5) return;
		
		for(char c : CHARS) {
			generate(word + c, words);
		}
		
	}
	
}
