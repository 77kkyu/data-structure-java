package com.company.arraylist;

public interface List<E> {

	// 리스트에 추가
	boolean add(E value);

	// 리스트 특정 위치에 추가
	void add(int index, E value);

	// 리스트 index에 있는 데이터 삭제
	E remove(int index);

	// 리스트에 특정 데이터 삭제, 동일한 데이터가 여러 개일 경우 처음 발견한 데이터만 삭제
	boolean remove(Object value);

	// 리스트 index로 데이터 호출
	E get(int index);

	// 리스트에 특정 위치에 있는 데이터 교체
	void set(int index, E value);

	// 리스트에 특정 데이터가 있는지 확인
	boolean contains(Object value);

	// 리스트에 특정 데이터가 몇 번째 위치에 있는지 확인
	int indexOf(Object value);

	// 리스트에 있는 데이터의 개수 확인
	int size();

	// 리스트에 데이터가 비어있는지 확인, 데이터가 없으면 true 있으면 false
	boolean isEmpty();

	// 리스트 초기화
	public void clear();

	public Object clone() throws CloneNotSupportedException;

}
