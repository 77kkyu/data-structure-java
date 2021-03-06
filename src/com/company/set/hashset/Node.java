package com.company.set.hashset;

public class Node<E> {

	// hash와 key값은 변하지 않으므로 final 선언
	public final int hash;
	public final E key;
	public Node<E> next;

	public Node(int hash, E key, Node<E> next) {
		this.hash = hash;
		this.key = key;
		this.next = next;
	}

}
