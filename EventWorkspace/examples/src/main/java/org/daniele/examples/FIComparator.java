package org.daniele.examples;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FIComparator {

	public static void main(String[] args) {

		List<String> meineBuchstaben = Arrays.asList("B", "A", "C");
		Comparator<String> alphabetisch = Comparator.naturalOrder();
		meineBuchstaben.sort(alphabetisch);
		meineBuchstaben.forEach(System.out::println);

		List<String> meineWörter = Arrays.asList("AAA", "BB", "C");

		List<Person> personen = Arrays.asList(new Person("B", 1), new Person("A", 2), new Person("A", 1));

	}

}

class Person {

	public Person(String name, int i) {
		super();
		this.name = name;
		this.i = i;
	}

	private String name;
	int i;
}