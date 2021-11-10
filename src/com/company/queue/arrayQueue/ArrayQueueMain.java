package com.company.queue.arrayQueue;

public class ArrayQueueMain {
	public static void main(String[] args) throws CloneNotSupportedException {

		ArrayQueue<Integer> arrayQueue = new ArrayQueue<>(10);
		arrayQueue.offer(1);
		arrayQueue.offer(2);
		arrayQueue.offer(3);
		arrayQueue.poll();
		arrayQueue.offer(5);
		arrayQueue.offer(4);
		System.out.println("arrayQueue ArrayQueue");
		int z = 0;
		for (Object a : arrayQueue.toArray()) {
			System.out.println(z + "번 째 data = " + a);
			z++;
		}

		ArrayQueue<Integer> original = new ArrayQueue<>();
		original.offer(10); // original에 10추가

		ArrayQueue<Integer> copy = original;
		ArrayQueue<Integer> clone = (ArrayQueue<Integer>) original.clone();

		copy.offer(20); // copy에 20추가
		clone.offer(30); // clone에 30추가

		System.out.println("original ArrayQueue");
		int i = 0;
		for (Object a : original.toArray()) {
			System.out.println(i + "번 째 data = " + a);
			i++;
		}

		System.out.println("\ncopy ArrayQueue");
		i = 0;
		for (Object a : copy.toArray()) {
			System.out.println(i + "번 째 data = " + a);
			i++;
		}

		System.out.println("\nclone ArrayQueue");
		i = 0;
		for (Object a : clone.toArray()) {
			System.out.println(i + "번 째 data = " + a);
			i++;
		}

		System.out.println("\noriginal ArrayQueue reference : " + original);
		System.out.println("copy ArrayQueue reference : " + copy);
		System.out.println("clone ArrayQueue reference : " + clone);

	}
}
