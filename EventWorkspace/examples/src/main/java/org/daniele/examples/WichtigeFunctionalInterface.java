package org.daniele.examples;

import java.util.Comparator;
import java.util.function.*;

public class WichtigeFunctionalInterface {

	public static void main(String[] args) {
		// alt
		Runnable runnable = () -> System.out.println("Hallo Welt");

		Comparator<A> comparatorA = (A a1, A a2) -> a1.einInt - a2.einInt;
		Comparator<String> comparatorString = (String s1, String s2) -> s1.length() - s2.length();

		// neu
		Function<A, B> functionAB = (A einA) -> B.einB;
		Function<String, Integer> functionStringInteger = (String string) -> string.length();

		Predicate<A> predicateA = (A einA) -> true;
		Predicate<String> predicateString = (String string) -> string.isEmpty();

		Supplier<A> supplierA = () -> A.einA;
		Supplier<Double> supplierDouble = () -> Math.random();

		Consumer<A> consumerA = (A einA) -> tuEtwasMit(einA);
		Consumer<String> consumerString = (String string) -> System.out.println(string);

		BiFunction<A, B, C> biFunctionABC = (A einA, B einB) -> C.einC;
		BiFunction<String, Font, Integer> biFunction = (String string, Font font) -> string.length() * font.size;

		BinaryOperator<A> binaryOperator = (A a1, A a2) -> A.einA;
		BinaryOperator<Integer> binaryOperatorInteger = (Integer i1, Integer i2) -> i1 + i2;
	}

	public static void tuEtwasMit(A einA) {
	}
}

class A {
	static A einA;
	int einInt;
}

class B {
	static B einB;
}

class C {
	static C einC;
}

class Font {
	int size;
}
