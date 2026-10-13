package CH11;

import java.util.LinkedList;
import java.util.Queue;

/**
 * CH11 자주 등장하는 자료 구조 - 스택
 * 
 * 46) 다리를 지나는 트럭
 * https://school.programmers.co.kr/learn/courses/30/lessons/42583
 * 
 */
public class P46 {
	public int solution(int bridgeLength, int weight, int[] truckWeights) {
		int bridgeWeight = 0;
		Queue<Integer> bridge = new LinkedList<>();
		
		for (int i = 0; i < bridgeLength; i++) {
			bridge.add(0);
		}
		
		int time = 0;
		int truckIndex = 0;
		
		// 트럭이 모두 지나갈때까지 반복
		while (truckIndex < truckWeights.length) {
			bridgeWeight -= bridge.poll(); // 현재 다리 위 트럭 무게
			int truckWeight = truckWeights[truckIndex]; // 다음 트럭 무게
			
			// 현재 다리 위 트럭 + 다음 트럭  무게가 제한 무게보다 작다면 
			if (bridgeWeight + truckWeight <= weight) {
				// 다음 트럭 지나간다
				bridge.add(truckWeight);
				bridgeWeight += truckWeight;
				truckIndex++;
			} else { // 제한 무게 넘는 다면 그냥 지나가기
				bridge.add(0);
			}
			
			time++;
		}
		
		// 대기 중 트럭이 없어지면 다리 위 트럭이 없어질때까지 반복한다.
		while (bridgeWeight > 0) {
			bridgeWeight -= bridge.poll();
			time++;
		}
		
		return time;
	}
}
