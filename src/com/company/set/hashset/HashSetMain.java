package com.company.set.hashset;

public class HashSetMain {
	public static void main(String[] args) {

		Custom a = new Custom("aaa", 10);
		Custom b = new Custom("aaa", 10); // 내용이 같음
		Custom c = new Custom("bbb", 10);

		HashSet<Custom> set = new HashSet<Custom>();
		set.add(a);

		if (set.add(b)) {
			System.out.println("hashset에 b객체가 추가 됨");
		} else {
			System.out.println("중복원소!");
		}

		if (set.add(c)) {
			System.out.println("hashset에 c객체가 추가 됨");
		} else {
			System.out.println("중복원소!");
		}

	}

	static class Custom {
		String str;
		int val;

		public Custom(String str, int val) {
			this.str = str;
			this.val = val;
		}

		@Override
		public boolean equals(Object obj) {
			if((obj instanceof Custom) && obj != null) {
				// 주소가 같은 경우
				if(obj == this) {
					return true;
				}
				Custom other = (Custom)obj;
				// str 이 null인경우 객체 비교를 못하므로
				if(str == null) {
					// 둘 다 str이 null이면 val 비교
					if(other.str == null) {
						return other.val == val;
					}
					return false;
				}
				if(other.str.equals(str) && other.val == val) {
					return true;
				}
			}
			return false;
		}

		@Override
		public int hashCode() {
			final int prime = 31;	// 소수로 하는 것이 좋다.

			int result = 17;

			/*
			 *  연산 과정 자체는 어떻게 해주어도 무방하나 최대한
			 *  중복되지 않도록 해주는 것이 좋다.
			 */
			result = prime * result * val;
			result = prime * result + (str != null ? str.hashCode() : 0);
			return result;
		}

	}
}
