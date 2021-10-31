package com.company.stack;

public class StackMain {
	public static void main(String[] args) {

		ArrayStack arrayStack = new ArrayStack(10);
		arrayStack.push('가');
		arrayStack.printStack();

		arrayStack.push('나');
		arrayStack.push('다');
		arrayStack.printStack();

		arrayStack.pop();
		arrayStack.printStack();

		arrayStack.peek();
		arrayStack.printStack();

		arrayStack.clear();
		arrayStack.printStack();

	}
}
