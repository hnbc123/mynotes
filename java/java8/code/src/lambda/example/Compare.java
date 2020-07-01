package lambda.example;

import java.util.ArrayList;
import java.util.Comparator;
import static java.util.Comparator.comparing;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import lambda.fruit.Apple;

public class Compare {
	public static void main(String[] args) {
		List<Apple> inventory = new ArrayList<>();
		inventory.add(new Apple("green", 120));
		inventory.add(new Apple("green", 20));
		inventory.add(new Apple("red", 60));
		inventory.add(new Apple("green", 60));
		inventory.add(new Apple("red", 80));
		inventory.add(new Apple("red", 70));
		inventory.add(new Apple("red", 100));
		
		// 第一种方法：直接传递类
//		inventory.sort(new AppleComparator());
		
		// 第二种方法：匿名内部类
		/*inventory.sort(new Comparator<Apple>() {
			@Override
			public int compare(Apple apple1, Apple apple2) {
				return apple1.getWeight().compareTo(apple2.getWeight());
			}
		});
		*/
		
		// 第三种方法：Lambda表达式
/*		inventory.sort((apple1, apple2) -> apple1.getWeight().compareTo(apple2.getWeight()));
		
		inventory.sort(comparing((apple) -> apple.getWeight()));*/
		
		// 第四种方法：方法引用
//		inventory.sort(comparing(Apple::getWeight));
		
		// 比较器复合
		// 按重量逆序
//		inventory.sort(comparing(Apple::getWeight).reversed());
		
		// 比较器链
/*		inventory.sort(comparing(Apple::getWeight)
				.reversed()
				.thenComparing(Apple::getColor));*/
		
	
		// 谓词复合
		// 包括三个方法：predicate(非)、and、or
//		Predicate<Apple> greenApple = Apple::isGreenApple;
//		inventory = filterApples(inventory, greenApple.negate());
		
//		inventory = filterApples(inventory, greenApple.and((apple) -> apple.getWeight() > 100));
		// and和or方法是按照在表达式链中的位置，从左向右确定优先级的。
//		inventory = filterApples(inventory, greenApple.and((apple) -> apple.getWeight() > 100)
//													  .or(greenApple.negate()));
		
		// 函数复合
		Function<Integer, Integer> f = x -> x + 1;
		Function<Integer, Integer> g = x -> x * 2;
		// andThen 相当于g(f(x))
//		Function<Integer, Integer> h = f.andThen(g);
		// compose 相当于f(g(x))
		Function<Integer, Integer> h = f.compose(g);
		
		System.out.println(h.apply(1));
		
		// 打印结果
		for (Apple apple : inventory) {
			System.out.println(apple);
		}
	}
	

	private static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }
}

class AppleComparator implements Comparator<Apple> {

	@Override
	public int compare(Apple a1, Apple a2) {
		return a1.getWeight().compareTo(a2.getWeight());
	}
	
}