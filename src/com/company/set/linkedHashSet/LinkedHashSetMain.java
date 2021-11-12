package com.company.set.linkedHashSet;

public class LinkedHashSetMain {
	public static void main(String[] args) {

		LinkedHashSet<Integer> s = new LinkedHashSet<Integer>();

		s.add(71);
		s.add(36);
		s.add(44);
		s.add(12);
		s.add(96);
		s.add(38);
		s.add(17);
		s.add(48);
		s.add(20);
		s.add(77);

		// 테이블에 저장 된 데이터
		System.out.println("[테이블 순회]");
		for(int i = 0; i < s.table.length; i++) {
			System.out.print("index " + i  + " : ");
			Node<Integer> v = s.table[i];
			if(v == null) {
				System.out.print("null");
			}
			while(v != null) {
				System.out.print(v.key + " ");
				v = v.next;
			}
			System.out.println();
		}


		// 연결 순서가 유지되는 데이터
		System.out.print("\n순서 유지 : ");
		Object[] a = s.toArray();
		for(Object v : a) {
			System.out.print(v + " ");
		}

	}
}
