package com.company.queue.linkedListQueue;

public class LinkedListQueueMain {
	public static void main(String[] args) throws CloneNotSupportedException {
		LinkedListQueue<Integer> original = new LinkedListQueue<>();
		original.offer(10); // original에 10추가

		LinkedListQueue<Integer> copy = original;
		LinkedListQueue<Integer> clone = (LinkedListQueue<Integer>) original.clone();

		copy.offer(20); // copy에 20추가
		clone.offer(30); // clone에 30추가

		System.out.println("original LinkedListQueue");
		int i = 0;
		for (Object a : original.toArray()) {
			System.out.println(i + "번 째 data = " + a);
			i++;
		}

		System.out.println("\ncopy LinkedListQueue");
		i = 0;
		for (Object a : copy.toArray()) {
			System.out.println(i + "번 째 data = " + a);
			i++;
		}

		System.out.println("\nclone LinkedListQueue");
		i = 0;
		for (Object a : clone.toArray()) {
			System.out.println(i + "번 째 data = " + a);
			i++;
		}

		System.out.println("\noriginal LinkedListQueue reference : " + original);
		System.out.println("copy LinkedListQueue reference : " + copy);
		System.out.println("clone LinkedListQueue reference : " + clone);
	}
}
