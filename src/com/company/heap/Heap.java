package com.company.heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Heap<E> {

	private final Comparator<? super E> comparator; // 정렬
	private static final int DEFAULT_CAPACITY = 10; // 최소 기본 용적 (10)
	private int size; // 요소 개수
	private Object[] array; // 요소를 담을 배열

	public Heap() { // 생성자1 초기 공간 할당 X
		this(null);
	}

	public Heap(int capacity) { // 생성자2 초기 공간 할당 O
		this(capacity, null);
	}

	public Heap(Comparator<? super E> comparator) {
		this.comparator = comparator;
		this.size = 0;
		this.array = new Object[DEFAULT_CAPACITY];
	}

	public Heap(int capacity, Comparator<? super E> comparator) {
		this.array = new Object[capacity];
		this.size = 0;
		this.comparator = comparator;
	}

	private int getParent(int index){ // 인자 인덱스의 부모 노드 인덱스를 반환
		return index/2;
	}

	private int getLeftChild(int index) { // 인자 인덱스의 왼쪽 자식 노드 인덱스를 반환
		return index*2;
	}

	private int getRightChild(int index) { // 받은 인덱스의 오른쪽 자식 노드 인덱스를 반환
		return index*2+1;
	}

	private void resize(int newCapacity) {

		Object[] newArray = new Object[newCapacity];

		for (int i=1; i<=size; i++) { // 새 배열에 기존에 있던 배열의 요소들을 모두 복사
			newArray[i] = array[i];
		}

		/*
		 *  현재 배열은 GC 처리를 위해 null로 처리한 뒤,
		 *  새 배열을 연결해준다.
		 */
		this.array = null;
		this.array = newArray;

	}

	public void add(E value) {

		if (size+1 == array.length) { // 배열 용적이 꽉 차있을 경우 용적을 두배로 늘려준다
			resize(array.length * 2);
		}
		siftUp(size+1, value); // 가장 마지막에 추가 되는 위치와 넣을 값(타겟)을 넘겨줌
		size++; // 정상적으로 재배치가 끝나면 사이즈를 증가

	}

	private void siftUp(int idx, E target) { // idx = 추가할 노드의 인덱스 , target = 재배치 할 노드
		// comparator가 존재할 경우 comparator 을 인자로 넘겨준다.
		if (comparator != null) {
			siftUpComparator(idx, target, comparator);
		}else {
			siftUpComparable(idx, target);
		}

	}

	@SuppressWarnings("unchecked")
	private void siftUpComparator(int idx, E target, Comparator<? super E> comp) { // Comparator을 이용한 sift-up

		// root노드보다 클 때까지만 탐색한다.
		while(idx > 1) {
			int parent = getParent(idx);	// 삽입노드의 부모노드 인덱스 구하기
			Object parentVal = array[parent];	// 부모노드의 값

			// 타겟 노드 값이 부모노드보다 크면 반복문 종료
			if(comp.compare(target, (E) parentVal) >= 0) {
				break;
			}

			/*
			 * 부모노드가 타겟노드보다 크므로
			 * 현재 삽입 될 위치에 부모노드 값으로 교체해주고
			 * 타겟 노드의 위치를 부모노드의 위치로 변경해준다.
			 */
			array[idx] = parentVal;
			idx = parent;
		}

		// 최종적으로 삽입될 위치에 타겟 노드 값을 저장해준다.
		array[idx] = target;
	}

	@SuppressWarnings("unchecked")
	public E remove() {
		if (array[1] == null) { // root이 비어있을 경우 예외처리
			throw new NoSuchElementException();
		}

		E result = (E) array[1]; // 삭제된 요소를 반환하기 위한 임시 변수
		E target = (E) array[size]; // 타겟이 될 요소
		array[size] = null; // 타켓의 노드 비우기

		// 삭제할 노드의 인덱스와 이후 재배치 할 타겟 노드를 넘겨줌
		siftDown(1, target); // 루트 노드가 삭제되므로 1을 넘겨준다

		return result;

	}

	private void siftDown(int idx, E target) { // idx = 삭제할 노드의 인덱스 , target = 재배치 할 노드
		if (comparator != null) { // comparator가 존재할 경우 comparator 을 인자로 넘겨준다.
			siftDownComparator(idx, target, comparator);
		}else {
			siftDownComparable(idx, target);
		}
	}

	@SuppressWarnings("unchecked")
	private void siftDownComparator(int idx, E target, Comparator<? super E> comp) { // Comparator을 이용한 sift-down

		array[idx] = null;	// 삭제 할 인덱스의 노드를 삭제
		size--;

		int parent = idx;	// 삭제노드부터 시작 할 부모를 가리키는 변수
		int child;	// 교환 될 자식을 가리키는 변수

		// 왼쪽 자식 노드의 인덱스가 요소의 개수보다 작을 때 까지 반복
		while((child = getLeftChild(parent)) <= size) {

			int right = getRightChild(parent);	// 오른쪽 자식 인덱스

			Object childVal = array[child];	// 왼쪽 자식의 값 (교환 될 값)

			/*
			 *  오른쪽 자식 인덱스가 size를 넘지 않으면서
			 *  왼쪽 자식이 오른쪽 자식보다 큰 경우
			 *  재배치 할 노드는 작은 자식과 비교해야하므로 child와 childVal을
			 *  오른쪽 자식으로 바꿔준다.
			 */
			if(right <= size && comp.compare((E) childVal, (E) array[right]) > 0) {
				child = right;
				childVal = array[child];
			}

			// 재배치 할 노드가 자식 노드보다 작을경우 반복문을 종료한다.
			if(comp.compare(target ,(E) childVal) <= 0){
				break;
			}

			/*
			 *  현재 부모 인덱스에 자식 노드 값을 대체해주고
			 *  부모 인덱스를 자식 인덱스로 교체
			 */
			array[parent] = childVal;
			parent = child;
		}

		// 최종적으로 재배치 되는 위치에 타겟이 된 값을 넣어준다.
		array[parent] = target;

		/*
		 *  용적의 사이즈가 최소 용적보다는 크면서 요소의 개수가 전체 용적의 1/4일경우
		 *  용적을 반으로 줄임(단, 최소용적보단 커야함)
		 */
		if(array.length > DEFAULT_CAPACITY && size < array.length / 4) {
			resize(Math.max(DEFAULT_CAPACITY, array.length / 2));
		}

	}

	@SuppressWarnings("unchecked")
	private void siftUpComparable(int idx, E target) { // 삽입 할 객체의 Comparable을 이용한 sift-up

		// 타겟 노드가 비교 될 수 있도록 한 변수를 만듬
		Comparable<? super E> comp = (Comparable<? super E>) target;

		while (idx > 1) {
			int parent = getParent(idx);
			Object parentVal = array[parent];

			if (comp.compareTo((E) parentVal) >= 0) {
				break;
			}
			array[idx] = parentVal;
			idx = parent;
		}

		array[idx] = comp;

	}

	@SuppressWarnings("unchecked")
	private void siftDownComparable(int idx, E target) { // Comparable을 이용한 shft-down

		Comparable<? super E> comp = (Comparable<? super E>) target;
		array[idx] = null;
		size--;

		int parent = idx;
		int child;

		while ( (child = getLeftChild(parent)) <= size ) {
			int right = getRightChild(parent);
			Object childVal = array[child];

			if (right <= size && ( (Comparable<? super E>)childVal).compareTo((E) array[right]) > 0) {
				child = right;
				childVal = array[child];
			}

			if (comp.compareTo((E) childVal) <= 0 ) {
				break;
			}
			array[parent] = childVal;
			parent = child;

		}

		array[parent] = comp;
		if(array.length > DEFAULT_CAPACITY && size < array.length / 4) {
			resize(Math.max(DEFAULT_CAPACITY, array.length / 2));
		}

	}

	public int size() {
		return this.size;
	}

	@SuppressWarnings("unchecked")
	public E peek() {
		if (array[1] == null) {
			throw new NoSuchElementException();
		}
		return (E)array[1];
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public Object[] toArray() {
		return Arrays.copyOf(array, size+1);
	}

}
