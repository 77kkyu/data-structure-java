package com.company.arraylist;

import java.util.Arrays;

public class ArrayList<E> implements List<E>, Cloneable {

	private static final int DEFAULT_CAPACITY = 10;     // 기본 크기
	private static final Object[] EMPTY_ARRAY = {}; // 빈 배열

	private int size; // 요소 개수

	Object[] array; // 요소를 넣어줄 배열

	public ArrayList() { // 생성자 ( 초기 공간 할당 x )
		this.array = EMPTY_ARRAY;
		this.size = 0;
	}

	public ArrayList(int capacity) { // 생성자 ( 초기 공간 할당 O )
		this.array = new Object[capacity];
		this.size = 0;
	}

	private void resize() {
		int array_capacity = array.length;
		if (Arrays.equals(array, EMPTY_ARRAY)) { // array is capacity is 0
			array = new Object[DEFAULT_CAPACITY];
			return;
		}

		if (size == array_capacity) { // 용량이 다 찼을 경우
			int new_capacity = array_capacity * 2;
			array = Arrays.copyOf(array, new_capacity);
			return;
		}

		if (size<(array_capacity/2)) {
			int new_capacity = array_capacity /2;
			array = Arrays.copyOf(array, Math.max(new_capacity, DEFAULT_CAPACITY));
			return;
		}

	}

	@Override
	public boolean add(E value) {
		addLast(value);
		return true;
	}

	public void addLast(E value) {
		if (size == array.length) { // 꽉차있는 상태라면 용적 재할당
			resize();
		}
		array[size] = value; // 마지막 위치에 요소 추기
		size++; // 사이즈 1 증가
	}

	@Override
	public void add(int index, E value) {

		if (index > size || index < 0) { // 영역을 벗어날 경우 예외처리
			throw new IndexOutOfBoundsException();
		}

		if (index == size) { // index가 마지막 위치라면 addLast 메소드로 요소추가하기
			addLast(value);
		}else {
			if (size == array.length) { // 꽉차있다면 용적 재할당
				resize();
			}
			for(int i=size; i>index; i--) {
				array[i] = array[i-1];
			}
			array[index] = value; // index 위치에 요소 할당
			size++;
		}

	}

	public void addFirst(E value) {
		add(0, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public E remove(int index) {

		if (index >= size || index < 0) { // 예외 처리
			throw new IndexOutOfBoundsException();
		}

		E element = (E) array[index]; // 삭제될 요소를 반환하기 위해 임시로 담아둠
		array[index] = null;

		for (int i=index; i<size; i++) { // 삭제한 요소의 뒤에 있는 모든 요소들을 한칸씩 당겨옴
			array[i] = array[i+1];
			array[i+1] = null;
		}

		size--;
		resize();
		return element;
	}

	@Override
	public boolean remove(Object value) {

		int index = indexOf(value); // 삭제하고자 하는 요소의 인덱스 찾기
		if (index == -1) { // -1이라면 array에 요소가 없으므로 false 반환
			return false;
		}
		remove(index); // index 위치에 있는 요소 삭제
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E get(int index) {
		if (index >= size || index < 0) { // 범위에 벗어나면 예외 발생
			throw new IndexOutOfBoundsException();
		}
		return (E) array[index];
	}

	@Override
	public void set(int index, E value) {
		if (index >= size || index < 0) { // 범위에 벗어나면 예외 발생
			throw new IndexOutOfBoundsException();
		}else {
			array[index] = value;
		}
	}

	@Override
	public boolean contains(Object value) {
		if (indexOf(value) >= 0) { // 0 이상이면 요소가 존재
			return true;
		}else {
			return false;
		}
	}

	@Override
	public int indexOf(Object value) {
		int i=0;
		for(i=0; i<size; i++) { // value와 같은 객체(요소 값)일 경우 i(위치) 반환
			if (array[i].equals(value)) {
				return i;
			}
		}
		// 일치하는 것이 없을경우 -1 반환
		return -1;
	}

	public int lastIndexOf(Object value) {
		for (int i=size-1; i>=0; i--) {
			if (array[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int size() {
		return size; // 요소 개수 반환
	}

	@Override
	public boolean isEmpty() {
		return size == 0; // 요소가 0개일 경우 비어있다는 의미이므로 true 반환
	}

	@Override
	public void clear() {
		for (int i=0; i<size; i++) { // 모든 공간을 null 처리 해준다
			array[i] = null;
		}
		size = 0;
		resize();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		ArrayList<?> cloneList = (ArrayList<?>) super.clone(); // 새로운 객체 생성

		cloneList.array = new Object[size]; // 새로운 객체의 배열도 생성해주어야함 (객체는 얕은복사가 되기 떄문임)

		System.arraycopy(array, 0, cloneList.array, 0, size); // 배열의 값을 복사함

		return cloneList;
	}

	public Object[] toArray() {
		return Arrays.copyOf(array, size);
	}

	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {
		if (a.length < size) {
			// copyOf(원본 배열, 복사할 길이, Class<? extends T[]> 타입)
			return (T[]) Arrays.copyOf(array, size, a.getClass());
		}
		// 원본배열, 원본배열 시작위치, 복사할 배열, 복사할배열 시작위치, 복사할 요소 수
		System.arraycopy(array, 0, a, 0, size);
		return a;
	}

}
