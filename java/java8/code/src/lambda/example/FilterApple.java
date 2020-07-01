package lambda.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lambda.example.FilterApple.Predicate;
import lambda.fruit.Apple;

/**
 * 对苹果进行筛选
 * @author Administrator
 *
 */
public class FilterApple {
	// 筛选绿苹果
	public static List<Apple> filterGreenApples(List<Apple> inventory) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if ("green".equals(apple.getColor())) {
				result.add(apple);
			}
		}
		return result;
	}
	
	// 根据颜色筛选苹果
	public static List<Apple> filterApplesByColor(List<Apple> inventory, String color) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (color.equals(apple.getColor())) {
				result.add(apple);
			}
		}
		return result;
	}
	
	// 根据重量筛选苹果
	public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (apple.getWeight() > weight) {
				result.add(apple);
			}
		}
		return result;
	}
	
	@FunctionalInterface
	public interface Predicate<T> {
		boolean test(T t);
	}

	// 根据相应策略过滤苹果
	public static <T> List<T> filter(List<T> inventory, Predicate<T> p) {
		List<T> result = new ArrayList<>();
		for (T t : inventory) {
			if (p.test(t)) {
				result.add(t);
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		// 初始化苹果列表
		List<Apple> apples = Arrays.asList(new Apple("red", 120)
										 , new Apple("green", 100)
										 , new Apple("red", 180)
										 , new Apple("green", 160)
										 , new Apple("green", 100));
		
		// 匿名内部类
		/*List<Apple> results = filterApples(apples, new ApplePredicate() {
			@Override
			public boolean test(Apple apple) {
				return apple.getWeight() > 150;
			}
		});*/
		
		// lambda表达式
		List<Apple> results = filter(apples, apple -> apple.getWeight() > 150);
		
		List<Integer> number = Arrays.asList(1, 2, 3, 4, 5);
		List<Integer> resultNums = filter(number, i -> i % 2 == 0);
		
		System.out.println(resultNums);
	}	
}

/**
 * 判断是否为重苹果
 * @author Administrator
 *
 */
class AppleHeavyWeightPredicate implements Predicate<Apple> {
	@Override
	public boolean test(Apple apple) {
		return apple.getWeight() > 150;
	}	
}

/**
 * 判断是否为绿苹果
 * @author Administrator
 *
 */
class AppleGreenColorPredicate implements Predicate<Apple> {
	@Override
	public boolean test(Apple apple) {
		return "green".equals(apple.getColor());
	}
}