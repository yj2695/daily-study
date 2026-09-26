package CH03;

/**
 * CH3 배열
 * 
 * 2) 삼각 달팽이
 * 
 * - n x n 2차원 배열 선언
 * - 숫자를 채울 현재 위치를 (0, 0)으로 설정
 * - 방향에 따라 이동할 수 없을 때까지 반복하면서 숫자 채우기
 *   - 아래로 이동하면서 숫자 채우기
 *   - 오른쪽으로 이동하면서 숫자 채우기
 *   - 왼쪽 위로 이동하면서 숫자 채우기
 * - 채워진 숫자를 차례대로 1차원 배열에 옮겨서 반환
 */
public class P02 {
	private static final int[] dx = {0, 1, -1};
	private static final int[] dy = {1, 0, -1};
	
	public int[] solution(int n) {
		int[][] triangle = new int[n][n];
		int v = 1;
		int x = 0;
		int y = 0;
		int d = 0;
		
		while(true) {
			// ① 현재 위치에 숫자 넣기
			triangle[y][x] = v++;
			
			// ② 현재 방향으로 다음 위치 계산
			int nx = x + dx[d];
			int ny = y + dy[d];
			
			// ③ 다음 위치로 갈 수 있나? - 배열 밖으로 나가는 경우, 이미 숫자가 있는 경우 확인
			if (nx == n || ny ==n || nx == -1 || ny == -1 || triangle[ny][nx] !=0) {
				// ④ 방향 변경
				d = (d + 1)%3;
				
				// ⑤ 바뀐 방향의 다음 위치 계산
				nx = x + dx[d];
				ny = y + dy[d];
				
				// ⑥ 방향을 바꿔도 못 가면 끝
				if (nx == n || ny == n || nx == -1 || ny == -1 || triangle[ny][nx] !=0) {
					break;
				}
				
				// ⑦ 갈 수 있으면 그 위치로 이동
				x = nx;
				y = ny;
			}
		}
		
		//	[1,  0,  0,  0]
		//	[2,  9,  0,  0]
		//	[3,  8,  7,  0]
		//	[4,  5,  6, 10]
		// => [1, 2, 9, 3, 8, 7, 4, 5, 6, 10]
		int[] result = new int[v - 1];
		int index = 0;
		for (int i=0; i<n; i++) {
			for (int j=0; j<=i; j++) {
				result[index++] = triangle[i][j];
			}
		}
		
		return result;
	}
}
