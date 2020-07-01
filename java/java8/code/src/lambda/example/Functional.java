package lambda.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Functional {
	public static <T> List<T> filter(List<T> list, Predicate<T> p) {
		List<T> results = new ArrayList<>();
		for (T t : list) {
			if (p.test(t)) {
				results.add(t);
			}
		}
		return results;
	}
	
	public static <T> void forEach(List<T> list, Consumer<T> c) {
		for (T t : list) {
			c.accept(t);
		}
	}

	public static <T, R> List<R> function(List<T> list, Function<T, R> f) {
		List<R> results = new ArrayList<>();
		for (T t : list) {
			results.add(f.apply(t));
		}
		return results;
	}
	
	public static <T> T supplier(Supplier<T> s) {
		return s.get();
	}
	
	public static void main(String[] args) {
		List<String> nonEmpty = filter(Arrays.asList("1", "", "4"), s -> !s.isEmpty());
		System.out.println(nonEmpty);
		forEach(function(nonEmpty, s -> s + "11"), System.out::println);
		List<String> a = supplier(ArrayList::new);
		a.add("123");
		System.out.println(a);
		
	}
}
