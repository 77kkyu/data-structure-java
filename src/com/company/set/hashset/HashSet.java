package com.company.set.hashset;

import com.company.set.Set;

import java.util.Arrays;

public class HashSet<E> implements Set<E> {

	// 최소 기본 용적이며 2^n 형태가 좋다
	private final static int DEFAULT_CAPACITY = 1 << 4;

	// 3/4이상 채워질 경우 resize하기 위함
	private final static float LOAD_FACTOR = 0.75f;

	Node<E>[] table;    // Node를 저장 할 Node타입 배열
	private int size;   // 요소의 개수

	@SuppressWarnings("unckecked")
	public HashSet() {
		table = (Node<E>[]) new Node[DEFAULT_CAPACITY];
		size = 0;
	}

	// 보조 해시 함수 (상속 방지를 위해 private static final 선언)
	private static final int hash(Object key) {
		int hash;
		if (key == null) {
			return 0;
		}else {
			// hashCode()의 경우 음수가 나올 수 있으므로 절대값을 통해 양수로 변환해준다.
			return Math.abs(hash = key.hashCode()) ^ (hash >>> 16);
		}
	}

	@SuppressWarnings("unchecked")
	private void resize() {
		int newCapacity = table.length * 2;
		final Node<E>[] newTable = (Node<E>[]) new Node[newCapacity]; // 기존 테이블의 두배 용적으로 생성

		for (int i=0; i<table.length; i++) { // 인덱스 0부터 순회
			Node<E> value = table[i]; // 각 인덱스의 첫 번쨰 노드(head)
			if (value == null) { // 해당 값이 없으면 넘어감
				continue;
			}
			table[i] = null; // GC
			Node<E> nextNode; // 현재 노드의 다음 노드를 가리키는 변수

			while (value != null) { // 현재 인덱스에 연결 된 노드들을 순회함
				int idx = value.hash % newCapacity; // 새로운 인덱스를 구함

				/*
				 * 새로 담을 index에 요소(노드)가 존재할 경우
				 * == 새로 담을 newTalbe에 index값이 겹칠 경우 (해시 충돌)
				 */
				if (newTable[idx] != null) {
					Node<E> tail = newTable[idx];
					while (tail.next != null) { // 가장 마지막 노드로 간다
						tail = tail.next;
					}

					/*
					 *  반드시 value가 참조하고 있는 다음 노드와의 연결을 끊어주어야 한다.
					 *  안하면 각 인덱스의 마지막 노드(tail)도 다른 노드를 참조하게 되어버려
					 *  잘못된 참조가 발생할 수 있다.
					 */
					nextNode = value.next;
					value.next = null;
					tail.next = value;
				}else { // 충돌되지 않는다면(=빈 공간이라면 해당 노드를 추가)
					/*
					 *  반드시 value가 참조하고 있는 다음 노드와의 연결을 끊어주어야 한다.
					 *  안하면 각 인덱스의 마지막 노드(tail)도 다른 노드를 참조하게 되어버려
					 *  잘못된 참조가 발생할 수 있다.
					 */
					nextNode = value.next;
					value.next = null;
					newTable[idx] = value;
				}
				value = nextNode; // 다음 노드로 이동
			}
		}
		table = null;
		table = newTable; // 새로 생성한 테이블을 테이블 변수에 연결

	}

	@Override
	public boolean add(E e) {
		return add(hash(e), e) == null;
	}

	private E add(int hash, E key) {

		int idx = hash % table.length;

		if (table[idx] == null) { // table[idx]가 비어있을 경우 새 노드 생성
			table[idx] = new Node<E>(hash, key, null);
		}else { // table[idx]이 존재할 경우 (해시충돌) , 1.객체가 같은 경우 2.객체는 같지 않으나 얻어진 index가 같은 경우

			Node<E> temp = table[idx]; // 현재 위치 노드
			Node<E> prev = null; // temp의 이전 노드

			while (temp != null) { // 첫 노드(table[idx] 부터 탐색

				/*
				 *  만약 현재 노드의 객체가 같은경우(hash값이 같으면서 key가 같을 경우)는
				 *  HashSet은 중복을 허용하지 않으므로 key를 반납(반환)
				 *  (key가 같은 경우는 주소가 같거나 객체가 같은 경우가 존재)
				 */
				if ((temp.hash == hash) && (temp.key == key || temp.key.equals(key))) {
					return key;
				}
				prev = temp;
				temp = temp.next; // 다음 노드로 이동

			}

			prev.next = new Node<E>(hash, key, null); // 마지막 노드에 새 노드를 연결해준다

		}

		size++;
		if (size >= LOAD_FACTOR * table.length) {
			resize();
		}
		return  null;
	}

	@Override
	public boolean remove(Object o) {
		// null이 아니라는 것은 노드가 삭제되었다는 의미
		return remove(hash(o), o) != null;
	}

	private Object remove(int hash, Object key) {

		int idx = hash % table.length;

		Node<E> node = table[idx];
		Node<E> removedNode = null;
		Node<E> prev = null;

		if (node == null) {
			return null;
		}

		while (node != null) {

			if(node.hash == hash && (node.key == key || node.key.equals(key))) { // 같은 노드를 찾았다면

				removedNode = node; //삭제되는 노드를 반환하기 위해 담아둔다.

				if (prev == null) { // 해당노드의 이전 노드가 존재하지 않는 경우 (= head노드인 경우)
					table[idx] = node.next;
					node = null;
				}else { // 그 외엔 이전 노드의 next를 삭제할 노드의 다음노드와 연결해준다.
					prev.next = node.next;
					node = null;
				}
				size--;
				break;	// 삭제되었기 때문에 탐색 종료
			}
			prev = node;
			node = node.next;
		}

		return removedNode;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean contains(Object o) {
		int idx = hash(o) % table.length;
		Node<E> temp = table[idx];

		/*
		 *  같은 객체 내용이어도 hash값은 다를 수 있다.
		 *  하지만, 내용이 같은지를 알아보고 싶을 때 쓰는 메소드이기에
		 *  hash값은 따로 비교 안해주어도 큰 지장 없다.
		 *  단, o가 null인지는 확인해야한다.
		 */
		while (temp != null) {
			// 같은 객체를 찾았을 경우 true리턴
			if ( o == temp.key || (o != null && temp.key.equals(o))) {
				return true;
			}
			temp = temp.next;
		}

		return false;
	}

	@Override
	public void clear() {
		if (table != null && size > 0) {
			for (int i = 0; i < table.length; i++) {
				table[i] = null;	// 모든 노드를 삭제한다.
			}
			size = 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {

		// 만약 파라미터 객체가 현재 객체와 동일한 객체라면 true
		if(o == this) {
			return true;
		}
		// 만약 o 객체가 HashSet이 아닌경우 false
		if(!(o instanceof HashSet)) {
			return false;
		}

		HashSet<E> oSet;

		/*
		 *  Object로부터 HashSet<E>로 캐스팅 되어야 비교가 가능하기 때문에
		 *  만약 캐스팅이 불가능할 경우 ClassCastException 이 발생한다.
		 *  이 경우 false를 return 하도록 try-catch문을 사용해준다.
		 */
		try {

			// HashSet 타입으로 캐스팅
			oSet = (HashSet<E>) o;
			// 사이즈(요소 개수)가 다르다는 것은 명백히 다른 객체다.
			if(oSet.size() != size) {
				return false;
			}

			for(int i = 0; i < oSet.table.length; i++) {
				Node<E> oTable = oSet.table[i];

				while(oTable != null) {
					/*
					 * 서로 Capacity가 다를 수 있기 때문에 index에 연결 된 원소를을
					 * 비교하는 것이 아닌 contains로 원소의 존재 여부를 확인해야한다.
					 */
					if(!contains(oTable)) {
						return false;
					}
					oTable = oTable.next;
				}
			}

		} catch(ClassCastException e) {
			return false;
		}
		// 위 검사가 모두 완료되면 같은 객체임이 증명됨
		return true;

	}

	public Object[] toArray() {

		if (table == null) {
			return null;
		}
		Object[] ret = new Object[size];
		int index = 0;	// 인덱스 변수를 따로 둔다.

		for (int i = 0; i < table.length; i++) {

			Node<E> node = table[i];

			// 해당 인덱스에 연결 된 모든 노드를 하나씩 담는다.
			while (node != null) {
				ret[index] = node.key;
				index++;
				node = node.next;
			}
		}

		return ret;
	}

	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {

		Object[] copy = toArray();	// toArray()통해 먼저 배열을 얻는다.

		// 들어온 배열이 copy된 요소 개수보다 작을경우 size에 맞게 늘려주면서 복사한다.
		if (a.length < size)
			return (T[]) Arrays.copyOf(copy, size, a.getClass());

		// 그 외에는 copy 배열을 a에 0번째부터 채운다.
		System.arraycopy(copy, 0, a, 0, size);

		return a;
	}

}
