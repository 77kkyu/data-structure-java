package com.company.queue;

public interface Queue<E> {

	// 큐의 가장 마지막에 요소를 추가
	// 정상 true
	boolean offer(E e);

	// 큐의 첫 번째 요소를 삭제하고 삭제 된 요소를 반환
	E poll();

	// 큐위 첫 번째 요소를 반환
	E peek();

}
