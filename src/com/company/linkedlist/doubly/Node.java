package com.company.linkedlist.doubly;

public class Node<E> {

	E data;
	Node<E> next;
	Node<E> prev;

	public Node(E data) {
		this.data = data;
		this.prev = null;
		this.next = null;
	}

}
