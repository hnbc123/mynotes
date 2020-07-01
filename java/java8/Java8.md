#1 lambda表达式
##1.1 有效的lambda表达式
lambda表达式有三个部分：参数列表、箭头、表达式。  
基本语法是 (parameters) -> expression 或 (parameters) -> {statements;}

* (String s) -> s.length  
> 从一个对象中选择/抽取。具有一个String类型的参数并返回一个int，这里没有return语句，因为隐含了return。

* (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight())
> 布尔表达式。具有一个Apple类型的参数，并返回一个boolean。

* (int x, int y) -> {    
   System.out.println("hello world");    
   return x + y;  
   }
> 具有两个int型参数，返回一个int。lambda表达式可以包含多条语句，此时若有返回值需要加return。

* （）-> new Apple("green", 20)
> 创建对象。没有参数，返回一个Apple对象。

注意：（Integer i) -> return i + 1; 和 (String s) -> {"hello world";}不是有效的表达式。

##1.2 如何使用lambda表达式
###1.2.1 函数式接口
函数式接口是只定义一个抽象方法的接口。即使接口拥有默认方法，只要接口只有一个抽象方法，它就是函数式接口。  
lambda表达式允许你直接以内联的形式为函数式接口的抽象方法提供实现，并把整个表达式作为函数式接口的实例。  
@FunctionalInterface用于表示该接口会被设计为函数式接口。
###1.2.2 函数描述符
函数式接口的抽象方法的签名基本上就是lambda表达式的签名，这种抽象方法叫做函数描述符。  

* execute(() -> {});  
  public void execute(Runnable r){r.run();}
> lambda具有签名() -> void,和Runnable中抽象方法run()的签名相匹配。

###1.2.3 常用函数式接口
| 函数式接口 | 函数描述符 |
| :----:| :----: |
| Predicate\<T> | T->boolean |
| Consumer\<T> | T->void |
| Function<T,R>| T->R |
| Supplier\<T> | ()->T |
| UnaryOperator\<T> | T->T |
| BinaryOperator\<T> | (T,T)->T |
| BiPredicate<L,R> | (L,R)->boolean |
| BiConsumer<T,U> | (T,U)->void |
| BiFunction<T,U,R> | (T,U)->R |

###1.2.4 使用局部变量
lambda表达式引用局部变量时必须显式声明为final，或事实上是final。
例如，下面的代码无法编译：  

	int portNumber = 1337;
	Runnable r = () -> System.out.println(portNumber);
	portNumber = 31337;

### 1.2.5 方法引用
方法引用主要有3类：

* 指向静态方法的方法引用（Integer::parseInt)
> (args) -> ClassName.staticMethod(args)
> ClassName::staticMethod

* 指向任意类型实例方法的方法引用（String::length)
> (arg0, rest) -> arg0.instanceMethod(rest)
> ClassName::instanceMethod

* 指向现有对象的实例方法的方法引用
> (args) -> expr.instanceMethod(args)  
> expr::isntanceMethod

构造函数引用：  
对于一个现有构造函数，你可以利用它的名称和关键字new来创建它的一个引用:  ClassName::new。  

	List<Integer> weights = Arrays.asList(120, 150, 160, 150);
	List<Apple> apples = map(weights, Apple::new);

##1.3 复合Lambda表达式的有用方法
###1.3.1 比较器复合
* 逆序

 		inventory.sort(comparing(Apple::getWeight).reversed());
* 比较器链

		inventory.sort(comparing(Apple::getColor)
							.reversed()
							.thenComparing(Apple::getWeight));
###1.3.2 谓词复合
* negate 非

		Predicate<Apple> redApple = a -> "red".equals(a.getColor());
		Predicate<Apple> notRedApple = redApple.negate();
* and 且

		Predicate<Apple> redAndHeavyApple = redApple.and(a -> a.getWeight() > 150);
* or 或

注意：and或or方法是按照在表达式链中的位置，从左到右确定优先级的。因此，a.or(b).and(c)可以看作(a || b) && c。

###1.3.3 函数复合
* andThen
> Function<Integer, Integer> h = f.andThen(g);相当于g(f(x))

* compose
> Function<Integer, Integer> h = f.compose(g);相当于f(g(x))


#2 流
##2.1 流操作
* 中间操作
> 诸如filter或sorted等中间操作会返回另一个流，除非流水线上触发一个终端操作，否则中间操作不会执行任何处理，因为中间操作一般都可以合并起来，在终端操作时一次性处理。

* 终端操作
> 终端操作会从流的流水线生成结果。其结果是任何不是流的值。

| 操作 | 类型 | 返回类型 | 函数式接口 | 函数描述符 |
| :----:| :----: | :----:| :----: | :----: |
| filter | 中间 | Stream\<T> | Predicate\<T> | T->boolean |
| distinct | 中间 | Stream\<T> |  |  |
| skip | 中间 | Stream\<T> | long |  |
| limit | 中间 | Stream\<T> | long |  |
| map | 中间 | Stream\<R> | Function<T,R> | T->R |
| flatMap | 中间 |Stream\<R>| Function<T,Stream\<R>>|T->Stream\<R> |
| sorted | 中间 | Stream\<T> | Comparator\<T> |(T,T)->int|
| anyMatch | 终端 | boolean | Predicate\<T> | T->boolean |
| noneMatch | 终端 | boolean | Predicate\<T> | T->boolean |
| allMatch | 终端 | boolean | Predicate\<T> | T->boolean |
| findAny | 终端 | Optional\<T> |  |  |
| findFirst | 终端 | Optional\<T> |  |  |
| forEach | 终端 | void | Consumer\<T> | T->void |
| collect | 终端 | R | Collector<T,A,R> | |
| reduce | 终端 | Optional\<T> | BinaryOperator\<T> | (T,T)->T |
| count | 终端 | long | | |

##2.2 使用流
###2.2.1 筛选
* 用谓词筛选
> 流支持filter方法，该操作接受一个谓词(一个返回Boolean的函数)，并返回一个包括所有符合谓词的元素的流。

	List<Dish> vegetarianMenu = menu.stream()
									.filter(Dish::isVegetarian)
									.collect(toList());

* 筛选各异的元素
> 流支持distinct方法，它会返回一个元素各异（根据流所生成元素的hashCode和equals方法实现）的流。

	List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
	numbers.stream()
		   .filter(i -> i % 2 == 0)
		   .distinct()
		   .forEach(System.out::println);

* 截断流
> 流支持limit(n)方法，该方法会返回一个不超过给定长度的流。

	List<Dish> dishes = menu.stream()
							.filter(d -> d.getCalories() > 300)
							.limit(3)
							.collect(toList());

* 跳过元素
> 流支持skip(n)方法，返回一个扔掉了前n个元素的流。如果流中元素不足n个，则返回一个空流。limit(n)和skip(n)是互补的。

	List<Dish> dishes = menu.stream()
							.filter(d -> d.getCalories() > 300)
							.skip(3)
							.collect(toList());

###2.2.2 映射
* 对流中每个元素应用函数
> 流支持map方法，它接受一个函数作为参数。这个函数会被应用到每个元素上，并将其映射成一个新的元素。

	List<String> dishNames = menu.stream()
								 .map(Dish::getName)
								 .collect(toList());

* 流的扁平化
> flatMap方法让你把一个流的每个值都换成另一个流，然后把所有的流连接起来成为一个流。
	
	List<String> uniqueCharacters = 
		words.stream()
			 .map(w -> w.split(""))
			 .flatMap(Arrays::stream)
			 .distinct()
			 .collect(toList());

###2.2.3 查找
* anyMatch:检查谓词是否至少匹配一个元素  

		if (menu.stream().anyMatch(Dish::isVegetarian)) {
			System.out.println("");
		}
* allMatch:检查谓词是否匹配所有元素
* noneMatch:流中没有任何元素与谓词匹配

###2.2.4 匹配
* findAny:返回当前流中任意元素

		Optinal<Dish> dish = menu.stream()
								 .filter(Dish::isVegetarain)
								 .findAny();
* findFirst:查找第一个元素
> 如果不关心返回的元素是哪个，请使用findAny，以内它在使用并行流时限制较少。

###2.2.5 Optional简介
Optional<T>是一个容器类，代表一个值存在或不存在。

* isPresent()将在Optional包含值的时候返回true，否则返回false。
* ifPresent(Consumer<T> block)会在值存在时执行给定代码块。
* T get()会在值存在时返回值，否则抛出NoSuchElement异常。
* T orElse(T other)会在值存在时返回值，否则返回一个默认值。

###2.2.6 归约
* 元素求和

		int sum = numbers.stream().reduce(0, Integer::sum);
		// 无初始值时返回一个Optional
		Optional<Integer> = numbers.stream().reduce(Integer::sum);
* 最值

		Optional<Integer> = numbers.stream().reduce(Integer::max);

* 计数
		int count = numbers.stream().count();

###2.2.7 数值流
java8引入三个原始类型特化流：IntStream,DoubleStream,LongStream，分别将流中的元素特化为int、long、double，从而避免了暗含的装箱成本。

* 映射到数值流：mapToInt、mapToDouble、mapToLong  

		int calories = menu.stream()
						   .mapToInt(Dish::getCalories)
						   .sum(); 
* 转换回对象流：boxed
 
		IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
		Stream<Integer> stream = intStream.boxed();

* Optional原始类型特化：OptionalInt、OptionalDouble、OptionalLong

* 数值范围：range(不包含结束值)、rangeClosed(包含结束值)

		//范围[0,100]
		IntStream evenNumbers = IntStream.rangeClosed(1, 100)
										 .filter(n -> n % 2 == 0);


例：勾股数

	Stream<int[]> pythagoreanTriples =
		IntStream.rangeClosed(1, 100).boxed()
				 .flatMap(a ->
				 	IntStream.rangeClosed(a, 100)
							 .filter(b -> Math.sqrt(a*a + b*b) % 1 == 0)
							 .mapToObj(b -> new int[]{a, b, (int)Math.sqrt(a*a + b*b)})
				 ); 

###2.2.8 构建流
* 由值创建流

		Stream<String> stream = Stream.of("hello", "world");
		// 空流
		Stream<String> emptyStream = Stream.empty();

* 由数组创建流

		int[] numbers = {2, 3, 5, 7, 11, 13};
		int sum = Arrays.stream(numbers).sum();

* 由文件创建流
		
		// Files.lines会返回由指定文件中各行构成的字符串流。
		Stream<String> lines = Files.lines(Path.get("data.txt"), Charset.defaultCharset());

* 由函数生成流：创建无限流
1) 迭代：接收初始值和UnaryOperator<T>类型的lambda表达式

		// 斐波那契数列
		Stream.iterator(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
			  .limit(10)
			  .map(t -> t[0])
			  .forEach(System.out::println);

2) 生成：接收Supplier<T>类型的lambda提供新的值

		Stream.generate(Math::random)
			  .limit(5)
			  .forEach(System.out::println);

IntStream的generate方法会接受一个IntSupplier，可以直接内联提供方法实现，也可以如下实现getAsInt方法：

		IntSupplier fib = new IntSupplier() {
			private int previous = 0;
			private int current = 1;
			public int getAsInt() {
				int oldPrevious = this.previous;
				int nextValue = this.previous + this.current;
				this.previous = this.current;
				this.current = nextValue;
				return oldPrevious;
			}
		};

		IntStream.generate(fib).limit(10).forEach(System.out::println);

##2.3 用流收集数据
###2.3.1 归约和汇总
* 最值

		Comparator<Dish> dishCaloriesComparator = 
			Comparator.comparingInt(Dish::getCalories);
		Optional<Dish> mostCaloriesDish = 
			menu.stream().collect(maxBy(dishCaloriesComparator));	
* 求和

		int totalCalories = 
			menu.stream()
				.collect(summingInt(Dish::getCalories));

* 平均数

		double avgCalories = 
			menu.stream().collect(averagingInt(Dish::getCalories));

* summarizingInt工厂

		IntSummaryStatistics menuStatistics = 
			menu.stream().collect(summarizingInt(Dish::getCalories));

> 该收集器会把个数、总和、最值、平均数信息收集到IntSummaryStatistics的类里，它提供了取值(getter)方法来访问结果。

		

* 连接字符串

		String shortMenu = menu.stream().map(Dish::getName).collect(joining());
		// 可添加分隔符
		String shortMenu = menu.stream().map(Dish::getName).collect(joining(", "));

* 广义的归约操作

		// reduce接受三个参数：
		// 第一个参数是归约操作的起始值
		// 第二个参数是转换函数
		// 第三个参数是BinaryOperator
		int totalCalories = menu.stream().collect(
			reducing(0, Dish::getCalories, Integer::sum));
		// 也可以使用单参数形式
		Optional<Dish> mostCalorieDish = 
			menu.stream().collect(reducing(
				(d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));

###2.3.2 分组
* 简单分组

		// 方法引用
		Map<Dish.Type, List<Dish>> dishesByType = 
			menu.stream().collect(groupingBy(Dish::getType));
		// lambda表达式形式
		public enum CaloricLevel {DIET, NORMAL, FAT}
		Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = 
			menu.stream().collect(groupingBy(dish -> {
				if (dish.getCalories() <= 400)
					return CaloricLevel.DIET;
				else if (dish.getCalories() <= 700)
					return CaloricLevel.NORMAL;
				else return CaloricLevel.FAT;
			}));
* 多级分组

		Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = 
		menu.stream().collect(
			groupingBy(Dish::getType, 
				groupingBy(dish -> {
					if (dish.getCalories() <= 400)
						return CaloricLevel.DIET;
					else if (dish.getCalories() <= 700)
						return CaloricLevel.NORMAL;
					else return CaloricLevel.FAT;
				})
			)
		);

* 按子组收集数据

		// groupingBy(f)实际上是groupingBy(f, toList())的简便写法。
		Map<Dish.Type, Long> typesCount = menu.stream().collect(
			groupingBy(Dish::getType, counting()));
		
		Map<Dish.Type, Optional<Dish>> mostCaloricByType = 
			menu.stream()
				.collect(groupingBy(Dish::getType, maxBy(comparingInt(Dish::getCalories))));

* 把收集器的结果转换为另一种类型: Collectors.collectingAndThen
		
		Map<Dish.Type, Dish> mostCaloricByType = 
			menu.stream()
				.collect(groupingBy(Dish::getType,
						collectingAndThen(maxBy(comparingInt(Dish::getCalories)),Optional::get)));

* 与groupingBy联合使用的其他收集器的例子

		Map<Dish.Type, Integer> totalCaloriesByType = 
			menu.stream().collect(groupingBy(Dish::getType, 
				summingInt(Dish::getCalories)));

常和groupingBy联合使用的收集器是mapping方法生成的，它接受两个参数：一个函数对流中的元素做变换，另一个将变换的结果对象收集起来。

	Map<Dish.Type, Set<CaloriesLevel>> caloricLevelsByType = 
			menu.stream().collect(groupingBy(Dish::getType, 
				mapping(dish -> {
					if (dish.getCalories() <= 400) return CaloriesLevel.DIET;
					else if (dish.getCalories() <= 700) return CaloriesLevel.NORMAL;
					else return CaloriesLevel.FAT;}, toSet())));

	// 通过toCollection可以对返回类型有更多控制
	Map<Dish.Type, Set<CaloriesLevel>> caloricLevelsByType = 
			menu.stream().collect(groupingBy(Dish::getType, 
				mapping(dish -> {
					if (dish.getCalories() <= 400) return CaloriesLevel.DIET;
					else if (dish.getCalories() <= 700) return CaloriesLevel.NORMAL;
					else return CaloriesLevel.FAT;}, toCollection(HashSet::new))));

###2.3.3 分区
分区是分组的特殊情况，它由谓词作为分类函数，最多可以分为true和false两组。

	Map<Boolean, List<Dish>> partitionedMenu = 
		menu.stream().collect(partitioningBy(Dish::isVegetarian));
	List<Dish> vegetarianDishes = partitionedMenu.get(true);

传递第二个收集器：

	// 对素食和非素食按类型分组
	
	Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType = 
		menu.stream().collect(
			partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));

例：按质数和非质数分区

	public boolean isPrime(int candidate) {
		int candidateRoot = (int) Math.sqrt((double) candidate);
		return IntStream.rangeClosed(2, candidate)
						.noneMatch(i -> candidate % i == 0);
	}

	public Map<Boolean, List<Integer>> partitionPrimes(int n) {
		return IntStream.rangeClosed(2, n).boxed()
						.collect(partitioningBy(candidate -> isPrime(candidate)));
	}

###2.3.4 Collectors类的静态工厂方法
| 工厂方法 | 返回类型 | 作用 |
| :----:| :----: | :----|
| toList | List\<T> | 把流中所有项目收集到一个List |
| toSet | Set\<T> | 把流中所有项目收集到一个Set，删除重复项 |
| toCollection | Collection\<T> | 把流中所有项目收集到给定的供应源创建的集合 |
| counting | Long | 计算流中元素的个数 |
| summingInt | Integer | 对流中项目的一个整数属性求和 |
| averagingInt | Double | 计算流中项目Integer属性的平均值 |
| summarizingInt | IntSummaryStatistics | 收集关于流中项目Integer属性的统计值，例如最大、最小、总和与平均值 |
| joining | String | 连接对流中每个项目调用toString方法所生成的字符串 |
| maxBy | Optional<T> | 一个包裹了流中按照给定比较器选出的最大元素的Optional |
| minBy | Optional<T> | 一个包裹了流中按照给定比较器选出的最小元素的Optional |
| reducing | 归约操作产生的类型 | 从一个初始值开始，利用BinaryOperator与流中的元素逐个结合，从而将流归约为单个值 |
| collectionAndThen | 转换函数返回的类型 | 包裹另一个收集器，对其结果应用转换函数 |
| groupingBy | Map<K, List<T>> | 根据项目的一个属性的值对流中的项目分组，并将属性值作为结果Map的键 |
| partitioningBy | Map<Boolean, List<T>> | 根据对流中每个项目应用谓词的结果来对项目进行分区 |

###2.3.5 用流收集数据

	/**
	 * Collector接口
	 * T: 流中要收集的项目的泛型
	 * A: 累加器的类型
	 * R: 收集操作得到的对象类型
	 */
	public interface Collector<T, A, R> {
		Supplier<A> supplier();
		BiConsumer<A, T> accumulator();
		Function<A, R> finisher();
		BinaryOperator<A> combiner();
		Set<Characteristics> characteristics();
	}

* supplier方法：建立新的结果容器
* accumulator方法：将元素添加到结果容器
* finisher方法：对结果容器应用最终转换
> 遍历完流后，finisher方法必须返回在累积过程的最后要调用的一个函数，以便将累加器对象转换为整个集合操作的最终结果。通常无需进行转换，只需返回identity函数： return Function.identity();

* combiner方法：合并两个结果容器
> 它定义了对流的各个子部分进行并行处理时，各个子部分归约所得的累加器要如何合并。

* characteristics方法：定义收集器的行为
> Characteristics是一个包含三个项目的枚举  
UNORDERED：规约结果不受流中项目的遍历和累计顺序的影响。  
CONCURRENT：accumulator函数可以从多个线程同时调用，且该收集器可以并行归约流。如果收集器没有标为UNORDERED,那它仅在用于无序数据源时才可以并行归约。  
IDENTITY_FINISH：表明完成器方法返回的函数是一个恒等函数，可以跳过，累加器对象会直接用作归约过程的最终结果。

	public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {
		@Override
		public Supplier<List<T>> supplier() {
			return ArrayList::new;
		}
		
		@Override
		public BiConsumer<List<T>, T> accumulator() {
			return List::add;
		}
		
		@Override
		public Function<List<T>, List<T>> finisher() {
			return Function.identity();
		}
	
		@Override
		public BinaryOperator<List<T>> combiner() {
			return (list1, list2) -> {
				list1.addAll(list2);
				return list1;
			};
		}
	
		@Override
		public Set<Characteristics> characteristics() {
			return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, CONCURRENT));
		}	
	}

	List<Dish> dishes = menu.stream().collect(toList());

	// 进行自定义收集而不去实现Collector
	// 它永远是一个IDENTITY_FINISH和CONCURRENT但非UNORDERED的收集器。
	List<Dish> dishes = menu.stream().collect(
							ArrayList::new, // 供应源
							List::add, // 累加器
							List::addAll); // 组合器

###2.3.6 质数分区优化

	/**
	 * 在下一个质数大于被测数平方根时停止
	 * @param list 被测列表
	 * @param p 检测条件
	 * @return 元素满足谓词的最长前缀
	 */
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
	
	/**
	 * 是否是质数
	 * @param primes 质数列表
	 * @param candidate 被测数
	 * @return
	 */
	public static boolean isPrime(List<Integer> primes, int candidate) {
		int candidateRoot = (int) Math.sqrt(candidate);
		return takeWhile(primes, i -> i <= candidateRoot)
					.stream()
					.noneMatch(i -> candidate % i == 0);
	}

	/**
	 * 获取n以下的质数列表
	 * /
	public Map<Boolean, List<Integer>> partitionPrimes(int n) {
		return IntStream.rangeClosed(2, n).boxed()
						.collect(new PrimeNumbersCollector());
	}



	/**
	 * 自定义质数收集器
	 */ 
	public class PrimeNumbersCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {
		@Override
		public Supplier<Map<Boolean, List<Integer>>> supplier() {
			return () -> new HashMap<Boolean, List<Integer>>() {{
				put(true, new ArrayList<Integer>());
				put(false, new ArrayList<Integer>());
			}};
		}
	
		@Override
		public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
			return (Map<Boolean, List<Integer>> acc, Integer candidate) -> 
				acc.get(MyCollect.isPrime(acc.get(true), candidate))
				   .add(candidate);
		}
	
		@Override
		public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
			return (Map<Boolean, List<Integer>> map1, Map<Boolean, List<Integer>> map2) -> {
				map1.get(true).addAll(map2.get(true));
				map1.get(false).addAll(map2.get(false));
				return map1;
			};
		}
	
		@Override
		public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
			return Function.identity();
		}
	
		@Override
		public Set<Characteristics> characteristics() {
			return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
		}	
	}

##2.4 并行数据处理与性能
###2.4.1 并行流
对顺序流调用parallel方法可以让顺序流变为并行流，类似地，对并行流调用sequential方法就可以把它变为顺序流。但最后一次parallel或sequential调用会影响整个流水线。

**使用并行流的建议**：

* 用适当的基准来检查性能，因为并行流并不总比顺序流快。
* 留意装箱。自动装箱/拆箱会大大降低性能，应使用原始类型流(IntStream、LongStream、DoubleStream)来避免。
* 有些操作本身在并行流上的性能就比顺序流差，特别是limit和findFirst等依赖元素顺序的操作。可以用unordered方法把有序流变成无序流。
* 考虑流背后的数据结构是否易于分解。例如ArrayList的拆分效率比LinkedList高得多。
* 流自身的特点，以及流水线中的中间操作修改流的方式，都可能会改变分解过程的性能。例如筛选操作可能丢弃的元素个数无法预测，导致流本身的大小未知。
* 要考虑终端操作中合并步骤的代价是大是小，(例如Collector中的combiner方法)。如果这一步代价很大，那么组合每个子流产生的部分结果所付出的代价就可能会超过通过并行流得到的性能提升。

**流的数据源和可分解性**

| 工厂方法 | 返回类型 |
| :----:| :----: |
| ArrayList | 极佳 |
| LinkedList | 差 |
| IntStream.range | 极佳 |
| Stream.iterate | 差 |
| HashSet | 好 |
| TreeSet | 好 |

###2.4.2 分支/合并框架
用分支/合并框架求和

	public class ForkJoinSumCalculator extends RecursiveTask<Long> {
		// 要求和的数组
		private final long[] numbers;
		// 子任务处理的数组的起始和终止位置
		private final int start;
		private final int end;
		
		// 不再将任务分解为子任务的数组大小
		public static final long THRESHOLD = 10000;
		
		// 公共构造函数用于创建主任务
		public ForkJoinSumCalculator(long[] numbers) {
			this(numbers, 0, numbers.length);
		}
		
		// 私有构造函数用于以递归方式为主任务创建子任务
		public ForkJoinSumCalculator(long[] numbers, int start, int end) {
			this.numbers = numbers;
			this.start = start;
			this.end = end;
		}
		
		@Override
		protected Long compute() {
			// 该任务负责求和部分的大小
			int length = end - start;
			if (length <= THRESHOLD) {
				// 顺序计算结果
				return computeSequentially();
			}
			// 创建一个子任务来为数组的前一半求和
			ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length/2);
			// 利用另一个ForkJoinPool线程异步执行新创建的子任务
			leftTask.fork();
			// 创建一个子任务来为数组的后一半求和
			ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length/2, end);
			// 同步执行第二个子任务，有可能允许进一步递归划分
			Long rightResult = rightTask.compute();
			// 读取第一个子任务的结果，如果尚未完成就等待
			Long leftResult = leftTask.join();
			
			return leftResult + rightResult;
		}
	
		/**
		 * 在子任务不再可分时计算结果的简单算法
		 * @return
		 */
		private long computeSequentially() {
			long sum = 0;
			for (int i = start; i < end; i++) {
				sum += numbers[i];
			}
			return sum;
		}
	}

	public static long forkJoinSum(long n) {
		long[] numbers = LongStream.rangeClosed(1, n).toArray();
		ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
		return new ForkJoinPool().invoke(task);
	}

**使用分支/合并框架的最佳做法**

* 对一个任务调用join方法会阻塞调用方，直到该任务做出结果。因此，有必要在两个子任务的计算都开始后再调用它，否则每个子任务都必须等待另一个子任务完成才能启动。
* 不应该在RecursiveTask内部使用ForkJoinPool的invoke方法。应该始终直接调用compute或fork方法，只有顺序代码才应该用invoke来启动并行计算。
* 对子任务调用fork方法可以把它排进ForkJoinPool，同时对两个子任务调用它的效率要比直接对其中一个调用compute低，直接调用compute可以为其中一个子任务重用同一线程，从而避免在线程池中多分配一个任务造成的开销。

###2.4.3 Spliterator(可分迭代器)

	public interface Spliterator<T> {
		boolean tryAdvance(Consumer<? super T> action);
		Spliterator<T> trySplit();
		long estinmateSize();
		int characteristics();
	}

* Spliterator 特性

| 特性 | 含义 |
| :----: | :---- |
| ORDERED | 元素有既定的顺序，因此Spliterator在遍历和划分时也会遵循这一顺序 |
| DISTINCT | 对于任意一对遍历过的元素x和y，x.equals(y)返回false |
| SORTED | 遍历的元素按照一个预定义的顺序排序 |
| SIZED | 该Spliterator由一个已知大小的源建立，因此estimatedSized()返回的是准确值 |
| NONNULL | 保证遍历的元素不会为null |
| IMMUTABL | Spliterator的数据源不能修改。这意味着在遍历时不能添加、删除或修改任何元素 |
| CONCURRE | 该Spliterator的数据源可以被其他线程同时修改而无需同步 |
| SUBSIZED | 该Spliterator和所有从它拆分出来的Spliterator都是SIZED |

示例：计算字符串中单词个数

	public class WordCounterSpliterator implements Spliterator<Character> {
		private final String string;
		private int currentChar = 0;
		
		public WordCounterSpliterator(String string) {
			this.string = string;
		}
		
		@Override
		public boolean tryAdvance(Consumer<? super Character> action) {
			// 处理当前字符
			action.accept(string.charAt(currentChar++));
			// 如果还有字符要处理，则返回true
			return currentChar < string.length();
		}
	
		@Override
		public Spliterator<Character> trySplit() {
			int currentSize = string.length() - currentChar;
			if (currentSize < 10) {
				// 返回null表示要解析的string已经足够小，可以顺序处理
				return null;
			}
			// 将试探拆分位置设定为要解析的string中间
			for (int splitPos = currentSize / 2 + currentChar;
					splitPos < string.length(); splitPos++) {
				// 从拆分位置前进知道下一个空格
				if (Character.isWhitespace(string.charAt(splitPos))) {
					// 创建一个新WordCounterSpliterator来解析string从开始到拆分位置的部分
					Spliterator<Character> spliterator = 
						new WordCounterSpliterator(string.substring(currentChar, splitPos));
					// 将起始位置设为拆分位置
					currentChar = splitPos;
					return spliterator;
				}
			}
			return null;
		}
	
		@Override
		public long estimateSize() {
			return string.length() - currentChar;
		}
	
		@Override
		public int characteristics() {
			return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
		}		
	}

	
	final String SENTENCE = "All things in their being are good for something.";
	Spliterator<Character> spliterator = new WordCounterSpliterator(SENTENCE);
	Stream<Character> stream = StreamSupport.stream(spliterator, true);
	System.out.println("Found " + countWords(stream) + " words");

#3 其他
##3.1 默认方法
解决多重继承的规则：

* 类中的方法优先级最高。类或父类中声明的方法的优先级高于任何声明为默认方法的优先级。
* 如果无法根据第一条进行判断，那么子接口的优先级更高：函数签名相同时，优先选择拥有最具体实现的默认方法的接口，即如果B继承了A，那么B就比A更具体。
* 最后，如果还是无法判断，继承了多个接口的类必须通过显示覆盖和调用期望的方法，显式地选择使用哪一个默认方法的实现。    

显式调用示例：

	interface A {
		default void hello() {
			System.out.println("Hello from A");
		}
	}
	
	interface B {
		default void hello() {
			System.out.println("Hello from B");
		}
	}
	
	public vlass C implements B, A {
		void hello() {
			B.super.hello();
		}
	}

##3.2 用Optional取代null
###3.2.1 创建Optional对象

* 声明空的Optional
> Optional<Car> optCar = Optional.empty();

* 根据非空值创建Optional
> Optional<Car> optCar = Optional.of(car);  
> 如果car是一个null，这段代码会立即抛出一个
NullPointerException。

* 可接受null的Optional
> Optional<Car> optCar = Optional.ofNullable(car);

###3.2.2 Optional类的方法

| 方法 | 描述 |
| :----: | :---- |
| empty | 返回一个空的Optional实例 |
| filter | 如果值存在并且满足提供的谓词，就返回包含该值的Optional对象；否则返回一个空的Optional对象 |
| flatMap | 如果值存在，就对该值执行提供的mapping函数调用，返回一个Optional类型的值，否则就返回一个空的Optional对象 |
| get | 如果该值存在，将该值用Optional封装返回，否则抛出一个NoSuchElementException |
| ifPresent | 如果值存在，就执行使用该值的方法调用，否则什么也不做 |
| isPresent | 如果值存在就返回true，否则返回false |
| map | 如果值存在，就对该值执行提供的mapping函数调用 |
| of | 将指定值用Optional封装之后返回，如果该值为null，则抛出一个NullPointerException异常 |
| ofNullable | 将指定值用Optional封装之后返回，如果该值为null，则返回一个空的Optional对象 |
| orElse | 如果有值则将其返回，否则返回一个默认值 |
| orElseGet | 如果有值则将其返回，否则返回一个由指定的Supplier接口生成的值 |
| orElseThrow | 如果有值则将其返回，否则抛出一个由指定的Supplier接口生成的异常 |

##3.3 新的日期和时间API
###3.3.1 LocalDate 和 LocalTime

	LocalDate date = LocalDate.of(2014, 9, 1);
	// int year = date.getYear();
	int year = date.get(ChronoField.YEAR);
	// Month month = date.getMonth();
	int month = date.get(ChronoField.MONTH_OF_YEAR);
	// int day = date.getDayOfMonth();
	int day = date.get(ChronoField.DAY_OF_MONTH);
	DayOfWeek dow = date.getDayOfWeek();
	int len = date.lengthOfMonth();
	boolean leap = date.isLeapYear();
	LocalDate today = LocalDate.now();
	
	LocalTime time = LocalTime.of(3, 40, 20);
	int hour = time.getHour();
	int minute = time.getMinute();
	int second = time.getSecond();
	
	LocalDate myDate = LocalDate.parse("2020-04-11");
	LocalTime myTime = LocalTime.parse("11:30:05");

###3.3.2 合并日期和时间

	LocalDateTime dt1 = LocalDateTime.of(2020, 4, 10, 14, 26, 40);
	LocalDateTime dt2 = LocalDateTime.of(date, time);
	LocalDateTime dt3 = date.atTime(14, 10, 22);
	LocalDateTime dt4 = date.atTime(time);
	LocalDateTime dt5 = time.atDate(date);
	LocalDate date1 = dt1.toLocalDate();
	LocalTime time1 = dt1.toLocalTime();

###3.3.3 机器的日期和时间格式

	Instant.ofEpochSecond(3);
	// 2秒后再加10万纳秒(1秒)
	Instant.ofEpochSecond(2, 1_000_000_000);
	// 4秒之前的10万纳秒(1秒)
	Instant.ofEpochSecond(4, -1_000_000_000);

###3.3.4 定义Duration 或 Period

	// Duration类主要用于以秒和纳秒衡量时间的长短,不能仅向between方法传递一个LocalDate对象作参数。
	Duration d1 = Duration.between(time1, time);
	Duration d2 = Duration.between(dt1, dt2);
	Duration d3 = Duration.between(instant1, instant2);
	
	// Period可以以年、月、日的方式对多个时间单位建模
	Period days = Period.between(date, date1);
	System.out.println(days.getDays());
	
	Duration threeMinutes1 = Duration.ofMinutes(3);
	Duration threeMinutes2 = Duration.of(3, ChronoUnit.MINUTES);
	
	Period tenDays = Period.ofDays(10);
	Period threeWeeks = Period.ofWeeks(3);
	Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);

日期-时间类中表示时间间隔的通用方法

| 方法名 | 是否是静态方法 | 方法描述 |
| :---: | :---: | :--- |
| between | 是 | 创建两个时间点之间的interval |
| form | 是 | 由一个临时时间点创建interval |
| of | 是 | 由它的组成部分创建interval的实例 |
| parse | 是 | 由字符串创建interval的实例 |
| addTo | 否 | 创建该interval的副本，并将其叠加到某个指定的temporal对象 |
| get | 否 | 读取该interval的状态 |
| isNegative | 否 | 检查该interval是否为负值，不包含零 |
| isZero | 否 | 检查该interval的时长是否为零 |
| minus | 否 | 通过减去一定的时间创建该interval的副本 |
| multipliedBy | 否 | 将interval的值乘以某个标量创建该interval的副本 |
| negated | 否 | 以忽略某个时长的方式创建该interval的副本 |
| plus | 否 | 以增加某个指定的时长的方式创建该interval的副本 |
| subtractFrom | 否 | 从指定的temporal对象中减去该interval |

###3.3.5 操纵日期

* 修改日期属性


		// 这些方法会返回修改了属性的对象，它们都不会修改原来的对象
		// 以比较直观的方式操纵LocalDate的属性
		LocalDate date1 = LocalDate.of(2014, 3, 18);
		LocalDate date2 = date1.withYear(2011);
		LocalDate date3 = date2.withDayOfMonth(25);
		LocalDate date4 = date3.with(ChronoField.MONTH_OF_YEAR, 9);
		
		// 以相对方式修改LocalDate对象的属性
		LocalDate date5 = LocalDate.of(2014, 3, 18);
		LocalDate date6 = date5.plusWeeks(1);
		LocalDate date7 = date6.minusYears(3);
		LocalDate date8 = date7.plus(6, ChronoUnit.MONTHS);


**表示时间点的日期-时间类的通用方法**

| 方法名 | 是否是静态方法 | 描述 |
| :---: | :---: | :--- |
| from | 是 | 依据传入的Temporal对象创建对象实例 |
| now | 是 | 依据系统时钟创建Temporal对象 |
| of | 是 | 由Temporal对象的某个部分创建该对象的实例 |
| parse | 是 | 由字符串创建Temporal对象的实例 |
| atOffset | 否 | 将Temporal对象和某个时区偏移相结合 |
| atZone | 否 | 将Temporal对象和某个时区相结合 |
| format | 否 | 使用某个指定的格式器将Temporal对象转换为字符串（Instant类不提供该方法）|
| get | 否 | 读取Temporal对象的某一部分值 |
| minus | 否 | 创建Temporal对象的一个副本，通过将当前Temporal对象的值减去一定的时长创建该副本 |
| plus | 否 | 创建Temporal对象的一个副本，通过将当前Temporal对象的值加上一定的时长创建该副本 |
| with | 否 | 以该Temportal对象为模板，对某些状态进行修改创建该对象的副本 |

* 使用预定义的TemporalAdjuster

		LocalDate date1 = LocalDate.of(2020, 4, 10);
		LocalDate date2 = date1.with(nextOrSame(DayOfWeek.SUNDAY));
		LocalDate date3 = date2.with(lastDayOfMonth());

**TemporalAdjuster类中的工厂方法**

| 方法名 | 描述 |
| :---: | :--- |
| dayOfWeekInMonth | 创建一个新的日期，它的值为同一个月中每一周的第几天 |
| firstDayOfMonth | 创建一个新的日期，它的值为当月的第一天 |
| firstDayOfNextMonth | 创建一个新的日期，它的值为下月的第一天 |
| firstDayOfNextYear | 创建一个新的日期，它的值为明年的第一天 |
| firstDayOfYear | 创建一个新的日期，它的值为当年的第一天 |
| firstInMonth | 创建一个新的日期，它的值为同一个月中，第一个符合星期几要求的值 |
| lastDayOfMonth | 创建一个新的日期，它的值为当月的最后一天 |
| lastDayOfNextMonth | 创建一个新的日期，它的值为下月的最后一天 |
| lastDayOfNextYear | 创建一个新的日期，它的值为明年的最后一天 |
| lastDayOfYear | 创建一个新的日期，它的值为今年的最后一天 |
| lastInMonth | 创建一个新的日期，它的值为同一个月中，最后一个符合星期几要求的值 |
| next/previous | 创建一个新的日期，并将其值设定为日期调整前或调整后，第一个符合星期几要求的值 |
| nextOrSame/previousOrSame | 创建一个新的日期，并将其值设定为日期调整后或者调整前，第一个符合星期几要求的日期，如果该日期已经符合要求，直接返回该对象。 |

* 自定义TemporalAdjuster

		class NextWorkingDay implements TemporalAdjuster {
			@Override
			public Temporal adjustInto(Temporal temporal) {
				DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
				int dayToAdd = 1;
				if (dow == DayOfWeek.FRIDAY) {
					dayToAdd = 3;
				} else if (dow == DayOfWeek.SATURDAY) {
					dayToAdd = 2;
				}
				
				return temporal.plus(dayToAdd, ChronoUnit.DAYS);
			}
		}

		LocalDate date = date.with(new NextWorkingDay());

###3.3.6 解析和格式化日期

* 预定义格式

		LocalDate date = LocalDate.of(2014, 3, 28);
		String s1 = date.format(DateTimeFormatter.BASIC_ISO_DATE); //20140318
		String s2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE); //2014-03-18
		
		LocalDate date1 = LocalDate.parse("20140318", DateTimeFormatter.BASIC_ISO_DATE);
		LocalDate date2 = LocalDate.parse("2014-03-18", DateTimeFormatter.ISO_LOCAL_DATE);

* 按照某个模式创建DateTimeFormatter
  
 		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String formattedDate = date1.format(formatter);
		LocalDate date3 = LocalDate.parse(formattedDate, formatter);

* 创建本地化的DateTimeFormatter
		
		DateTimeFormatter italianFormatter = 
			DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);

* 构造一个DateTimeFormatter

		DateTimeFormatter italianFormatter = new DateTimeFormatterBuilder()
			.appendText(ChronoField.DAY_OF_MONTH)
			.appendLiteral(". ")
			.appendText(ChronoField.MONTH_OF_YEAR)
			.appendLiteral(" ")
			.appendText(ChronoField.YEAR)
			.parseCaseInsensitive()
			.toFormatter(Locale.ITALIAN);

###3.3.7 处理不同的时区和历法

	ZoneId romeZone = ZoneId.of("Europe/Rome");
	LocalDate date = LocalDate.of(2014, Month.MARCH, 18);
	ZonedDateTime zdt1 = date.atStartOfDay(romeZone);
	
	LocalDateTime dateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
	ZonedDateTime zdt2 = dateTime.atZone(romeZone);
	
	Instant instant = Instant.now();
	ZonedDateTime zdt3 = instant.atZone(romeZone);