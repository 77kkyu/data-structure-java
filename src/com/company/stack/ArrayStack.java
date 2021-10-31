package com.company.stack;

interface Stack {
	boolean isEmpty();
	boolean isFull();
	void push(char item);
	char pop();
	char peek();
	void clear();
}

public class ArrayStack implements Stack {

	private int top;
	private int stackSize;
	private char stackArr[];

	public ArrayStack(int stackSize) { // 생성자
		top = -1;                               // 스택 포인터 초기값
		this.stackSize = stackSize;             // 스택 사이즈
		stackArr = new char[this.stackSize];    // 스택 배열 생성
	}

	@Override
	public boolean isEmpty() { // 비어있는지 확인
		return (top == -1); // 스택 포인터가 -1인 경우 데이터가 없는 상태이므로 true 아닌 경우 false를 return
	}

	@Override
	public boolean isFull() { // 가득찬 상태 확인
		return (top == this.stackSize-1); // 스택 포인터가 스택의 마지막 인덱스와 동일한 경우 true 아닌 경우 false를 return
	}

	@Override
	public void push(char item) {
		if (isFull()) { // false일 경우
			System.out.println("Stack is full");
		} else { // true일 경우
			stackArr[++top] = item; // 다음 스택 포인터가 가리키는 인덱스에 데이터 추가
			System.out.println("Inserted Item = " + item);
		}
	}

	@Override
	public char pop() { // 스택의 최상위 데이터 가져와서 삭제
		if (isEmpty()) { // false
			System.out.println("delete fail, Stack is empty");
			return 0;
		}else {
			System.out.println("Delete = " + stackArr[top]);
			return stackArr[top--];
		}
	}

	@Override
	public char peek() { // 스택 최상위 데이터 가져오기
		if (isEmpty()) {
			System.out.println("peeking fail, Stack is empty");
			return 0;
		}else {
			System.out.println("peeked item = " + stackArr[top]);
			return stackArr[top];
		}
	}

	@Override
	public void clear() { // 스택 초기화
		if (isEmpty()) {
			System.out.println("Stack is already empty");
		}else {
			top = -1;
			stackArr = new char[this.stackSize];
			System.out.println("Stack is clear");
		}
	}

	public void printStack() {
		if (isEmpty()) {
			System.out.println("Stack is empty");
		}else {
			System.out.print("Stack elements = ");
			for (int i=0; i<=top; i++) {
				System.out.print(stackArr[i] + " ");
			}
			System.out.println();
		}
	}



}
