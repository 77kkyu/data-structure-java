package com.company.queue.linkedListQueue;

import com.company.queue.Queue;

import java.util.NoSuchElementException;


public class LinkedListQueue<E> implements Queue<E>, Cloneable {

	private Node<E> head;
	private Node<E> tail;
	private int size;

	public LinkedListQueue() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	@Override
	public boolean offer(E e) {
		Node<E> newNode = new Node<E>(e);

		if (size == 0) { // 비어있으면
			head = newNode;
		}else { // 그 외의 마지막 노드(tail)의 다음 노드(next)가 새 노드를 가리킨다
			tail.next = newNode;
		}

		// tail이 가리키는 노드를 새 노드로 바꿔준다
		tail = newNode;
		size++;
		return true;
	}

	@Override
	public E poll() {

		if (size == 0) { // 삭제할 요소가 없으면 null리턴
			return null;
		}

		E element = head.data; // 삭제될 요소의 데이터를 반환하기 위한 임시 변수

		Node<E> nextNode = head.next; // head 노드의 다음 노드

		// head의 모든 데이터 삭제
		head.data = null;
		head.next = null;

		// head가 가리키는 노드를 삭제된 head노드의 다음노드를 가리키도록 변경
		head = nextNode;
		size--;

		return element;
	}

	public E remove() {
		E element = poll(); //첫번째 요소 삭제 후 반환
		if (element == null) {
			throw new NoSuchElementException();
		}
		return element;
	}

	@Override
	public E peek() {
		if (size == 0) {
			return null;
		}
		return head.data;
	}

	public E element() {
		E element = peek();
		if (element == null) {
			throw new NoSuchElementException();
		}
		return element;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean contains(Object value) {

		/**
		 * head 데이터부터 x가 null이 될 때까지 value랑 x의 데이터(x.data)랑
		 * 같은지를 비교하고 같을 경우 true를 반환한다.
		 */
		for (Node<E> x=head; x!=null; x=x.next) {
			if (value.equals(x.data)) {
				return true;
			}
		}
		return false;

	}

	public void clear() {
		for (Node<E> x=head; x!=null;) {
			Node<E> next = x.next;
			x.data = null;
			x.next = null;
			x = next;
		}
		size = 0;
		head = tail = null;
	}

	public Object[] toArray() {
		Object[] array = new Object[size];
		int idx = 0;
		for (Node<E> x = head; x != null; x = x.next) {
			array[idx++] = (E) x.data;
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {
		if (a.length < size) {
			// Array.newInstance(컴포넌트 타입, 생성할 크기)
			a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
		}

		int i = 0;
		// 얕은 복사를 위한 s 배열
		Object[] result = a;
		for (Node<E> x = head; x != null; x = x.next) {
			result[i++] = x.data;
		}
		return a;
	}

	public Object clone() {

		// super.clone() 은 CloneNotSupportedException 예외를 처리해주어야 한다.
		try {
			@SuppressWarnings("unchecked")
			LinkedListQueue<E> clone = (LinkedListQueue<E>) super.clone();	// 새로운 큐 객체 생성
			clone.head = null;
			clone.tail = null;
			clone.size = 0;

			// 내부까지 복사되는 것이 아니기 때문에 내부 데이터들을 모두 복사해준다.
			for(Node<E> x = head; x != null; x = x.next) {
				clone.offer(x.data);
			}
			return clone;

		} catch (CloneNotSupportedException e) {
			throw new Error(e);	// 예외처리는 여러분들이 자유롭게 구성하면 된다.
		}
	}

}
