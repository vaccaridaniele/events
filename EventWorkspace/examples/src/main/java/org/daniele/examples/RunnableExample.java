package org.daniele.examples;

public class RunnableExample {

	public static void main(String[] args) {
		Thread myThread = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("Printed inside runnable");
			}
		});
		myThread.run();

		/*
		 *  use of functional interface: a functional interface is an interface that has only one method
		 */
		Thread myLambdaThread = new Thread(() -> System.out.println("Printed inside Lambda runnable "));
		myLambdaThread.run();
	}

}