package org.daniele.examples;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Stream operation examples
 * 
 * {@link https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html#NonInterference}
 *
 */
public class StreamExample {
	public static void main(String[] args) {

		List<String> wörter = Arrays.asList("Welt", "Pc", "Hallo", "Tag");

		// 1. Stream erstellen
		Stream<String> wörterStream = wörter.stream();

		// beliebig viele intermediate Operations
		Stream<String> wörterStreamSortiert = wörterStream.sorted();
		// wörterStream.sorted(); not allowed; one time consuming method

		Stream<String> wörterStreamSortiertGefiltert = wörterStreamSortiert.filter(wort -> wort.length() > 3);

		Stream<String> wörterStreamSortiertGefiltertGroße = wörterStreamSortiertGefiltert.map(String::toUpperCase);

		// 3. eine terminal operation
		wörterStreamSortiertGefiltertGroße.forEach(System.out::println);

		wörter.stream()
		.sorted()
		.filter(wort -> wort.length() > 3)
		.map(String::toUpperCase)
		.forEach(System.out::println);
	}
}
