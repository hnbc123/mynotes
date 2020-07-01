# Java基础
## 1. JDK 和 JRE 有什么区别？
* JDK：Java Development Kit的简称，Java开发工具包，提供了Java的开发环境和运行环境。
* JRE：Java Runtime Environment的简称，Java运行环境。

具体来说 JDK 其实包含了 JRE、编译 java 源码的编译器 javac以及很多 java 程序调试和分析的工具。简单来说：如果你需要运行 java 程序，只需安装 JRE 就可以了；如果你需要编写 java 程序，需要安装 JDK。

## 2. == 和 equals 的区别是什么？

* ==

> 对于基本类型，==比较的是值是否相同，对于引用类型比较的是引用是否相同。

* equals

> equals本质上就是==， 只是String、Integer等类重写了equals方法，把它变成了值比较。

Object里的equals源码如下：

	public boolean equals(Object obj) {
        return (this == obj);
    }

**总结** ：
> == 对于基本类型来说是值比较，对于引用类型来说是比较的是引用；而 equals 默认情况下是引用比较，只是很多类重新了 equals 方法，比如 String、Integer 等把它变成了值比较，所以一般情况下 equals 比较的是值是否相等。

## 3. “==”在字符串比较中的用法

	public class MyTest {
		public static void main(String[] args) {		
			String hello = "Hello World";
			String hello2 = new String("Hello World");
			String world = "World";
			
			// 都是指向常量池的引用
			System.out.println(hello == "Hello World"); // true
			
			// hello是字符串常量表达式,并实现为调用interned; 
			// hello2是一个对象,指向堆上的一个地址
			System.out.println(hello == hello2); // false
			
			// String类重写了equals方法,比较的是字符串内容
			System.out.println(hello.equals(hello2)); // true
			
			// intern()会检查当前字符串池是否包含这个字符串,包含则返回池中的字符串,
			// 否则将字符串对象添加到池中,并返回该字符串对象的引用。
			System.out.println(hello == hello2.intern()); // true
			
			// 不同类/包下的两个常量表达式,依然是指向同一个字符串池的引用
			System.out.println(Other.hello == hello); // true
			
			// 都是指向字符串池的引用
			System.out.println(hello == "Hello " + "World"); // true
			
			// 后者不是常量表达式,是运行时通过串联计算的字符串,会新建对象。
			System.out.println(hello == "Hello " + world);

			// test1,new String("1") 的时候吧“1”和new String("1") 放入堆中，
			// 这时候运行intern()，会把‘1’放入常量池中。然后s2取到的是常量池中的‘1’，这样因为两个指向的不同，所以为false
			String s = new String("1");
			s.intern();
			String s2 = "1";
			System.out.println(s == s2);

			// 同理，s3创建的时候是把4个东西放入了堆中，new String("1")，new String("11")，new String("1")，“1”,
			// 运行方法的时候发现常量池中没有“11”,那么会去堆中的new String("11")，这样其实两个的指向是相同的，所以为true
			String s = new String("1") + new String("1");
			s.intern();
			String s2 = "11";
			System.out.println(s == s2);
		}
	}
	
	class Other {
		static String hello = "Hello World";
	}

**JDK1.6查找到常量池存在相同值的对象时会返回该对象的地址。JDK1.7后，intern方法还是会先去查询常量池中是否有已经存在，如果存在，则返回常量池中的引用，这一点与之前的没有什么区别，区别在于，如果常量池找不到对应的字符串，则不会将字符串复制到常量池，而只是在常量池中生成一个对原字符串的引用。那么其他字符串在常量池找值时就会返回另一个堆中的对象的地址。**


**Java的8种基本类型(Byte, Short, Integer, Long, Character, Boolean, Float, Double), 除Float和Double以外, 其他六种都实现了常量池, 但是它们只在大于等于-128并且小于等于127时才使用常量池。**


## 4. final在Java中有什么作用？
* final修饰的类不能被继承
* final修饰的方法不能被重写
* final修饰的变量叫常量，常量必须初始化，初始化后之不能被修改

## 5. Java中的Math.round(-1.5)等于多少？
等于-1。  
Math.round()方法取整规则：原参数加0.5后向下取整。 

## 6. String属于基础的数据类型吗？
不属于。Java的基础类型有八种：byte、short、int、long、float、double、char、boolean，String属于对象。

## 7. Java中操作字符串都有哪些类？它们之间有什么区别？
操作字符串的类有：String、StringBuffer、StringBuilder。
String声明的是不可变的对象，每次操作都会生成新的String对象，然后将指针指向新的String对象；而StringBuffer、StringBuilder可以在原有对象的基础上进行操作，所以在经常改变字符串内容的情况下最好不要使用String。  
StringBuffer和StringBuilder最大的区别在于StringBuffer是线程安全的，而StringBuilder是非线程安全的，但StringBuilder的性能高于StringBuffer，所以在单线程环境下推荐使用StringBuilder，多线程环境下推荐使用StringBuffer。

