package com.company.queue.linkedListDeque;

public class LinkedListDequeMain {
	public static void main(String[] args) throws CloneNotSupportedException {
		LinkedListDeque<Integer> original = new LinkedListDeque<>();
		original.offer(10); // original에 10추가
		original.offerLast(50);

		LinkedListDeque<Integer> copy = original;
		LinkedListDeque<Integer> clone = (LinkedListDeque<Integer>) original.clone();

		copy.offer(20); // copy에 20추가
		clone.offer(30); // clone에 30추가

		System.out.println("original LinkedListDeque");
		int i = 0;
		for (Object a : original.toArray()) {
			System.out.println(i + "번 째 data = " + a);
			i++;
		}

		System.out.println("\ncopy LinkedListDeque");
		i = 0;
		for (Object a : copy.toArray()) {
			System.out.println(i + "번 째 data = " + a);
			i++;
		}

		System.out.println("\nclone LinkedListDeque");
		i = 0;
		for (Object a : clone.toArray()) {
			System.out.println(i + "번 째 data = " + a);
			i++;
		}

		System.out.println("\noriginal LinkedListDeque reference : " + original);
		System.out.println("copy LinkedListDeque reference : " + copy);
		System.out.println("clone LinkedListDeque reference : " + clone);
	}
}
