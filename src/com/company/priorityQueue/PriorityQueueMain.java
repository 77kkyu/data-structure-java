package com.company.priorityQueue;

public class PriorityQueueMain {
	public static void main(String[] args) throws CloneNotSupportedException {

		PriorityQueue<Integer> original = new PriorityQueue<>();
		original.offer(10); // original에 10추가

		PriorityQueue<Integer> copy = original;
		PriorityQueue<Integer> clone = (PriorityQueue<Integer>) original.clone();

		copy.offer(20); // copy에 20추가
		clone.offer(30); // clone에 30추가

		System.out.println("original PriorityQueue");
		int i = 0;
		for (Object a : original.toArray()) {
			System.out.println(i + "번 째 data = " + a);
			i++;
		}

		System.out.println("\ncopy PriorityQueue");
		i = 0;
		for (Object a : copy.toArray()) {
			System.out.println(i + "번 째 data = " + a);
			i++;
		}

		System.out.println("\nclone PriorityQueue");
		i = 0;
		for (Object a : clone.toArray()) {
			System.out.println(i + "번 째 data = " + a);
			i++;
		}

		System.out.println("\noriginal PriorityQueue reference : " + original);
		System.out.println("copy PriorityQueue reference : " + copy);
		System.out.println("clone PriorityQueue reference : " + clone);

	}
}
