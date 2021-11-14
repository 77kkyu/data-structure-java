package com.company.heap;

import java.util.Comparator;
import java.util.Random;

public class HeapMain {
	public static void main(String[] args) {

		Heap<Integer> heap = new Heap<>();

		Random rnd = new Random();

		for(int i = 0; i < 15; i++) {
			heap.add(rnd.nextInt(100));
		}

		// 힙 내부 배열의 요소 상태
		System.out.print("내부 배열 상태 : ");
		for(Object val : heap.toArray()) {
			System.out.print(val + " ");
		}
		System.out.println();


		// 힙이 비어있을 때 까지 한 개씩 요소 뽑음
		System.out.print("힙 요소 뽑기 : \t");
		while(!heap.isEmpty()) {
			System.out.print(heap.remove() + " ");
		}

		System.out.println();
		System.out.println();

		Heap<Student> heap1 = new Heap<Student>();
		Heap<Student> heap2 = new Heap<Student>(comparator);


		heap1.add(new Student("김자바", 40));
		heap2.add(new Student("김자바", 40));

		heap1.add(new Student("이씨프", 27));
		heap2.add(new Student("이씨프", 27));

		heap1.add(new Student("조파이", 48));
		heap2.add(new Student("조파이", 48));

		heap1.add(new Student("김자바", 18));
		heap2.add(new Student("김자바", 18));

		heap1.add(new Student("상스윕", 32));
		heap2.add(new Student("상스윕", 32));

		heap1.add(new Student("양씨샵", 27));
		heap2.add(new Student("양씨샵", 27));


		System.out.println("[Heap 1] : 이름순(같을 경우 나이 오름차순)");
		while(!heap1.isEmpty()) {
			System.out.println(heap1.remove());
		}
		System.out.println();

		System.out.println("[Heap 2] : 나이 내림차순(같을 경우 이름순)");
		while(!heap2.isEmpty()) {
			System.out.println(heap2.remove());
		}
		System.out.println();
	}

	private static Comparator<Student> comparator = new Comparator<Student>() {
		@Override
		public int compare(Student o1, Student o2) {
			// 나이가 같다면 이름순
			if(o1.age == o2.age) {
				return o1.name.compareTo(o2.name);
			}

			return o2.age - o1.age;	// 나이 내림차순
		}
	};

	private static class Student implements Comparable<Student> {

		String name;
		int age;

		public Student(String name, int age) {

			this.name = name;
			this.age = age;
		}

		@Override
		public int compareTo(Student o) {
			// 이름이 같다면 나이순 (오름차순)
			if(this.name.compareTo(o.name) == 0) {
				return this.age - o.age;
			}
			// 이름순
			return this.name.compareTo(o.name);

		}


		public String toString() {
			return "이름 : " + name + "\t나이 : " + age;
		}

	}
}
