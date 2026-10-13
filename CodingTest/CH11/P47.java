package CH11;

/**
 * CH11 자주 등장하는 자료 구조 - 그래프
 * 
 * 47) 순위 : 방향 그래프 + 재귀 DFS
 * https://school.programmers.co.kr/learn/courses/30/lessons/49191
 * 
 */
public class P47 {
	public int solution(int n, int[][] results) {
		boolean[][] graph = new boolean[n][n];
		for (int[] edge : results) {
			int u = edge[0] - 1;
			int v = edge[1] - 1;
			
			graph[u][v] = true;
		}
		
		int count = 0;
		for (int u = 0; u < n; u++) {
			int wins = countForward(u, graph, new boolean[n]) - 1;
			int loses = countBackward(u, graph, new boolean[n]) - 1;
			if (wins + loses + 1 == n) {
				count++;
			}
		}
		
		return count;
	}
	
	// 내가 이긴 사람 찾기
	private int countForward(int u, boolean[][] graph, boolean[] isVisited) {
		int count = 1;
		
		for (int v = 0; v < graph[u].length; v++) {
			if (!graph[u][v] || isVisited[v]) continue;
			isVisited[v] = true;
			count += countForward(v, graph, isVisited);
		}
		
		return count;
	}
	
	// 나를 이긴 사람 찾기
	private int countBackward(int u, boolean[][] graph, boolean[] isVisited) {
		int count = 1;
		
		for (int v = 0; v < graph.length; v++) {
			if (!graph[v][u] || isVisited[v]) continue;
			isVisited[v] = true;
			count += countBackward(v, graph, isVisited);
		}
		
		return count;
	}
	
}
