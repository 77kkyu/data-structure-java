package com.company.hashtable;

public class HashTableMain {
	public static void main(String[] args) {

		HashTable hashTable = new HashTable();

		hashTable.put("해쉬", "철규");
		hashTable.put("에이든", "중창");
		hashTable.put("마크", "동주");
		hashTable.put("앨런", "봉민");

		System.out.println("size = " + hashTable.size());
		System.out.println(hashTable.get("해쉬"));
		System.out.println(hashTable.get("에이든"));
		System.out.println(hashTable.get("마크"));
		System.out.println(hashTable.get("앨런"));
		System.out.println(hashTable.toString());

		System.out.println();
		System.out.println("===============================");
		System.out.println();

		hashTable.remove("해쉬");
		System.out.println("size = " + hashTable.size());
		System.out.println(hashTable.get("해쉬"));
		System.out.println(hashTable.toString());

	}
}
