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
>
> 



## 3. “==”在字符串比较中的用法(可忽略)

```java
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
```

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
StringBuffer和StringBuilder最大的区别在于**StringBuffer是线程安全的，而StringBuilder是非线程安全的**，但StringBuilder的性能高于StringBuffer，所以**在单线程环境下推荐使用StringBuilder，多线程环境下推荐使用StringBuffer**。



## 8. 如何将字符串反转？

使用StringBuilder或StringBuffer的reverse()方法。



## 9. String类的常用方法有哪些？

* indexOf(): 返回指定字符的索引
* charAt(): 返回指定索引处的字符
* replace(): 字符串替换
* trim(): 去除字符串两端空白（不只包括空格）
* split(): 分割字符串，返回一个分割后的字符串数组
* getBytes(): 返回字符串的byte类型数组
* length(): 返回字符串长度
* toLowerCase(): 将字符串转成小写字母
* toUpperCase(): 将字符串转成大写字母
* substring(): 截取字符串
* equals(): 字符串比较



## 10. 普通类可抽象类有哪些区别？

* 普通类不能包含抽象方法，抽象类可以包含抽象方法（也可以不包含）。
* 抽象类不能直接实例化，普通类可以直接实例化。
* 普通类可以用final修饰，而抽象类不能。因为定义为final的类不能被继承。



## 11. 接口和抽象类有什么区别？

* 抽象类需使用extends来继承；接口需使用implements来实现
* 抽象类可以有构造函数；接口没有
* 抽象类可以有能运行的main方法；接口不能有
* 类可以实现多个接口，但只能继承一个抽象类（接口的继承用extends，且可继承多个接口）
* 接口中的方法默认使用public修饰



## 12. java中IO流分几种？

按功能分：输入流和输出流

按类型分：字节流和字符流

字节流和字符流的区别：字节流按8位传输以字节为单位输入输出数据；字符流按16位传输以字符为单位输入输出数据。



## 13. BIO、NIO、AIO有什么区别？

* BIO：Block IO 同步阻塞式IO，就是传统IO，特点是模式简单使用方便，并发处理能力低。
* NIO：New IO 同步非阻塞IO，是传统IO的升级，客户端和服务器通过Channel(通道)通讯，实现了多路复用。
* AIO：Asynchronous IO 是NIO的升级，也叫NIO2，实现了异步非阻塞IO，异步IO的操作基于事件和回调机制。



## 14. Files的常用方法有哪些？

* Files.exists(): 检查文件路径是否存在
* Files.createFile(): 创建文件
* Files.createDirectory(): 创建文件夹
* Files.delete(): 删除一个文件或目录
* Files.copy(): 复制文件
* Files.move(): 移动文件
* Files.size(): 查看文件个数
* Files.read(): 读取文件
* Files.write(): 写入文件



# 容器

## 15. Collection和Collections是有什么区别?

* java.util.Collection是一个集合接口。它提供了对集合对象进行基本操作的通用接口方法。Collection接口在Java类库中有很多具体的实现。Collection接口的意义是为各种具体的集合提供了最大化的统一操作方式，其直接继承接口有List和Set。
* Collections是集合类的一个工具类，提供了一系列静态方法，用于对集合中元素进行排序、搜索以及线程安全等各项操作。



## 16. List、Set、Map之间的区别是什么？

| 比较       | List                                                  | Set                                                     | Map                                                          |
| ---------- | ----------------------------------------------------- | ------------------------------------------------------- | ------------------------------------------------------------ |
| 继承接口   | Collection                                            | Collection                                              |                                                              |
| 常见实现类 | AbstractList(常用子类有ArrayList、LinkedList、Vector) | AbstractSet（常用子类有HashSet、LinkedHashSet、TreeSet) | HashMap、HashTable                                           |
| 常见方法   | add()、remove()、clear()、get()、contains()、size()   | add()、remove()、clear()、contains()、size()            | put()、get()、remove()、clear()、containsKey()、containsValue()、keySet()、values()、size() |
| 元素       | 可重复                                                | 不可重复（重复元素不存储）                              | 不可重复（会替换之前元素）                                   |
| 顺序       | 有序                                                  | 无序（由HashCode决定）                                  |                                                              |
| 线程安全   | Vector线程安全                                        |                                                         | HashTable线程安全                                            |



## 17. HashMap和HashTable有什么区别？

* HashMap去掉了HashTable的contains方法，但加上了containsValue()和containsKey()方法。
* HashTable是同步的，而HashMap是非同步的，效率上比HashTable高。
* HashMap允许空键值，而HashTable不允许。



## 18. 如何决定使用HashMap还是TreeMap?

如果需要存储有序元素最好使用TreeMap，否则使用HashMap更好。



## 19. HashMap的实现原理

HashMap概述：HashMap是基于哈希表的Map接口的非同步实现。此实现提供所有可选的映射操作，并允许使用null值和null键。此类不保证映射的顺序，特别是它不保证该顺序恒久不变。

HashMap的数据结构：在java编程语言中，最基本的结构就是两种，一种是数组，另一个是模拟指针（引用），所有的数据结构都可以用这两个基本结构来构造。HashMap实际上是一个“链表散列”的数据结构，即数组和链表的结合体。

当我们往HashMap中put元素时，首先根据key的hashcode重新计算hash值，根据hash值得到这个元素在数组中的位置（下标），如果该数组在该位置上已经存放了其他元素，那么这个位置上的元素将以链表的形式存放，新加入的放在链头。如果数组中该位置没有元素，就直接将该元素放到数组的该位置上。

jdk1.8中对HashMap的实现做了优化，当链表中的节点数据超过八个之后，该链表会转为红黑树来提高查询效率，从原来的O(n)到O(logn)。



## 20. HashSet的实现原理

* HashSet底层由HashMap实现
* HashSet的值存放于HashMap的key上
* HashMap的value统一为PRESENT



## 21. ArrayList和LinkedList的区别是什么？

最明显的区别是ArrayList底层的数据结构是数组，支持随机访问，而LinkedList的底层数据结构是双向循环链表，不支持随机访问。



## 22. 如何实现数组和List之间的转换？

* List转换为数组：调用ArrayList的toArray方法
* 数组转换为List： 调用Arrays的asList方法



## 23. ArrayList和Vector的区别是什么？

* Vector是同步的，而ArrayList不是。如果寻求在迭代的时候对列表进行改变，应该使用CopyOnWriteArrayList。
* ArrayList比Vector快，它因为有同步，不会过载。
* ArrayList更加通用，因为我们可以使用Collections工具类轻易地获取同步列表和只读列表。



## 24. Array和ArrayList有何区别？

* Array可以容纳基本类型和对象，而ArrayList只能容纳对象。
* Array是指定大小的，而ArrayList大小是固定的。
* Array没有提供ArrayList那么多功能，比如addAll、removeAll、iterator等。



## 25. 在Queue中poll()和remove()有什么区别？

poll()和remove()都是从队列中取出一个元素，但是poll()在获取元素失败的时候会返回空，但是remove()失败的时候会抛出异常。



## 26. 哪些集合类是线程安全的？

* Vector：比ArrayList多了个同步化机制，因为效率较低，现在已经不建议使用。
* Stack：堆栈类，先进后出。
* HashTable：比HashMap多了个线程安全。
* enumeration：枚举，相当于迭代器。



## 27. 迭代器Iterator是什么？

迭代器是一种设计模式，它是一个对象，可以遍历并选择序列中的对象，而开发人员不需要了解该序列的底层结构。迭代器通常被称为“轻量级”对象，因为创建它的代价小。



## 28. Iterator和ListIterator有什么区别？

* Iterator可用来遍历Set和List集合，但是ListIterator只能用来遍历List。
* Iterator对集合只能是单向遍历，ListIterator可以双向遍历。
* ListIterator包含一些其他的功能，比如增加元素、替换元素、获取前后元素的索引。



# 多线程

## 29. 并行和并发有什么区别？

* 并行是指两个或多个事件在同一时刻发生；而并发是指两个或多个事件在同一时间间隔发生。
* 并行是在不同实体上的多个事件，并发是同一实体上的多个事件。

所以并发编程的目标是充分地利用处理器的每一个核，以达到最高的处理性能。



## 30. 线程和进程的区别

进程是程序运行和资源分配的基本单位，一个程序至少有一个进程，一个进程至少有一个线程。进程在执行过程中拥有独立的内存单元，而多个线程共享内存资源，减少切换次数，从而效率更高。线程是进程的一个实体，是cpu调度和分派的基本单位，是比程序更小的能独立运行的基本单位。同一进程中的多个线程之间可以并发执行。



## 31. 守护线程是什么？

守护线程（即daemon thread）是个服务线程，准确地来说就是服务其他的线程。



## 32. 创建线程有哪几种方式？

* 继承Thread类创建线程类
  * 定义Thread类的子类，并重写该类的run方法，该run方法的方法体就代表了线程要完成的任务。因此把run()方法称为执行体。
  * 创建Thread子类的实例，即创建了线程对象。
  * 调用线程对象的start()方法来启动该线程。

* 通过Runnable接口创建线程类
  * 定义runnable接口的实现类，并重写该接口的run()方法，该run()方法的方法体同样是该线程的线程执行体。
  * 创建Runnable实现类的实例，并依次实例作为Thread的target来创建Thread对象，该Thread对象才是真正的线程对象。
  * 调用线程对象的start()方法来启动该线程。
* 通过Callable和Future创建线程
  * 创建Callable接口的实现类，并实现call()方法，该call()方法将作为线程执行体，并且有返回值。
  * 创建Callable实现类的实例，使用FutureTask类来包装Callable对象，该FutureTask对象封装了该Callable对象的call()方法的返回值。
  * 使用FutureTask对象作为Thread对象的target创建并启动新线程。
  * 调用FutureTask对象的get()方法来获得子线程执行结束后的返回值。



## 33. Runnable和Callable有什么区别？

* Runnable接口中的run()方法的返回值是void，它做的事情只是纯粹地去执行run()方法中的代码而已。
* Callable接口中的call()方法是有返回值的，是一个泛型，如Future、FutureTask配合可以用来获取异步执行的结果。



## 34. 线程有哪些状态？

线程通常都有五种状态：创建、就绪、运行、阻塞和死亡。

* 创建状态。在生成线程对象，并没有调用该对象的start方法时线程处于创建状态。
* 就绪状态。当调用了线程对象的start方法之后，该线程就进入了就绪状态，但是此时线程调度程序还没有把该线程设置为当前线程，此时处于就绪状态。在线程运行之后，从等待或睡眠中回来之后，也会处于就绪状态。
* 运行状态。线程调度程序将处于就绪状态的线程设置为当前线程，此时线程就进入了运行状态，开始运行run函数当中的代码。
* 阻塞状态。线程正在运行的时候被暂停，通常是为了等待某个事件的发生（比如说 某项资源就绪）之后再继续运行。sleep、suspend、wait等方法都可以导致线程阻塞。
* 死亡状态。如果一个线程的run方法执行结束或者调用stop方法后，该线程就会死亡。对于已经死亡的线程，无法再使用start方法令其进入就绪状态。



## 35. sleep()和wait()有什么区别？

* sleep()：该方法是线程类(Thread)的静态方法，让调用线程进入睡眠状态，让出执行机会给其他线程，等到休眠时间结束后，线程进入就绪状态和其他线程一起竞争cpu的执行时间。因为sleep()是static静态的方法，他不能改变对象的机锁，当一个synchronized块中调用了sleep()方法，线程虽然进入休眠，凡是对象的机锁没有被释放，其他线程依然无法访问这个对象。
* wait()：该方法是Object类的方法，当一个线程执行到wait方法时，它就进入到一个和该对象相关的等待池，同时释放对象的机锁，使得其它线程能够访问，可以通过notify、notifyAll方法来唤醒等待的线程。



## 36. notify()和notifyAll()有什么区别？

* 如果线程调用了对象的wait()方法，那么线程便会处于该对象的等待池中，等待池中的线程不会去竞争该对象的锁。
* notify()只随机唤醒一个wait线程；notifyAll()唤醒所有wait线程。
* 当有线程调用了对象的notifyAll()方法或notify()方法，被唤醒的线程便会进入该对象的锁池中，锁池中的线程会去竞争该对象锁。也就是说，调用了notify后只有一个线程会由等待池进入锁池，而notifyAll会将该对象等待池内的所有线程移动到锁池中，等待锁竞争。
* 优先级高的线程竞争到对象锁的概率大，假如某线程没有竞争到该对象锁，它还会留在锁池中，唯有线程再次调用wait()方法，它才会重新回到等待池中。而竞争到对象锁的线程则继续往下执行，直到执行完了synchronized代码块，它会释放掉该对象锁，这时锁池中的线程会继续竞争该对象锁。



## 37. 线程的run()和start()有什么区别？

每个线程都是通过某个特定Thread对象所对应的方法run()来完成其操作的，方法run()称为线程体。通过调用Thread类的start()方法来启动一个线程。

start()方法来启动一个线程，真正实现了多线程运行。这时无需等待run方法体代码执行完毕，可以直接继续执行下面的代码，这时此线程处于就绪状态，并没有运行。然后通过此Thread类调用方法run()来完成其运行状态，这里方法run()称为线程体，它包含了要执行的这个线程的内容，run()方法运行结束，此线程终止，然后CPU再调度其他线程。

run()方法是在本线程里的，只是线程里的一个函数，而不是多线程的。如果直接调用run()，其实就相当于是调用了一个普通函数而已，直接调用run()方法必须等待run()方法执行完毕才能执行下面的代码，所以没有线程的特征。多线程执行时要使用start()方法而不是run()方法。



## 38. 创建线程池有哪几种方式？

* newFixedThreadPool(int nThreads)

  > 创建一个固定长度的线程池，每当提交一个任务就创建一个线程，直到达到线程池的最大数量，这时线程规模将不再变化，当线程发生未预期的错误而结束时，线程池会补充一个新的线程。

* newCachedThreadPool()

  > 创建一个可缓存的线程池，如果线程池的规模超过了处理需求，将自动回收空闲线程，而当需求增加时，则可以自动添加新线程，线程池的规模不存在任何限制。

* newSingleThreadExecutor()

  > 这是一个单线程的Executor，他创建单个工作线程来执行任务，如果这个线程异常结束，会创建一个新的来替代它。它的特点是能确保依照任务在队列中的顺序来串行执行。

* newScheduledThreadPool(int corePoolSize)

  > 创建了一个固定长度的线程池，而且以延迟或定时的方式来执行任务，类似以Timer。



## 39. 线程池中submit()和execute()方法有什么区别？

* 接收的参数不一样
* submit有返回值，而execute没有
* submit方便Exception处理



## 40. 在java程序中怎么保证多线程的运行安全？

* 原子性：提供互斥访问，同一时刻只能有一个线程对数据进行操作。(atomic, synchronized)
* 可见性：一个线程对主内存的修改可以及时地被其他线程看到。(synchronized, volatile)
* 有序性：一个线程观察其他线程中的指令执行顺序，由于指令重排序，该观察结果一般杂乱无序。(happens-before原则)



## 41. 多线程锁的升级原理是什么？

在Java中，锁共有4种状态，级别从低到高依次为：无状态锁、偏向锁、轻量级锁和重量级锁状态，这几个状态会随着竞争情况逐渐升级。锁可以升级但不能降级。



## 42. 什么是死锁？

死锁是指两个或两个以上的进程在执行过程中由于竞争资源或者由于彼此通信而造成的一种阻塞的现象，若无外力作用，它们都将无法推进下去。此时称系统处于死锁状态或系统产生了死锁，这些永远在互相等待的进程称为死锁进程。



## 43. 死锁的必要条件是什么？

死锁的四个必要条件：

* 互斥条件：进程对所分配到的资源不允许其他进程访问，若其他进程访问该资源，只能等待直至占有该资源的进程使用完成后释放该资源。
* 请求和保持条件：进程获得一定的资源之后，又对其他资源发出请求，但是该资源可能被其他进程占有，此时请求阻塞，但又对自己获得的资源保持不放。
* 不可剥夺条件：是指进程已获得的资源，在未完成使用之前，不可被剥夺。
* 环路等待条件：是指进程发生死锁后，若干进程之间形成一种头尾相接的循环等待资源关系。



## 44. ThreadLocal是什么？有哪些使用场景？

线程局部变量是局限于线程内部的变量，属于线程自身所有，不在多个线程间共享。Java提供ThreadLocal类来支持线程局部变量，是一种实现线程安全的方式。但是在管理环境下（如web服务器）使用线程局部变量的时候要特别小心，在这种情况下，工作线程的生命周期比任何应用变量的生命周期都要长。任何线程局部变量一旦在工作完成后没有释放，Java应用就存在内存泄漏的风险。



## 45. synchronized底层实现原理

synchronized可以保证方法或代码块在运行时，同一时刻只有一个方法可以进入到临界区，同时它还可以保证共享变量的内存可见性。



Java中每一个对象都可以作为锁，这是synchronized实现同步的基础：

* 普通同步方法，锁是当前实例对象
* 静态同步方法，锁是当前类的class对象
* 同步方法块，锁是括号里的对象



## 46. synchronized和volatile的区别是什么？

* volatile本质是在告诉jvm当前变量在寄存器（工作内存）中的值是不确定的，需要从主存中读取；synchronized则是锁定当前变量，只有当前线程可以访问该变量，其他线程则被阻塞住。
* volatile仅能使用在变量级别；synchronized则可以使用在变量、方法和类级别。
* volatile仅能实现变量的修改可见性，不能保证原子性；而synchronized则可以保证变量的修改可见性和原子性。
* volatile不会造成线程的阻塞；synchronized可能会造成线程的阻塞。
* volatile标记的变量不会被编译器优化；synchronized标记的变量可以被编译器优化。



## 47. synchronized和Lock有什么区别？

* synchronized是java内置关键字，在jvm层面，Lock是java类。
* synchronized无法判断是否获取锁的状态，Lock可以判断是否获取到锁。
* synchronized会自动释放锁，Lock需在finally中手工释放锁（unlock()方法释放锁），否则容易造成线程死锁。
* 用synchronized关键字的两个线程，如果当前线程1获得锁并阻塞，线程2会一直等待下去；而Lock锁如果尝试获取不到锁，线程可以不用一直等待就结束了。
* synchronized的锁可重入、不可中断、非公平；而Lock锁可重入、可判断、可公平。
* Lock锁适合大量的代码的同步问题，synchronized锁适合少量代码的同步问题。



## 48. synchronized 和 ReentrantLock区别是什么？

* synchronized是关键字而ReentrantLock是类
* ReetrantLock可以对获取锁的等待时间进行设置，这样就避免了死锁
* ReentrantLock可以获取各种锁的信息
* ReentrantLock可以灵活地实现多路通知
* ReentrantLock底层调用的是Unsafe的park方法加锁，synchronized操作的是对象头中的mark word



## 49. atomic的原理是什么？

Atomic包中的类基本的特性就是在多线程环境下，当有多个线程同时对单个（包括基本类型及引用类型）变量进行操作时，具有排他性，即当多个线程同时对该变量的值进行更新时，仅有一个线程能成功，而未成功的线程可以向自旋锁一样，继续尝试，直到执行成功。

Atomic系列的类中的核心方法都会调用unsafe类中的几个本地方法。sun.misc.Unsafe类中包含了大量的对C代码的操作，包括很多直接内存分配以及原子操作的调用，而它之所以标记为非安全的，是因为里面大量方法的调用都存在安全隐患，例如在通过unsafe分配内存的时候，如果自己指定某些区域可能会导致一些类似C++一样的指针越界到其他进程的问题。



# 反射

## 50. 什么是反射？

反射主要是指程序可以访问、检测和修改它本身状态或行为的一种能力。Java反射机制主要提供了以下功能：

* 在运行时判断任意一个对象所属的类
* 在运行时构造任意一个类的对象
* 在运行时判断任意一个类所具有的成员变量的方法
* 在运行时调用任意一个对象的方法



## 51. 什么是Java序列化?什么情况下需要序列化？

简单地说就是为了保存在内存中的各种对象的状态，并且可以把保存的对象状态再读出来。虽然可以用自己的各种各样的方法来保存object states，但Java提供一种更好的保存对象的机制，就是序列化。

什么情况下需要序列化？

* 当想把内存中的对象状态保存到一个文件中或者数据库中的时候
* 当想用套接字在网络上传送对象的时候
* 当想通过RMI传输对象的时候



## 52. 动态代理是什么？有哪些应用？

动态代理：

当想要给实现了某个接口的类中的方法加一些额外的处理，比如说加日志和事务等，可以给这个类创建一个代理，即创建一个新的类，这个类部件包含原来类方法的功能，而且在原来的基础上添加了额外处理的新类。这个代理类是动态生成的，具有解耦意义，灵活、扩展性强。

动态代理的应用：

* Spring的AOP
* 加事务
* 加权限
* 加日志



## 53. 怎么实现动态代理？

首先必须定义一个接口，还要有一个InvocationHandler（将实现接口的类的对象传递给它）处理类，再有一个工具类Proxy（习惯称为代理类，因为调用newInstance()可以产生代理对象，其实它只是一个产生代理对象的工具类）。利用InvocationHandler，拼接代理类源码，将其编译生成代理类的二进制码，利用加载器加载，并将其实例化产生代理对象，最后返回。



## 54. 如何实现对象克隆？

* 实现Cloneable接口并重写Object类中的clone()方法
* 实现Serializable接口，通过对象的序列化和反序列化实现克隆，可以实现真正的深度克隆。



## 55. 深克隆和浅克隆区别是什么？

* 浅克隆只是复制了对象的引用地址，两个对象指向同一个内存地址，所以修改其中任意的值，另一个值都会发生变化，这就是浅克隆。
* 深克隆是将对象及值复制过来，两个对象修改其中任意的值另一个值不会改变，例如JSON.parse()和JSON.stringify()。





# Java Web

## 56. jsp和Servlet有什么区别？

* jsp经编译后就变成了Servlet。jsp的本质就是Servlet，JVM只能识别java的类，不能识别jsp代码。Web容器将jsp的代码编译成JVM能够识别的java类。
* jsp更擅长表现于页面显示，Servlet更擅长于逻辑控制。
* Servlet中没有内置对象，jsp中的内置对象都必须通过HttpServletRequest对象、HttpServletResponse对象以及HttpServlet对象得到。
* jsp是Servlet的一种简化，使用jsp只需要完成程序员需要输出到客户端的内容，jsp中的java脚本如何镶嵌到一个类中，有jsp容器完成。而Servlet是个完整的Java类，这个类的Service方法用于生成对客户端的响应。



## 57. jsp有哪些内置对象？作用分别是什么？

jsp有9个内置对象：

* request：封装客户端的请求，其中包含来自GET或POST请求的参数。
* response：封装服务器对客户端的响应。
* pageContext：通过该对象可以获取其他对象。
* session：封装用户会话的对象。
* application：封装服务器运行环境的对象。
* out：输出服务器响应的输出流对象。
* config：Web应用的配置对象。
* page：jsp页面本身（相当于Java程序中的this）。
* exception：封装页面抛出异常的对象。



## 58. jsp的4种作用域

* page：代表与一个页面相关的对象和属性。
* request：代表与Web客户机发出的一个请求相关的对象和属性。一个请求可能跨越多个页面，涉及多个Web组件。需要在页面显示的临时数据可以置于此作用域。
* session：代表某个用户与服务器建立的一次会话相关的对象和属性。跟某个用户相关的数据应该放在用户自己的session中。
* application：代表与整个Web应用程序相关的对象和属性，它实质上是跨越整个Web应用程序，包括对个页面、请求和会话的一个全局作用域。



## 59. session和cookie有什么区别？

* 由于HTTP协议是无状态的协议，所以服务端需要记录用户的状态时，就需要用某种机制来识别具体的用户，这个机制就是session。session是保存在服务端的，有一个唯一标识。在服务端保存session的方法有内存、数据库、文件等。几群的时候也要考虑session的转移，在大型网站中，一般会有专门的session服务器集群，用来保存用户会话，这个时候session信息都是放在内存中的，使用一些缓存服务比如Memcached之类的来存放session。
* 每次HTTP请求的时候，客户端都会发送相应的cookie信息到服务端，实际上大多数的应用都是用cookie实现session跟踪的。第一次创建session的时候，服务端会在HTTP协议中告诉客户端，需要在cookie里记录一个session ID，以后每次请求把这个会话ID发送到服务器，一次来识别用户。若用户禁用cookie，一般会使用URL重写技术进行会话跟踪，即每次HTTP交互，URL后面都会被附加上诸如sid=xxxx之类的参数。
* cookie还可以用在一些方便用户的场景下，比如自动填充用户名密码。session是在服务端保存的一个数据结构，用来跟踪用户的状态，这个数据可以保存在集群、数据库、文件中；cookie是客户端保存用户信息的一种机制，用来记录用户的一些信息，也是实现session的一种方式。



## 60. session的工作原理

session是存在于服务器上的类似一个散列表格的文件，里面存有我们需要的信息，类似于一个大型的Map，里面的键存储的是用户的sessionid。用户向服务器发送请求的时候会带上这个sessionid，这时就可以从中取出对应的值了。



## 61. 客户端禁用cookie，session还能用吗？

session采用的是在服务端保持状态的方法，而cookie采用的是在客户端保持状态的方案。session是利用Session ID来确定当前对话所对应的服务器session，而Session ID是通过cookie来传递的，禁用cookie相当于失去了Session ID，也就得不到session了。

实现在用户关闭cookie的情况下使用session的途径有以下几种：

* 设置php.ini配置文件中的“session.use_trans_sid = 1”，或者编译时打开了"--enable-trans-sid"选项，让PHP自动跨页传递Session ID。
* 手动通过URL传值、隐藏表单传递Session ID。
* 用文件、数据库等形式保存Session ID，在跨页过程中手动调用。



## 62. spring mvc 和 struts的区别是什么？

* 拦截机制不同

  Struts2 是类级别的拦截，每次请求就会创建一个Action，和Spring整合时Struts2的ActionBean注入作用域是原型模式，然后通过setter、getter把request数据注入到属性。Struts2中，一个Action对应一个request、response上下文，在接收参数时，可以通过属性接收，这说明属性参数是让多个方法共享的。Struts2中Action的一个方法可以对应一个url，而其类属性却被所有方法共享，这也就无法用注解或其他方式标识其所属方法了，只能设计为多例。

  SpringMVC是方法级别的拦截，一个方法对应一个request上下文，所以方法之间基本上是独立的，独享request、response数据。而每个方法同时又和一个url对应，参数的传递是直接注入到方法中的，是方法所独有的。处理结果通过ModeMap返回给框架。在Spring整合时，SpringMVC的Controller Bean默认为单例模式，所以默认对所有的请求，只会创建一个Controller，因为没有共享的属性，所以是线程安全的。如果要改变默认的作用域，需要添加@Scope注解修改。

  Struts2有自己的拦截机制，SpringMVC用的是独立的Aop方式，导致Struts的配置文件量比SpringMVC大。

* 底层框架不同

  Struts2采用Filter(StrutsPrepareAndExecuteFilter)实现，SpringMVC则采用Servlet(DispatcherServlet)实现。Filter在容器启动之后即初始化，服务停止之后销毁，晚于Servlet。Servlet是在调用时初始化，先于Filter调用，服务停止后销毁。

* 性能方面

  Struts2是类级别的拦截，每次请求对应一个新的Action实例，需要加载所有的属性值注入。SpringMVC实现了零配置，由于SpringMVC基于方法的拦截，有加载一次单例模式bean注入。所以SpringMVC开发效率和性能高于Struts2。

* 配置方面

  SpringMVC 和 Spring是无缝的，从项目的管理和安全上也比Struts高。



## 63. 如何避免sql注入

* PreparedStatement
* 使用正则表达式过滤传入的参数
* 字符串过滤
* JSP中调用函数检查是否包含非法字符
* JSP页面判断代码



## 64. 什么是XSS攻击？如何避免？

XSS攻击又称CSS，全称Cross Site Script（跨站脚本攻击），其原理是攻击者向有XSS漏洞的网站中输入恶意的HTML代码，当用户浏览该网站时，这段HTML代码会自动执行，从而达到攻击的目的。XSS攻击类似于SQL注入攻击，SQL注入攻击中以SQL语句作为用户输入，从而达到查询/修改/删除数据的目的，而在XSS攻击中，通过插入恶意脚本，实现对用户浏览器的控制，获取用户的一些信息。XSS是Web程序中常见的漏洞，XSS属于被动式且用于客户端的攻击方式。

XSS防范的总体思路是：对输入（和URL参数）进行过滤，对输出进行编码。



## 65. 什么是CSRF攻击？如何避免？

CSRF（Cross-site request forgery）也被称为 one-click attack或者 session riding，中文全称是叫**跨站请求伪造**。一般来说，攻击者通过伪造用户的浏览器的请求，向访问一个用户自己曾经认证访问过的网站发送出去，使目标网站接收并误以为是用户的真实操作而去执行命令。常用于盗取账号、转账、发送虚假消息等。攻击者利用网站对请求的验证漏洞而实现这样的攻击行为，网站能够确认请求来源于用户的浏览器，却不能验证请求是否源于用户的真实意愿下的操作行为。

如何避免：

* 验证HTTP Referer字段

> HTTP头中的Referer字段记录了该 HTTP 请求的来源地址。在通常情况下，访问一个安全受限页面的请求来自于同一个网站，而如果黑客要对其实施 CSRF
> 攻击，他一般只能在他自己的网站构造请求。因此，可以通过验证Referer值来防御CSRF 攻击。

* 使用验证码

> 关键操作页面加上验证码，后台收到请求后通过判断验证码可以防御CSRF。但这种方法对用户不太友好。

* 在请求地址中添加token并验证

> CSRF 攻击之所以能够成功，是因为黑客可以完全伪造用户的请求，该请求中所有的用户验证信息都是存在于cookie中，因此黑客可以在不知道这些验证信息的情况下直接利用用户自己的cookie 来通过安全验证。要抵御 CSRF，关键在于在请求中放入黑客所不能伪造的信息，并且该信息不存在于 cookie 之中。可以在 HTTP 请求中以参数的形式加入一个随机产生的 token，并在服务器端建立一个拦截器来验证这个 token，如果请求中没有token或者 token 内容不正确，则认为可能是 CSRF 攻击而拒绝该请求。这种方法要比检查 Referer 要安全一些，token 可以在用户登陆后产生并放于session之中，然后在每次请求时把token 从 session 中拿出，与请求中的 token 进行比对，但这种方法的难点在于如何把 token 以参数的形式加入请求。
> 对于 GET 请求，token 将附在请求地址之后，这样 URL 就变成 http://url?csrftoken=tokenvalue。
> 而对于 POST 请求来说，要在 form 的最后加上 <input type="hidden" name="csrftoken" value="tokenvalue"/>，这样就把token以参数的形式加入请求了。

* 在HTTP 头中自定义属性并验证

> 这种方法也是使用 token 并进行验证，和上一种方法不同的是，这里并不是把 token 以参数的形式置于 HTTP 请求之中，而是把它放到 HTTP 头中自定义的属性里。通过 XMLHttpRequest 这个类，可以一次性给所有该类请求加上 csrftoken 这个 HTTP 头属性，并把 token 值放入其中。这样解决了上种方法在请求中加入 token 的不便，同时，通过 XMLHttpRequest 请求的地址不会被记录到浏览器的地址栏，也不用担心 token 会透过 Referer 泄露到其他网站中去。





# 异常

## 66. throw 和 throws 的区别

throws是用来声明一个方法可能抛出的所有异常信息，throws是将异常声明但是不处理，而是将异常往上传递给调用者，而throw则是指抛出一个具体的异常类型。



## 67. final、finally、finalize有什么区别？

* final可以修饰类、变量、方法，修饰类表示该类不能被继承、修饰方法表示该方法不能被重写、修饰变量表示该变量是一个常量不能被重新赋值。
* finally一般作用在try-catch代码块中，在处理异常的时候，通常将一定要执行的代码放在finally代码块中，表示不管是否出现异常，该代码块都会执行，一般用来存放一些关闭资源的代码。
* finalize是一个方法，属于Object类的一个方法，而Object类是所有类的父类，该方法一般由垃圾回收器来调用。当我们调用System的gc()方法的时候，由垃圾回收器调用finalize()回收垃圾。



## 68. 常见的异常类有哪些？

- NullPointerException：当应用程序试图访问空对象时，则抛出该异常。
- SQLException：提供关于数据库访问错误或其他错误信息的异常。
- IndexOutOfBoundsException：指示某排序索引（例如对数组、字符串或向量的排序）超出范围时抛出。 
- NumberFormatException：当应用程序试图将字符串转换成一种数值类型，但该字符串不能转换为适当格式时，抛出该异常。
- FileNotFoundException：当试图打开指定路径名表示的文件失败时，抛出此异常。
- IOException：当发生某种I/O异常时，抛出此异常。此类是失败或中断的I/O操作生成的异常的通用类。
- ClassCastException：当试图将对象强制转换为不是实例的子类时，抛出该异常。
- ArrayStoreException：试图将错误类型的对象存储到一个对象数组时抛出的异常。
- IllegalArgumentException：抛出的异常表明向方法传递了一个不合法或不正确的参数。
- ArithmeticException：当出现异常的运算条件时，抛出此异常。例如，一个整数“除以零”时，抛出此类的一个实例。 
- NegativeArraySizeException：如果应用程序试图创建大小为负的数组，则抛出该异常。
- NoSuchMethodException：无法找到某一特定方法时，抛出该异常。
- SecurityException：由安全管理器抛出的异常，指示存在安全侵犯。
- UnsupportedOperationException：当不支持请求的操作时，抛出该异常。
- RuntimeExceptionRuntimeException：是那些可能在Java虚拟机正常运行期间抛出的异常的超类。





# 网络

## 69. http响应码301和302代表的是什么？有什么区别？

301和302都是HTTP状态的编码，都代表着某个URL发生了转移。

区别是301代表永久性转移，302代表暂时性转移。



## 70. forward 和 redirect 的区别

forward和redirect代表了两种请求转发方式：直接转发和间接转发。

直接转发(Forward)：客户端和浏览器只发出一次请求，Servlet、HTML、jsp或其他信息资源，由第二个信息资源响应该请求，在请求对象request中，保存的对象对于每个信息资源是共享的。

间接转发(Redirect)：实际是两次HTTP请求，服务器端在响应第一次请求的时候，让浏览器再向另外一个URL发出请求，从而达到转发的目的。



## 71. 简述TCP和UDP的区别

* TCP面向连接（如打电话要先拨号建立连接）；UDP是无连接的，即发送数据之前不需要建立连接。
* TCP提供可靠的服务，也就是说通过TCP连接传送的数据，无差错、不丢失、不重复，且按序到达；UDP尽最大努力交付，即不保证可靠交付。
* TCP通过校验和、重传控制、序号标识、滑动窗口、确认应答实现可靠传输。如丢包时的重发控制，还可以对次序乱掉的分包进行顺序控制。
* UDP具有较好的实时性，工作效率比TCP高，适用于对高速传输和实时性有较高的通信或广播通信。
* 每一条TCP连接只能是点到点的；UDP支持一对一、一对多、多对一和多对多的交互通信。
* TCP对系统资源要求较多，UDP对系统资源要求较少。



## 72. TCP为什么要三次握手？

为了实现可靠数据传输，TCP协议得通信双方，都必须维护一个序列号，以标识发送出去的数据包中，哪些是已经被对方收到的。三次握手的过程即是通信双方互相告知序列号起始值，并确认对方已经收到了序列号起始值的必经步骤。

如果只是两次握手，至多只有连接发起方的其实序列号能被确认，另一方选择的序列号则得不到确认。



## 73. TCP粘包是怎么产生的？

* 发送方产生粘包

  采用TCP协议传输数据的客户端与服务器经常是保持一个长连接的状态（一次连接发一次数据不存在粘包），双方在连接不断开的情况下，可以一直传输数据；但当发送的数据包过小时，TCP协议会默认启用Nagle算法，将这些较小的数据包进行合并发送（缓冲区数据发送是一个堆压的过程）；这个合并过程就是在发送缓冲区中进行的，也就是说数据发送出来它已经是粘包的状态了。

* 接收方产生粘包

  接收方采用TCP协议接收数据时的过程是这样的：数据到底接收方，从网络模型的下方传递至传输层，传输层的TCP协议处理是将其放置接收缓冲区，然后由应用层来主动获取（C语言用recv、read等函数）；这时会出现一个问题，就是我们在程序中调用的读取数据函数不能及时的把缓冲区中的数据拿出来，而下一个数据又到来并有一部分放入的缓冲区末尾，等我们读取数据时就是一个粘包。（放数据的速度 > 应用层拿数据速度） 



## 74. OSI的七层模型都有哪些？

* 应用层：网络服务与最终用户的一个接口
* 表示层：数据的表示、安全、压缩
* 会话层：建立、管理、终止会话
* 传输层：定义传输数据的协议端口号，以及流控和差错校验
* 网络层：进行逻辑地址寻址，实现不同网络之间的路径选择
* 数据链路层：建立逻辑连接、进行硬件地址寻址、差错校验等功能
* 物理层：建立、维护、断开物理连接



## 75. get 和 post请求有哪些区别？

* GET在浏览器回退时是无害的，而POST会再次提交请求。
* GET请求会被浏览器主动缓存，而POST不会，除非手动设置。
* GET请求只能进行url编码，而POST支持多种编码方式。
* GET请求参数会被完整保留在浏览器历史记录里，而POST中的参数不会被保留。
* GET请求在URL中传送的参数是有长度限制的，而POST没有。
* 对参数的数据类型，GET只接受ASCII字符，而POST没有限制。
* GET比POST更不安全，因为参数直接暴露在URL上，所以不能用来传递敏感信息。
* GET参数通过URL传递，POST放在Request body中。



## 76. 如何实现跨域？

* 图片ping或script标签跨域
* JSONP跨域
* CORS
* window.name + iframe
* window.postMessage()
* 修改document.domain跨子域
* WebSocket
* 代理



## 77. JSONP的实现原理

jsonp即json + padding，动态创建script标签，利用script标签的src属性何以获取任何域下的js脚本，通过这个特性，服务器端不再返回json格式，而是返回一段调用某个函数的js代码，在src中进行了调用，这样就实现了跨域。