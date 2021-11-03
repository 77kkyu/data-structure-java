package com.company.linkedlist.singly;

public class test {
	public static void main(String[] args) {
		SinglyLinkedList<Student> list = new SinglyLinkedList<>();

		list.add(new Student("해쉬", 92));
		list.add(new Student("앨런", 72));
		list.add(new Student("에이든", 98));

		list.addLast(new Student("하하몽", 200));

		list.add(new Student("마크", 51));

		list.addFirst(new Student("크크몽", 100));

		//list.sort();
		for(int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
}

class Student implements Comparable<Student> {
	String name;
	int score;

	Student(String name, int score){
		this.name = name;
		this.score = score;
	}

	public String toString() {
		return "이름 : " + name + "\t성적 : " + score;
	}

	@Override
	public int compareTo(Student o) {
		return o.score - this.score;
	}
}
