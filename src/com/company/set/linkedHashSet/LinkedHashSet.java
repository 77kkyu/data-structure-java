package com.company.set.linkedHashSet;

import com.company.set.Set;
import java.util.Arrays;


public class LinkedHashSet<E> implements Set<E> {

	// 최소 기본 용적이며 2^n꼴 형태가 좋다.
	private final static int DEFAULT_CAPACITY = 1 << 4;

	// 3/4 이상 채워질 경우 resize하기 위한 임계값
	private static final float LOAD_FACTOR = 0.75f;

	Node<E>[] table;		// 요소의 정보를 담고있는 Node를 저장할 Node타입 배열
	private int size;		// 요소의 개수

	// 들어온 순서를 유지할 linkedlist
	private Node<E> head;	// 노드의 첫 부분
	private Node<E> tail;	// 노드의 마지막 부분


	@SuppressWarnings("unchecked")
	public LinkedHashSet() {
		table = (Node<E>[]) new Node[DEFAULT_CAPACITY];
		size = 0;
		head = null;
		tail = null;
	}


	// 보조 해시 함수 (상속 방지를 위해 private static final 선언)
	private static final int hash(Object key) {
		int hash;
		if (key == null) {
			return 0;
		}
		else {
			// hashCode()의 경우 음수가 나올 수 있으므로 절댓값을 통해 양수로 변환해준다.
			return Math.abs(hash = key.hashCode()) ^ (hash >>> 16);
		}
	}
	@SuppressWarnings("unchecked")
	private void resize() {

		int newCapacity = table.length * 2;

		// 기존 테이블의 두배 용적으로 생성
		final Node<E>[] newTable = (Node<E>[]) new Node[newCapacity];

		// 0번째 index부터 차례대로 순회
		for (int i = 0; i < table.length; i++) {

			// 각 인덱스의 첫 번째 노드(head)
			Node<E> value = table[i];

			// 해당 값이 없을 경우 다음으로 넘어간다.
			if (value == null) {
				continue;
			}


			table[i] = null; // gc

			Node<E> nextNode;	// 현재 노드의 다음 노드를 가리키는 변수

			// 현재 인덱스에 연결 된 노드들을 순회한다.
			while (value != null) {

				int idx = value.hash % newCapacity;	// 새로운 인덱스를 구한다.

				/*
				 * 새로 담을 index에 요소(노드)가 존재할 경우
				 * == 새로 담을 newTalbe에 index값이 겹칠 경우 (해시 충돌)
				 */
				if (newTable[idx] != null) {
					Node<E> tail = newTable[idx];

					// 가장 마지막 노드로 간다.
					while (tail.next != null) {
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
				}
				// 충돌되지 않는다면(=빈 공간이라면 해당 노드를 추가)
				else {

					/*
					 *  반드시 value가 참조하고 있는 다음 노드와의 연결을 끊어주어야 한다.
					 *  안하면 각 인덱스의 마지막 노드(tail)도 다른 노드를 참조하게 되어버려
					 *  잘못된 참조가 발생할 수 있다.
					 */
					nextNode = value.next;
					value.next = null;
					newTable[idx] = value;
				}

				value = nextNode;	// 다음 노드로 이동
			}
		}
		table = null;
		table = newTable;	// 새로 생성한 table을 table변수에 연결
	}

	private void linkLastNode(Node<E> o) {

		Node<E> last = tail;	// 마지막 노드를 갖고온다.

		tail = o;	// tail을 새로운 노드 o가 가리키도록 갱신해준다.

		/*
		 * 만약 마지막 노드가 null이라는 것은 노드에 저장된 데이터가 없는,
		 * 즉 head 노드도 null인 상태라는 것이다.
		 * 그러므로 head도 새 노드인 o를 가리키도록 한다.
		 */
		if (last == null) {
			head = o;
		}
		/*
		 * last가 null이 아닐경우 새 노드 o의 앞의 노드를 가리키는 노드를 last로 두고,
		 * last의 다음 노드는 새 노드인 o를 가리키도록 한다.
		 */
		else {
			o.prevLink = last;
			last.nextLink = o;
		}

	}

	@Override
	public boolean add(E e) {
		return add(hash(e), e) == null;
	}

	private E add(int hash, E key) {

		int idx = hash % table.length;

		Node<E> newNode = new Node<E>(hash, key, null);	// 새로운 노드

		if (table[idx] == null) {
			table[idx] = newNode;
		}
		else {

			Node<E> temp = table[idx];
			Node<E> prev = null;

			while (temp != null) {
				if ((temp.hash == hash) && (temp.key == key || temp.key.equals(key))) {
					return key;
				}
				prev = temp;
				temp = temp.next;
			}

			prev.next = newNode;
		}
		size++;

		linkLastNode(newNode); // table에 저장이 끝났으면 해당 노드를 연결해준다.

		// 데이터의 개수가 현재 table 용적의 75%을 넘어가는 경우 용적을 늘려준다.
		if (size >= LOAD_FACTOR * table.length) {
			resize();
		}
		return null;

	}


	private void unlinkNode(Node<E> o) {
		Node<E> prevNode = o.prevLink;	// 삭제하는 노드의 이전 노드
		Node<E> nextNode = o.nextLink;	// 삭제하는 노드의 이후 노드

		/*
		 *  prevNode가 null이라는 것은 삭제한 노드가 head노드였다는 의미이므로
		 *  그 다음노드인 nextNode가 head가 된다.
		 */
		if (prevNode == null) {
			head = nextNode;
		}
		else {
			prevNode.nextLink = nextNode;
			o.prevLink = null;
		}

		/*
		 * nextNode가 null이라는 것은 삭제한 노드가 tail이라는 의미로
		 * 이전 노드가 tail이 된다.
		 */
		if (nextNode == null) {
			tail = prevNode;
		}
		else {
			nextNode.prevLink = prevNode;
			o.nextLink = null;
		}

	}

	@Override
	public boolean remove(Object o) {
		// null이 아니라는 것은 노드가 삭제되었다는 의미다.
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
			// 같은 노드를 찾았다면
			if (node.hash == hash && (node.key == key || node.key.equals(key))) {

				removedNode = node; // 삭제되는 노드를 반환하기 위해 담아둔다.

				// 해당노드의 이전 노드가 존재하지 않는 경우 (= table에 첫번째 체인 노드인 경우)
				if (prev == null) {
					table[idx] = node.next;
				}
				// 그 외엔 이전 노드의 next를 삭제할 노드의 다음노드와 연결해준다.
				else {
					prev.next = node.next;
				}

				// table의 체인을 끊었으니 다음으로 순서를 유지하는 link를 끊는다.
				unlinkNode(node);
				node = null;

				size--;
				break;	// table에서 삭제되었으니 탐색 종료
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
			if (o == temp.key || (o != null && temp.key.equals(o))) {
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
				table[i] = null;
			}
			size = 0;
		}
		head = tail = null;	// 마지막에 반드시 head와 tail을 끊어주어야 gc처리가 된다.
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {

		// 만약 파라미터 객체가 현재 객체와 동일한 객체라면 true
		if(o == this) {
			return true;
		}
		// 만약 o 객체가 LinkedHashSet 계열이 아닌경우 false
		if(!(o instanceof LinkedHashSet)) {
			return false;
		}

		LinkedHashSet<E> oSet;

		/*
		 *  Object로부터 LinkedHashSet<E>로 캐스팅 되어야 비교가 가능하기 때문에
		 *  만약 캐스팅이 불가능할 경우 ClassCastException 이 발생한다.
		 *  이 경우 false를 return 하도록 try-catch문을 사용해준다.
		 */
		try {

			// LinkedHashSet 타입으로 캐스팅
			oSet = (LinkedHashSet<E>) o;
			// 사이즈가 다르다는 것은 명백히 다른 객체다.
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

		if (table == null || head == null) {
			return null;
		}
		Object[] ret = new Object[size];
		int index = 0;

		// 들어온 순서대로 head부터 tail까지 순회한다.
		for(Node<E> x = head; x != null; x = x.nextLink) {
			ret[index] = x.key;
			index++;
		}

		return ret;
	}

	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {

		Object[] copy = toArray();
		if (a.length < size) {
			return (T[]) Arrays.copyOf(copy, size, a.getClass());
		}
		System.arraycopy(copy, 0, a, 0, size);

		return a;
	}

}
