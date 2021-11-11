package com.company.queue.arrayDeque;

public class ArrayDequeMain {
	public static void main(String[] args) throws CloneNotSupportedException {
		ArrayDeque<Integer> original = new ArrayDeque<>();
		original.offer(10); // original에 10추가

		ArrayDeque<Integer> copy = original;
		ArrayDeque<Integer> clone = (ArrayDeque<Integer>) original.clone();

		copy.offer(20); // copy에 20추가
		clone.offer(30); // clone에 30추가

		System.out.println("original ArrayDeque");
		int i = 0;
		for (Object a : original.toArray()) {
			System.out.println(i + "번 째 data = " + a);
			i++;
		}

		System.out.println("\ncopy ArrayDeque");
		i = 0;
		for (Object a : copy.toArray()) {
			System.out.println(i + "번 째 data = " + a);
			i++;
		}

		System.out.println("\nclone ArrayDeque");
		i = 0;
		for (Object a : clone.toArray()) {
			System.out.println(i + "번 째 data = " + a);
			i++;
		}

		System.out.println("\noriginal ArrayDeque reference : " + original);
		System.out.println("copy ArrayDeque reference : " + copy);
		System.out.println("clone ArrayDeque reference : " + clone);
	}
}
