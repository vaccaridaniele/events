package org.daniele.examples;

public class TypeInterfaceExample {

	public static void main(String[] args) {
		StringLenghtLambda myLambda = s -> s.length();
		System.out.println(myLambda.getLenght("hello lambda"));
		printLambda(myLambda);
	}

	public static void printLambda(StringLenghtLambda l) {
		System.out.println(l.getLenght("hello lambda"));
	}

	interface StringLenghtLambda {
		int getLenght(String s);
	}
}