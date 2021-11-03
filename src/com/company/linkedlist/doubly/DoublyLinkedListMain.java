package com.company.linkedlist.doubly;

public class DoublyLinkedListMain {

	public static void main(String[] args) throws CloneNotSupportedException {

		DoublyLinkedList<Integer> original = new DoublyLinkedList<>();
		original.add(10);	// original에 10추가
		original.addFirst(50);

		DoublyLinkedList<Integer> copy = original;
		DoublyLinkedList<Integer> clone = (DoublyLinkedList<Integer>) original.clone();

		copy.add(20);	// copy에 20추가
		clone.add(30);	// clone에 30추가

		System.out.println("original list");
		for(int i = 0; i < original.size(); i++) {
			System.out.println("index " + i + " data = " + original.get(i));
		}

		System.out.println("\ncopy list");
		for(int i = 0; i < copy.size(); i++) {
			System.out.println("index " + i + " data = " + copy.get(i));
		}

		System.out.println("\nclone list");
		for(int i = 0; i < clone.size(); i++) {
			System.out.println("index " + i + " data = " + clone.get(i));
		}

		System.out.println("\noriginal list reference : " + original);
		System.out.println("copy list reference : " + copy);
		System.out.println("clone list reference : " + clone);

	}

}
