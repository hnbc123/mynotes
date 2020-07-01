#1 操作符
##1.1 方法调用中的别名问题
> 基本类型存储了实际的数值，而并非指向一个对象的引用，所以在为其赋值的时候，是直接将一个地方的内容复制到另一个地方。

> 但是对对象进行操作时，真正操作的是对象的引用。将对象传递给方法时实际只是传递了一个引用，对传递来的参数进行操作改变的是方法外部的对象。

##1.2 按位操作符
|  | 两个都是1 | 两个都是0 | 一个1一个0 |
| :----:| :----: | :----: | :----: |
| 与（&） | 1 | 0 | 0 |
| 或（&#124;） | 1 | 0 | 1 |
| 异或（^）| 0 | 0 | 1 |

> 非（~），也称取反操作符，是一元操作符，生成与输入位相反的值。

##1.3 移位操作符
* 左移位操作符（<<）
	> 按照操作符右侧指定的位数将操作符左侧的操作数向左移动（低位补0）。
* 有符号右移位操作符（>>）
	> 按照操作符右侧指定的位数将操作符左侧的操作数向右移动（低位补0）。 
	> 若符号为正，则在高位插入0；若符号为负，则在高位插入1。
* 无符号右移位操作符（>>>）
	> 无论正负都在高位插入0。

> 移位与等号组合使用时，如果对byte或short值进行这样的移位运算，它们会先被转成int型，再进行右移操作，然后被截断，赋值给原来的类型。

    
#2 控制执行流程
##2.1 break 和 continue 的区别
* 一般的continue会退回最内层循环的开头（顶部）并继续执行。
* 带标签的continue会到达标签的位置，并重新进入紧接在那个标签后面的循环。
* 一般的break会中断并跳出当前的最内层循环。
* 带标签的break会中断并跳出标签所指的循环。

#3 初始化与清理
##3.1 成员初始化
###3.1.1 默认值
> 类的每个基本类型数据成员都会有一个初始值。类里定义一个对象引用时，如果不将其初始化，此引用就会获得一个特殊值null。对于方法的局部变量，如果未定义初始值，编译器不会为其设置初始值，而是显示错误信息。
###3.1.2 初始化顺序
> 在类的内部，定义变量的先后顺序决定了初始化的顺序，变量会在任何方法（包括构造器）被调用之前得到初始化。

> 初始化的顺序是先静态对象（如果尚未初始化），而后是非静态对象。只有第一个相关对象被创建（或者第一次访问静态数据）的时候它们才会被初始化。此后静态对象不会再次被初始化。

> 总结（假设有个名为Dog的类）：  
> 1. 首次创造类型为Dog的对象时（构造器可以看成静态方法），或者Dog类的静态方法/静态域首次被访问时，Java解释器查找类路径，以定位Dog.class文件。  
> 2. 载入Dog.class，有关静态初始化的所有动作都会执行。因此静态初始化只在Class对象首次加载的时候进行一次。  
> 3. 当用new Dog()创建对象时，首先将在堆上为Dog对象分配足够的存储空间。  
> 4. 这块存储空间会被清零，这就自动地将Dog对象中的所有基本类型数据都设置成了默认值，而引用则被设置成了null。  
> 5. 执行所有出现于字段定义处的初始化动作。  
> 6. 执行构造器。

##3.2 其他
* 基本类型传递给重载方法时，如果有某个重载方法接受该类型参数，它就会被调用。
* static方法的内部不能调用非静态方法。    

#4 访问权限控制
* 包访问权限
> 默认访问权限，无关键字。当前的包中所有类对该成员都有访问权限，但对于这个包之外的所有类，这个成员是private。

* 接口访问权限（public）
> public之后紧跟的成员声明自己对每个人都是可用的。

* 私有访问权限（private）
> 除了包含该成员的类外，其他任何类都无法访问这个成员。

* 继承访问权限（protect）
> 提供包访问权限，该类的子类也具有访问权限。

#5 复用类
##5.1 继承
Java会自动在导出类的构造器中插入对基类构造器的调用，构建过程是从基类向外扩散的，基类在导出类构造器可以访问它之前就已经完成了初始化。

如果基类没有无参构造器或需要调用一个带参数的基类构造器，就必须要用super显式地编写调用基类构造器的语句，而且其必须位于导出类构造器的起始位置。

在导出类中也可对基类的方法进行重载。

##5.2 组合与继承的选择
组合技术通常用于想在新类中使用仙有泪的功能而非它的接口的情形。即在新类中嵌入某个对象，让其实现所需要的功能，但新类的用户看到的只是为新类所定义的接口，而非所嵌入对象的接口。

继承是使用某个现有类并开发一个它的特殊版本。通常这意味着你在使用一个通用类，并为了某种特殊需要而将其特殊化。

##5.3 final和private关键字
类中所有的private方法都隐式地指定为final。由于无法取得private方法，所以也就无法覆盖它。如果试图覆盖一个private方法，实际上是在导出类生成了一个新的方法。

#6 多态
##6.1 多态机制
> 只有普通的方法调用可以是多态的。例如，如果你直接访问某个域，这个访问就将在编译期进行解析;如果某个方法是静态的，它的行为就不具有多态性；private方法不可被覆盖。


	public class StaticPolymorphism {
		public static void main(String[] args) {
			StaticSuper sup = new StaticSub();
			// 静态对象是与类，而并非单个的对象相关联的
			System.out.println(sup.staticGet());
			System.out.println(sup.dynamicGet());
		}
	}
	
	class StaticSuper {
		public static String staticGet() {
			return "Base staticGet()";
		}
		
		public String dynamicGet() {
			return "Base dynamicGet()";
		}
	}
	
	class StaticSub extends StaticSuper {
		public static String staticGet() {
			return "Derived staticGet";
		}
		
		public String dynamicGet() {
			return "Derived dynamicGet()";
		}
	}

##6.2 构造器的调用顺序
* 1) 在其他任何事物发生之前，将分配给对象的存储空间初始化成二进制的零。
* 2) 调用基类构造器。这个步骤会不断地反复递归下去，首先是构造这种层次结构的根，然后是下一层导出类等等，直到最低层的导出类。
* 3) 按声明顺序调用成员的初始化方法。
* 4) 调用导出类构造器的主体。  


		public class PolyConstructors extends Glyph {
			public static void main(String[] args) {
				new RoundGlyph(5);
			}
		}
		
		class Glyph {
			void draw() {
				System.out.println("Glyph.draw()");
			}
			
			Glyph() {
				System.out.println("Glyph() before draw()");
				// 调用的是被覆盖后的draw()方法
				// 由于初始化最开始时将分配给对象的存储空间初始化为零，因此radius为0。
				draw();
				System.out.println("Glyph() after draw()");
			}
		}
		
		class RoundGlyph extends Glyph {
			private int radius = 1;
			RoundGlyph(int r) {
				radius = r;
				System.out.println("RoundGlyph.RoundGlyph(), radius = " + radius);
			}
			
			void draw() {
				System.out.println("GoundGlyph.draw(), radius = " + radius);
			}
		}

#7 接口
> 可以选择在接口中显式地将方法声明为public的，但即使你不这么做，它们也是public的。
> 放在接口中的任何域都自动是public、static和final的。

> 如果要从一个非接口的类继承，那么只能从一个类去继承，其余的基元素都必须是接口。需要将所有接口名都置于implements关键字之后，用逗号将它们一一隔开。可以继承任意多个接口，并可以向上转型为每个接口。接口继承可以将extends关键字用于多个基类接口。

#8 内部类
* 成员内部类
> 内部类自动拥有对其外围类所有成员的访问权，内部类的对象只能在与其外围类的对象相关联的情况下才能被创建。

下面是内部类的示例：   
迭代器接口：

	public interface Selector {
		boolean end();
		
		Object current();
		
		void next();
	}

内部类实现迭代器接口：    

	public class Sequence {
		private Object[] items;
		
		private int next = 0;
		
		public Sequence(int size) {
			items = new Object[size];
		}
		
		public void add(Object x) { 
			if (next < items.length) {
				items[next++] = x;
			}
		}
		
		private class SequenceSelector implements Selector {
			private int i = 0;
			
			@Override
			public boolean end() {
				return i == items.length;
			}
	
			@Override
			public Object current() {
				return items[i];
			}
	
			@Override
			public void next() {
				if (i < items.length) {
					i++;
				}
			}
		}
		
		public Selector selector() {
			return new SequenceSelector();
		}
		
		public static void main(String[] args) {
			Sequence sequence = new Sequence(10);
			for (int i = 0; i < 10; i++) {
				sequence.add(Integer.toString(i));
			}
			
			Selector selector = sequence.selector();
			while (!selector.end()) {
				System.out.println(selector.current() + "");
				selector.next();
			}
		}
	}

要想直接创建内部类的对象，必须使用外部类的对象来创建该内部类的对象。    

	public class DotNew {
		public class Inner {
		}
		
		public static void main(String[] args) {
			DotNew dn = new DotNew();
			DotNew.Inner dni = dn.new Inner();
		}
	}


* 局部内部类
> 在方法的作用域内创建的类。

* 匿名内部类
> 如果定义一个匿名内部类，并且希望它使用一个在其外部定义的对象，那么编译器会要求其引用参数是final的。
(jdk1.8之后可不加final。)

* 嵌套类(静态内部类)
> 普通的内部类对象隐式地保存了一个引用，指向创建它的外围类对象。如果不需要内部类对象与其外围类对象之间有联系，那么可以将内部类声明为static。不能从嵌套类的对象中访问非静态的外围类对象。

#9 Foreach 与迭代器
任何实现Iterator接口的类都可以用于foreach语句中

	public class IterableClass implements Iterable<String> {
		protected String[] words = "And that is how we know the Earth to be banana-shaped.".split(" ");
	
		@Override
		public Iterator<String> iterator() {
			return new Iterator<String>() {
				private int index = 0;
	
				@Override
				public boolean hasNext() {
					return index < words.length;
				}
	
				@Override
				public String next() {
					return words[index++];
				}
				
				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
				
			};
		}
		
		public static void main(String[] args) {
			for (String s : new IterableClass()) {
				System.out.println(s);
			}
		}
		
	}

添加一种或多种在foreach中使用这个类的方法：

	public class MultiIterableClass extends IterableClass {
		public Iterable<String> reversed() {
			return new Iterable<String>() {
			
				@Override
				public Iterator<String> iterator() {
					return new Iterator<String>() {
						int current = words.length - 1;
						
						@Override
						public boolean hasNext() {
							return current > -1;
						}
	
						@Override
						public String next() {
							return words[current--];
						}
						
						@Override
						public void remove() {
							throw new UnsupportedOperationException();
						}
					};
				}			
			};
		}
		
		public Iterable<String> randomized() {
			return new Iterable<String>() {
				@Override
				public Iterator<String> iterator() {
					List<String> shuffled = new ArrayList<String>(Arrays.asList(words));
					Collections.shuffle(shuffled, new Random(47));
					return shuffled.iterator();
				}
			};
		}
		
		public static void main(String[] args) {
			MultiIterableClass mic = new MultiIterableClass();
			for (String s : mic.reversed()) {
				System.out.print(s + " ");
			}
			System.out.println();
			
			for (String s : mic.randomized()) {
				System.out.print(s + " ");
			}
			System.out.println();
			
			for (String s : mic) {
				System.out.print(s + " ");
			}
			System.out.println();	
		}
	}

#10 异常
##10.1 使用finally
无论异常是否被抛出，finally子句总能运行。

	public class FinallyWorks {
		static int count = 0;
		
		public static void main(String[] args) {
			while(true) {
				try {
					if (count++ == 0) {
						throw new ThreeException();
					} else {
						System.out.println("No Exception");
					}
				} catch(ThreeException e) {
					System.out.println("Three Exception");
				} finally {
					System.out.println("In finally clause");
					if (count == 2) {
						break;
					}
				}
			}
		}
		
		
	}
	
	class ThreeException extends Exception {
		
	}

##10.2 异常丢失

	public class LostMessage {
		void f() throws VeryImportantException {
			throw new VeryImportantException();
		}
		
		void dispose() throws HoHumException {
			throw new HoHumException();
		}
		
		public static void main(String[] args) {
			try {
				LostMessage lm = new LostMessage();
				try {
					lm.f();
				} finally {
					lm.dispose();
				}
			} catch(Exception e) {
				System.out.println(e);
			}
		}
	}
	
	class VeryImportantException extends Exception {
		public String toString() {
			return "A very important exception";
		}
	}

#11 字符串
##11.1 字符串不可变
String对象时不可变的，String类中每一个看起来会修改String值的方法，实际上都是创建了一个全新的String对象，以包含修改后的字符串内容，而最初的String对象则丝毫未动。

##11.2 正则表达式
###11.2.1 常用符号
字符类

| 符号 | 意义 |
| :---: | :--- |
| . | 任意字符 |
| [abc] | 包含a、b和c的任何字符(与a&#124;b&#124;c作用相同) |
| [^abc] | 除了a、b和c之外的任何字符 |
| [a-zA-Z] | 从a到z或从A到Z的任何字符 |
| [abc[hij]] | 任意a、b、c、h、i和j字符(与a&#124;b&#124;c&#124;h&#124;i&#124;j作用相同) |
| [a-z&&[hij]] | 任意h、i或j(交) |
| \s | 空白符(空格、tab、换行、换页和回车) |
| \S | 非空白符（[^\s]) |
| \d | 数字[0-9] |
| \D | 非数字[^0-9] |
| \w | 词字符[a-zA-Z0-9] |
| \W | 非词字符[^\w] |

逻辑操作符

| 符号 | 意义 |
| :---: | :--: |
| XY | Y跟在X后面 |
| X&#124;Y | X或Y |
| (X) | 捕获组 |

边界匹配符

| 符号 | 意义 |
| :---: | :---:|
| ^ | 一行的起始 |
| $ | 一行的结束 |
| \b | 词的边界 |
| \B | 非词的边界 |
| \G | 前一个匹配的结束 |

量词
* 贪婪型：量词总是贪婪的，除非有其他的选项被设置。贪婪表达式会为所有可能的模式发现尽可能多的匹配。
* 勉强型：用问号来指定，这个量词匹配满足模式所需的最少字符数。
* 占有型：占有型量词不保存中间状态，因此他们可以防止回溯。

| 贪婪型 | 勉强型 | 占有型 | 如何匹配 |
| :---: | :---: | :---: | :---: |
| X? | X?? | X?+ | 一个或零个X |
| X* | X&#42;? | X*+ | 零个或多个X |
| X+ | X+? | X++ | 一个或多个X |
| X{n} | X{n}? | X{n}+ | 恰好n次X |
| X{n,} | X{n,}? | X{n,}+ | 至少n次X |
| X{n,m} | X{n,m}? | X{n,m}+ | X至少n次，且不超过m次 |

###11.2.2 Pattern和Matcher
用Pattern.compile()方法来编译正则表达式，它会根据String类型的正则表达式生成一个Pattern对象。然后把想要检索的字符串传入Pattern对象的matcher()方法。matcher()方法会生成一个Matcher对象。

	String s = "Arline ate eight apples and orange while Anita hadn't any.";
		
	Pattern p = Pattern.compile("(?i)((^[aeiou])|(\\s+[aeiou]))\\w+?[aeiou]\\b");
	Matcher m = p.matcher(s);
	while (m.find()) {
		System.out.println("Match \"" + m.group() + "\" at positions "
				+ m.start() + "-" + (m.end() - 1));
	};

### 11.2.3 组
组是用括号划分的正则表达式，可以根据组的编号来引用某个组。组号为0表示整个表达式，组号1表示被第一对括号括起的组，依次类推。在表达式A(B(C))D中有三个组： 组0是ABCD，组1是BC，组2是C。

#12 类型信息
**类字面常量**  
Java提供了.class方法生成对Class对象的引用。当使用".class"来创建对Class对象的引用时，不会自动地初始化该Class对象。为了类而做的准备工作实际包含三个步骤：
* 加载。由类加载器执行，该步骤将查找字节码，并从这些字节码中创建一个Class对象。
* 链接。在链接阶段将验证类中的字节码，为静态域分配存储空间，若必要将解析这个类创建的对其他类的所有引用。
* 初始化。如果该类具有超类，则对其初始化，执行静态初始化器和静态初始化块。

初始化被延迟到了对静态方法（构造器隐式地是静态的）或者非常数静态域首次引用时才执行。

	public class ClassInitialization {
		public static Random rand = new Random(47);
		
		public static void main(String[] args) throws Exception {
			Class initable = Initable.class;
			System.out.println("After creating Initable ref");
			System.out.println(Initable.staticFinal);
			System.out.println(Initable.staticFinal2);
			System.out.println(Initable2.staticNonFinal);
			
			Class initable3 = Class.forName("typeinfo.Initable3");
			System.out.println("After creating Initable3 ref");
			System.out.println(Initable3.staticNonFinal);
		}
	}
	
	class Initable {
		// 编译期常量不需要对类进行初始化就可以得到
		static final int staticFinal = 47;
		// 非编译期常量，将强制类的初始化
		static final int staticFinal2 = ClassInitialization.rand.nextInt(1000);
		static {
			System.out.println("Initializing Initable");
		}
	}
	
	class Initable2 {
		static int staticNonFinal = 147;
		static {
			System.out.println("Initializing Initable2");
		}
	}
	
	class Initable3 {
		static int staticNonFinal = 74;
		static {
			System.out.println("Initializing Initable3");
		}
	}

#13 泛型
##13.1 泛型方法
是否拥有泛型方法，与其所在的类是否是泛型没有关系。泛型方法使得该方法能够独立于类而产生变化。对于一个static方法，无法访问泛型类的的类型参数，所以如果static方法需要使用泛型能力，就必须使其成为泛型方法。定义泛型方法只需将泛型参数列表置于返回值之前。

	public class GenericMethods {
		public <T> void f(T x) {
			System.out.println(x.getClass().getName());
		}
		
		public static void main(String[] args) {
			GenericMethods gm = new GenericMethods();
			gm.f("");
			gm.f(1);
			gm.f(1.0);
			gm.f(1.0f);
			gm.f('c');
			gm.f(gm);
		}
	}

##13.2 擦除
在泛型代码内部，无法获得任何有关泛型参数类型的信息,List<String>和List<Integer>在运行时事实上是相同的类型。  
创建一个new T()的尝试将无法实现，部分原因是因为擦除，另一部分原因是因为编译器不能验证T具有无参构造器。 

##13.3 通配符 
通配符执行List<? extends Fruit>之类的向上转型后，就丢失了向其中传递任何对象的能力。

	public class GenericsAndCovariance {
		public static void main(String[] args) {
			List<? extends Fruit> flist = new ArrayList<Apple>();
	//		flist.add(new Apple());
	//		flist.add(new Fruit());
	//		flist.add(new Object());
			flist.add(null);
			Fruit f = flist.get(0);
		}
	}

指定超类型通配符(<? super MyClass>)可以安全地传递一个类型对象到泛型类型中。

	static void writeTo(List<? super Apple> apples) {
		apples.add(new Apple());
		apples.add(new Jonathan());
	//	apples.add(new Fruit());
	}

参数是Apple的某种基类型的List，向其中添加Apple或Apple的子类型是安全的。但既然Apple是下界，那么向其中添加Fruit是不安全的，因为这样可以向其中添加非Apple类型的对象。

##13.4 使用泛型时出现的问题
* 任何基本类型都不能作为类型参数。
* 一个类不能实现同一个泛型接口的两种变体，由于擦除的原因，这两个变体会成为相同的接口。
* 使用带有泛型类型参数的转型或instanceof不会有任何效果。

#14 容器
##14.1 Set
* Set 
> 存入Set的每个元素都必须是唯一的，Set**不保存**重复元素。加入Set的元素必须定义equals()方法以确保对象的唯一性。Set接口不保证维护元素的次序。

* HashSet 
> 为快速查找而设计的Set。存入HashSet的元素必须定义hashCode()

* TreeSet
> 保持次序的Set，底层为树结构。它可以从Set中提取有序的序列，元素必须实现Comparable接口

* LinkedHashSet
> 具有HashSet的查询速度，且内部使用链表维护元素的顺序。元素必须定义hashCode()方法

##14.2 Map
* HashMap
> Map基于散列表的实现。插入和查询键值对的开销是固定的。

* LinkedHashMap
> 在迭代遍历的时候，取得键值对的顺序使其插入顺序，或是最近最少使用的次序。

* TreeMap
> 基于红黑树的实现。查询所得到的结果是经过排序的。

* WeakHashMap
> 弱键映射，允许释放映射所指向的对象。

* ConcurrentHashMap
> 一种线程安全的Map，它不涉及同步加锁。

* IdentityHashMap
> 使用==代替equals()对键进行比较的散列映射。

##14.3 散列和散列码
equals()方法必须满足下列5个条件：  

* 自反性。对任意x，x.equals(x)一定返回true。
* 对称性。对任意x和y，如果y.equals(x)返回true，则x.equals(y)也返回true。
* 传递性。对任意x、y、z，如果有x.equals(y)返回true，y.equals(z)返回true，则x.equals(z)一定返回true。
* 一致性。对任意x和y，如果对象中用于等价比较的信息没有改变，那么无论调用x.equals(y)多少次，返回的结果应该保持一致。
* 对任何不是null的x，x.equals(null)一定返回false。

默认的Object.equals()只是比较对象的地址，所以一个Groundhog(3)并不等于另一个Groundhog(3)。因此，如果要使用自己的类作为HashMap的键，必须同时重载hashCode()和equals()。  
查询一个值的过程首先是计算散列码，然后使用散列码查询数组。冲突通常由外部链接处理：数组并不直接保存值，而是保存值的list。然后对list中的值使用equals()方法进行线性的查询。

