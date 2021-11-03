package com.company.linkedlist.doubly;

import com.company.linkedlist.singly.List;

import java.util.NoSuchElementException;

public class DoublyLinkedList<E> implements List<E>, Cloneable {

	private Node<E> head;   // 노드의 첫 부분
	private Node<E> tail;   // 노드의 마지막 부분
	private int size;       // 요소 개수

	public DoublyLinkedList() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	private Node<E> search(int index) {

		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		if (index > size / 2) {
			Node<E> x = tail;
			for (int i=size-1; i>index; i--) {
				x = x.prev;
			}
			return x;
		}else {
			Node<E> x = head;
			for(int i=0; i<index; i++) {
				x = x.next;
			}
			return x;
		}

	}

	public void addFirst(E value) {

		Node<E> newNode = new Node<E>(value); // 새 노드 생성
		newNode.next = head;

		/**
		 * head가 null이 아닐 경우에만 기존 head노드의 prev 변수가
		 * 새 노드를 가리키도록 한다.
		 * 이유는 기존 head노드가 없는 경우(null)는 데이터가
		 * 아무 것도 없던 상태였으므로 head.prev를 하면 잘못된 참조가 된다.
		 */
		if (head != null) {
			head.prev = newNode;
		}

		head = newNode;
		size++;

		/**
		 * 다음에 가리킬 노드가 없는 경우(=데이터가 새 노드밖에 없는 경우)
		 * 데이터가 한 개(새 노드)밖에 없으므로 새 노드는 처음 시작노드이자
		 * 마지막 노드다. 즉 tail = head 다.
		 */
		if (head.next == null) {
			tail = head;
		}

	}

	public void addLast(E value) {

		Node<E> newNode = new Node<E>(value);

		if (size == 0) { // 노드가 없을 경우 처음으로 생성
			addFirst(value);
			return;
		}

		/**
		 * 마지막 노드(tail)의 다음 노드(next)가 새 노드를 가리키도록 하고
		 * tail이 가리키는 노드를 새 노드로 바꿔준다.
		 */
		tail.next = newNode;
		newNode.prev = tail;
		tail = newNode;
		size++;

	}

	@Override
	public boolean add(E value) {
		addLast(value);
		return true;
	}

	@Override
	public void add(int index, E value) {

		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException();
		}

		if (index == 0) { // 추가하려는 index가 가장 앞에 추가하려는 경우
			addFirst(value);
			return;
		}

		if (index == size) { // 추가하려는 index가 마지막 위치일 경우
			addLast(value);
			return;
		}

		// 추가하려는 위치의 이전 노드
		Node<E> prev_Node = search(index - 1);

		// 추가하려는 위치의 노드
		Node<E> next_Node = prev_Node.next;

		// 추가하려는 노드
		Node<E> newNode = new Node<E>(value);

		// 링크 끊기
		prev_Node.next = null;
		next_Node.prev = null;

		// 링크 연결
		prev_Node.next = newNode;

		newNode.prev = prev_Node;
		newNode.next = next_Node;

		next_Node.prev = newNode;
		size++;

	}

	public E remove() {

		Node<E> headNode = head;

		if (headNode == null) { // 예외처리
			throw new NoSuchElementException();
		}

		// 삭제된 노드를 반환하기 위한 임시 변수
		E element = headNode.data;

		// head의 다음 노드
		Node<E> nextNode = head.next;

		// head 노드의 데이터들을 모두 삭제
		head.data = null;
		head.next = null;

		/**
		 * head의 다음노드(=nextNode)가 null이 아닐 경우에만
		 * prev 변수를 null로 업데이트 해주어야 한다.
		 * 이유는 nextNode가 없는 경우(null)는 데이터가
		 * 아무 것도 없던 상태였으므로 nextNode.prev를 하면 잘못된 참조가 된다.
		 */
		if (nextNode != null) {
			nextNode.prev = null;
		}

		head = nextNode;
		size--;

		/**
		 * 삭제된 요소가 리스트의 유일한 요소였을 경우
		 * 그 요소는 head 이자 tail이었으므로
		 * 삭제되면서 tail도 가리킬 요소가 없기 때문에
		 * size가 0일경우 tail도 null로 변환
		 */
		if (size == 0) {
			tail = null;
		}

		return element;

	}

	@Override
	public E remove(int index) {

		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}

		// 삭제하려는 노드가 첫번째 노드일 경우
		if (index == 0) {
			E element = head.data;
			remove();
			return element;
		}

		Node<E> prevNode = search(index-1); // 삭제할 노드의 이전 노드
		Node<E> removedNode = prevNode.next; // 삭제할 노드
		Node<E> nextNode = removedNode.next; // 삭제할 노드의 다음 노드

		E element = removedNode.data; // 삭제되는 노드의 데이터를 반환하기 위한 임시변수

		/**
		 * index == 0 일 때의 조건에서 이미 head노드의 삭제에 대한 분기가 있기 때문에
		 * prevNode는 항상 존재한다.
		 *
		 * 그러나 nextNode의 경우는 null일 수 있기 때문에 (= 마지막 노드를 삭제하려는 경우)
		 * 이전처럼 반드시 검사를 해준 뒤, nextNode.prev에 접근해야 한다.
		 */
		prevNode.next = null;
		removedNode.next = null;
		removedNode.prev = null;
		removedNode.data = null;

		/**
		 *  nextNode가 null이라는 것은 마지막 노드를 삭제했다는 의미이므로
		 *  prevNode가 tail이 된다. (연결 해줄 것이 없음)
		 */
		if (nextNode != null) {
			nextNode.prev = null;
			nextNode.prev = prevNode;
			nextNode.next = nextNode;
		}else {
			tail = prevNode;
		}

		size--;
		return element;
	}

	@Override
	public boolean remove(Object value) {

		Node<E> prevNode = head;
		Node<E> x = head; // removedNode

		// value와 같은 노드 찾기
		for (; x!=null; x=x.next) {
			if (value.equals(x.data)) {
				break;
			}
			prevNode = x;
		}

		// 일치하는 요소가 없을 경우 false 리턴
		if (x == null) {
			return false;
		}

		if (x.equals(head)) { // 삭제하려는 노드가 head일 경우 remove()로 삭제
			remove();
			return true;
		}else { // remove(inde index)와 같은 메커니즘으로 삭제
			Node<E> nextNode = x.next;
			prevNode.next = null;
			x.data = null;
			x.next = null;
			x.prev = null;

			if (nextNode != null) {
				nextNode.prev = null;
				nextNode.prev = prevNode;
				prevNode.next = nextNode;
			}else {
				tail = prevNode;
			}

			size--;
			return true;

		}

	}

	@Override
	public E get(int index) { // 해당 노드 반환
		return search(index).data;
	}

	@Override
	public void set(int index, E value) { // 해당 노드안에 value 넣기
		Node<E> replaceNode = search(index);
		replaceNode.data = null;
		replaceNode.data = value;
	}

	@Override
	public boolean contains(Object value) {
		return indexOf(value) >= 0;
	}

	@Override
	public int indexOf(Object value) { // 정방향 탐색

		int index =0;
		for (Node<E> x = head; x!=null; x=x.next) {
			if (value.equals(x.data)) {
				return index;
			}
			index++;
		}
		return -1;

	}

	public int lastIndexOf(Object o) {
		int index = size;
		for(Node<E> x=tail; x!=null; x=x.prev) {
			index--;
			if (o.equals(x.data)) {
				return index;
			}
		}
		return -1;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public void clear() {

		for (Node<E> x=head; x!=null;) {
			Node<E> nextNode = x.next;
			x.data = null;
			x.next = null;
			x.prev = null;
			x = nextNode;
		}

		head = tail = null;
		size = 0;

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		@SuppressWarnings("unchecked")
		DoublyLinkedList<? super E> clone = (DoublyLinkedList<? super E>) super.clone();

		clone.head = null;
		clone.tail = null;
		clone.size = 0;

		for (Node<E> x = head; x != null; x = x.next) {
			clone.addLast(x.data);
		}

		return clone;
	}
}
