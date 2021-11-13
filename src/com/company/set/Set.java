package com.company.set;

public interface Set<E> {

	boolean add(E e); // 정상적으로 추가 되면 true, else false

	boolean remove(Object o); // 정상적으로 삭제 되면 true, else false

	boolean contains(Object o); // Set에 요소가 포함되면 true, else false

	boolean equals(Object o); // 비교할 집합과 동일한 경우 true, else false

	boolean isEmpty(); // 비어있으면 true, else false

	int size();

	void clear();

}
