package stream.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import stream.menu.Dish;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Basic {
	public static void main(String[] args) {
		List<Dish> menu = Arrays.asList(
			new Dish("pork", false, 800, Dish.Type.MEAT),
			new Dish("beef", false, 700, Dish.Type.MEAT),
			new Dish("chicken", false, 400, Dish.Type.MEAT),
			new Dish("french fries", true, 530, Dish.Type.OTHER),
			new Dish("rice", true, 350, Dish.Type.OTHER),
			new Dish("season fruit", true, 120, Dish.Type.OTHER),
			new Dish("pizza", true, 550, Dish.Type.OTHER),
			new Dish("prawns", false, 300, Dish.Type.FISH),
			new Dish("salmon", false, 450, Dish.Type.FISH)
		);
		
		/*List<String> lowCaloricDishesName = 
				menu.stream()
//				menu.parallelStream()
					.filter(d -> d.getCalories() < 400)
					.sorted(comparing(Dish::getCalories))
					.map(Dish::getName)
					.collect(toList());
		
		for (String name : lowCaloricDishesName) {
			System.out.println(name);
		}*/
		
		/*List<String> threeHighCaloricDishNames = 
			menu.stream()
				.filter(d -> d.getCalories() > 300)
				.map(Dish::getName)
				.limit(3)
				.collect(toList());*/
		
		/*List<String> threeHighCaloricDishNames = 
				menu.stream()
					.filter(d -> d.getType() == Dish.Type.MEAT)
					.map(Dish::getName)
					.limit(2)
					.collect(toList());
		System.out.println(threeHighCaloricDishNames);*/

		// 流的扁平化
	/*	List<String> words = Arrays.asList("Goodbye", "World");
		List<String> uniqueCharacters =
		  words.stream()
			   .map(word -> word.split(""))
			   .flatMap(Arrays::stream)
			   .distinct()
			   .collect(toList());
		System.out.println(uniqueCharacters);*/
		
		/*List<Integer> numbers1 = Arrays.asList(1, 2, 3);
		List<Integer> numbers2 = Arrays.asList(3, 4);
		List<int[]> result = 
			numbers1.stream()
				  .flatMap(i -> numbers2.stream()
						  				.filter(j -> (i + j) % 3 == 0)
						  				.map(j -> new int[] {i, j}))
				  .collect(toList());*/
		
		// 查找和匹配
		// anyMatch 谓词是否至少匹配一个元素
//		boolean result = menu.stream().anyMatch(Dish::isVegetarian);
		// allMatch 谓词是否匹配所有元素
//		boolean result = menu.stream().allMatch(d -> d.getCalories() < 700);
		// noneMatch 谓词是否不匹配所有元素
//		boolean result = menu.stream().noneMatch(d -> d.getCalories() > 900);
//		System.out.println(result);
	
		// 查找元素
		// findAny():查找某一个;findFirst():查找第一个
		/*menu.stream()
			.filter(Dish::isVegetarian)
			.findAny()
			.ifPresent(d -> System.out.println(d.getName()));;*/
		
		// 归约
		List<Integer> number = Arrays.asList(1, 2, 3, 4, 5);
		// 求和
//		Integer result = number.stream().reduce(0, Integer::sum);
		// 乘积
//		Integer result = number.stream().reduce(1, (a, b) -> a * b);
//		System.out.println(result);
		// 最值
		/*number.stream()
			  .reduce(Integer::max)
			  .ifPresent(n -> System.out.println(n));
	
		menu.stream()
			.map(dish -> 1)
			.reduce((a, b) -> a + b)
			.ifPresent(num -> System.out.println(num));*/
	
		/*// 映射到数值流
		int calories = menu.stream()
						   .mapToInt(Dish::getCalories)
						   .sum();
		System.out.println(calories);

		// 没有最大值则提供默认最大值为1
		OptionalInt maxCalories = 
			menu.stream()
				.mapToInt(Dish::getCalories)
				.max();
		System.out.println(maxCalories.orElse(1));
		
		// 数值范围
		// range()不包括两端,rangeClosed()包括两端
		IntStream evenNumbers = IntStream.rangeClosed(1, 100)
										 .filter(i -> i % 2 == 0);
		System.out.println(evenNumbers.count());
		
		// 数值流应用：勾股数
		IntStream.rangeClosed(1, 100)
				 .boxed()
				 .flatMap(a -> IntStream.rangeClosed(a, 100)
				 						.mapToObj(b -> new double[] {a, b, Math.sqrt(a * a + b * b)}))
				 						.filter(b -> b[2] % 1 == 0)
				 .forEach(t -> System.out.println((int)t[0] + " " + (int)t[1] + " " + (int)t[2]));*/
		
		// 构建流
		// 由值构建流
		Stream<String> stream = Stream.of("Java 8", "lambda", "In", "Action");
		stream.map(String::toUpperCase).forEach(System.out::println);
		// 空流
		Stream<String> emptyStream = Stream.empty();
		
		// 由数组创建流
		int[] nums = {2, 3, 5, 7, 11, 13};
		System.out.println(Arrays.stream(nums).sum());
		
		// 由文件创建流
		try(Stream<String> lines = Files.lines(Paths.get("D:\\data.txt"), Charset.defaultCharset())) {
			long uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
									.distinct()
									.count();
			System.out.println(uniqueWords);
		} catch(Exception e) {
			
		}
		
		// 由函数生成流：创建无限流
		// iterate:迭代
		Stream.iterate(new int[] {0, 1}, n -> new int[] {n[1], n[0] + n[1]})
			  .limit(20)
			  .map(t -> t[0])
			  .forEach(System.out::println);
		// generate:生成
		Stream.generate(Math::random)
			  .limit(5)
			  .forEach(System.out::println);
		
		IntSupplier fib = new IntSupplier() {
			private int previous = 0;
			private int current = 1;
			@Override
			public int getAsInt() {
				int oldPrevious = this.previous;
				int nextValue = this.previous + this.current;
				this.previous = this.current;
				this.current = nextValue;
				return oldPrevious;
			}
		};
		IntStream.generate(fib).limit(10).forEach(System.out::println);
	}
}
