package CH05;

/**
 * CH5 재귀
 * 
 * 15) 쿼드압축 후 개수 세기
 * 
 * 1. 상태
 * (offsetX, offsetY, size) = 좌표 (offsetX, offsetY)에서 시작하여
 * 가로 길이와 세로길이가 size인 정사각형을 압축했을 때 남아있는 0과 1의 개수
 * 
 * 2. 종료조건
 * - 모든 원소가 0이거나, 모든 원소가 1일 때
 * (offsetX, offsetY, size) = {0:1, 1:0} or {0:0, 1:1}
 * 
 * 3. 점화식
 * (offsetX, offsetY , size) = (offsetX, offsetY, size/2)
 *                            + (offsetX + size/2, offsetY, size/2)
 *                            + (offsetX, offsetY + size/2, size/2)
 *                            + (offsetX + size/2, offsetY + size/2, size/2)
 */
public class p15 {
	public int[] solution(int[][] arr) {
		Count count = count(0, 0, arr.length, arr);
		
		return new int[] {count.zero, count.one};
	}
	
	private static class Count {
		public final int zero;
		public final int one;
		
		public Count(int zero, int one) {
			this.zero = zero;
			this.one = one;
		}
		
		public Count add(Count other) {
			return new Count(zero + other.zero, one + other.one);
		}
	}
	
	private Count count(int offsetX, int offsetY, int size, int[][] arr) {
		int h = size/2;
		
		for(int x = offsetX; x < offsetX + size; x++) {
			for (int y = offsetY; y < offsetY + size; y++) {
				if (arr[y][x] != arr[offsetY][offsetX]) {
					return count(offsetX, offsetY, h, arr)
							.add(count(offsetX + h, offsetY, h, arr))
							.add(count(offsetX, offsetY + h, h, arr))
							.add(count(offsetX + h, offsetY + h, h, arr));
				}
			}
		}
		
		if (arr[offsetY][offsetX] == 1) {
			return new Count(0, 1);
		}
		
		return new Count(1, 0);
	}
	
}
