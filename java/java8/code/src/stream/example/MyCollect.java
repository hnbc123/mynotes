package stream.example;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;

import static java.util.Comparator.comparingInt;

import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import stream.menu.Dish;

public class MyCollect {
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
	
		long howManyDishes = menu.stream().count();
		
		Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
		Optional<Dish> mostCaloriesDish = menu.stream().collect(maxBy(dishCaloriesComparator));
		
		// 汇总
		int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));
		double avgCalories = menu.stream().collect(averagingInt(Dish::getCalories));
		IntSummaryStatistics menuStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));
		
		// 连接字符串
		String shortMenu = menu.stream().map(Dish::getName).collect(joining(", "));
		
		// 分组
		Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));
	
		Map<CaloriesLevel, List<Dish>> dishesByCaloriesLevel = menu.stream().collect(
			groupingBy(dish -> {
				if (dish.getCalories() <= 400) return CaloriesLevel.DIET;
				else if (dish.getCalories() <= 700) return CaloriesLevel.NORMAL;
				else return CaloriesLevel.FAT;
			}));
		
		// 多级分组
		Map<Dish.Type, Map<CaloriesLevel, List<Dish>>> dishesByTypeCaloriesLevel = 
			menu.stream().collect(
				groupingBy(Dish::getType, 
					groupingBy(dish -> {
						if (dish.getCalories() <= 400) return CaloriesLevel.DIET;
						else if (dish.getCalories() <= 700) return CaloriesLevel.NORMAL;
						else return CaloriesLevel.FAT;
					})));
		
		// 按子组收集数据
		Map<Dish.Type, Long> typesCount = menu.stream().collect(groupingBy(Dish::getType, counting()));
		
		// 查找每个子组中热量最高的Dish
		Map<Dish.Type, Dish> mostCaloricByType = menu.stream()
			.collect(groupingBy(Dish::getType,
					collectingAndThen(maxBy(comparingInt(Dish::getCalories)), Optional::get)));
		
		Map<Dish.Type, Integer> totalCaloricByType = menu.stream()
			.collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));
		
		Map<Dish.Type, Set<CaloriesLevel>> caloricLevelsByType = 
			menu.stream().collect(groupingBy(Dish::getType, 
				mapping(dish -> {
					if (dish.getCalories() <= 400) return CaloriesLevel.DIET;
					else if (dish.getCalories() <= 700) return CaloriesLevel.NORMAL;
					else return CaloriesLevel.FAT;}, toSet())));
		
		// 分区
		Map<Boolean, List<Dish>> partitionedMenu = 
			menu.stream().collect(partitioningBy(Dish::isVegetarian));
		
		Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType = 
			menu.stream().collect(partitioningBy(Dish::isVegetarian, 
				groupingBy(Dish::getType)));
		
		// 自定义收集器
//		List<Dish> dishes = menu.stream().collect(new ToListCollector<Dish>());
		List<Dish> dishes = menu.stream().collect(ArrayList::new, List::add, List::addAll);
		
	}
	
	public enum CaloriesLevel { DIET, NORMAL, FAT }
	
	public static <T> List<T> takeWhile(List<T> list, Predicate<T> p) {
		int i = 0;
		for (T item : list) {
			if (!p.test(item)) {
				return list.subList(0, i);
			}
			i++;
		}
		return list;
	}
	
	public static boolean isPrime(List<Integer> primes, int candidate) {
		int candidateRoot = (int) Math.sqrt(candidate);
		return takeWhile(primes, i -> i <= candidateRoot)
					.stream()
					.noneMatch(i -> candidate % i == 0);
	}
	
	public Map<Boolean, List<Integer>> partitionPrimes(int n) {
		return IntStream.rangeClosed(2, n).boxed()
						.collect(new PrimeNumbersCollector());
	}
	
	/*public boolean isPrime(int candidate) {
		int candidateRoot = (int) Math.sqrt(candidate);
		return IntStream.rangeClosed(2, candidateRoot)
						.noneMatch(i -> candidate % i == 0);
	}
	
	public Map<Boolean, List<Integer>> partitionPrimes(int n) {
		return IntStream.rangeClosed(2, n).boxed()
						.collect(partitioningBy(candidate -> isPrime(candidate)));
	}*/
}
