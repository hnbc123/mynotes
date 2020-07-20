# 表达式之谜

## 谜题1： 奇数性

下面方法的目的是确定其唯一个参数是否为奇数，这个方法可行吗？

```  java
public static boolean isOdd(int i) {
    return i % 2 == 1;
}
```

## 解惑1： 奇数性

取余操作符(%)被定义为对于所有的int数值a和所有的非零int数值b，都满足下面的恒等式：

``` java
(a / b) * b + (a % b) == a
```

它意味着：**当取余操作返回一个非零的结果时，它与左操作数具有相同的正负符号。**

改正如下：

```java
public static boolean isOdd(int i) {
    return i % 2 != 0;
}
```

也可以使用位操作符AND(&)替代取余操作符：

```java
public static boolean isOdd(int i) {
    return (i & 1) != 0;
}
```





## 谜题2： 找零时刻

下面的程序会打印什么？

```java
public class Change {
    public static void main(String[] args) {
        System.out.println(2.00 - 1.10);
    }
}
```

## 解惑2： 找零时刻

运行结果为：0.8999999999999999。

**不是所有的小数都可以用二进制浮点数精确表示**，解决该问题的一种方式是使用BigDecimal，**一定要用BigDecimal(String) 构造器，不能使用BigDecimal(double) 构造器。**

改正代码如下：

```java
import java.math.BigDecimal;
public class Change {
    public static void main(String[] args) {
        System.out.println(new BigDecimal("2.00").
                          substract(new BigDecimal("1.10")));
    }
}
```





## 谜题3： 长整除

被除数表示一天里的微妙数，除数表示一天里的毫秒数。这个程序会打印什么？

```java
public class LongDivision {
    public static void main(String[] args) {
        final long MICROS_PER_DAY = 24 * 60 * 60 * 1000 * 1000;
        final long MILLTS_PER_DAY = 24 * 60 * 60 * 1000;
        System.out.println(MICROS_PER_DAY / MILLIS_PER_DAY);
    }
}
```

## 解惑3： 长整除

打印的是5。

问题在于常数MICROS _PER_DAY的计算溢出了。虽然计算的结果可以放进long中，但这个结果不适合放进int中，这个计算完全是以int运算来执行的，并且只有在运算完成之后，结果才被提升为long，而此时结果已经溢出，返回的是小了200倍的数值。

改正如下：

```java
public class LongDivision {
    public static void main(String[] args) {
        final long MICROS_PER_DAY = 24L * 60 * 60 * 1000 * 1000;
        final long MILLIS_PER_DAY = 24L * 60 * 60 * 1000;
        System.out.println(MICROS_PER_DAY / MILLIS_PER_DAY);
    }
}
```





## 谜题4： Dos Equis

下面的程序会打印什么？

```java
public class DosEquis {
    public static void main(String[] args) {
        char x = 'X';
        int i = 0;
        System.out.println(true ? x : 0);
        System.out.println(false ? i : x);
    }
}
```

## 解惑4： Dos Equis

打印的是X88。第一个语句打印的是X，第二个是88。

三目表达式结果类型规则的核心为以下三点：

* 如果第二个和第三个操作数具有相同的类型，那么它就是条件表达式的类型。
* 如果一个操作数的类型是T，T表示byte、short或char，而另一个操作数是一个int类型的常量表达式，它的值可以用类型T表示，那么条件表达式的类型就是T。
* 否则，将对操作数类型进行二进制数字提升，而条件表达式的类型就是第二个和第三个操作数被提升之后的类型。

第一个表达式中的int操作数是常量（0），而第二个表达式中的int操作数是变量（i），因此第二点被应用到第一个表达式上，返回的类型为char，调用的是PrintStream.print(char)；第三点被应用到第二个表达式上，返回的类型是对int和char进行二进制数字提升之后的类型，即int，调用的是PrintStream.print(int)。前一个将变量x的值作为Unicode字符(X)打印，而后一个方法将其作为一个十进制整数(88)打印。





##  谜题5： 半斤

给出一个对变量x和i的声明，使得下面这条语句合法：x += i;

但是下面这条不合法： x = x + i;

## 解惑5： 半斤

例： short x = 0; int i = 123456;

**复合赋值表达式自动地将所执行计算的结果转型为其左侧变量的类型。请不要将复合赋值操作符作用于byte、short或char类型的变量。**





# 字符之谜

## 谜题6： 字符串奶酪

下面的程序从一个字节序列创建一个字符串，然后迭代遍历字符串中的字符，并将它们作为数字打印。请描述程序打印的数字序列：

```java
public class StringCheese {
    public static void main(String[] args) {
        byte bytes = new byte[256];
        for (int i = 0; i < 256; i++) {
            bytes[i] = (byte)i;
        }
        String str = new String(bytes);
        for (int i = 0, n = str.length(); i < n; i++) {
            System.out.print((int)str.charAt(i) + " ");
        }
    }
}
```

## 解惑6： 字符串奶酪

打印的序列是不确定的。

String(byte[])构造器在通过解码使用平台缺省字符集的指定byte数组来构造一个新的String时，该新String的长度是字符集的一个函数，因此，它可能不等于byte数组的长度。当给定的所有字节在缺省字符集中并非全部有效时，这个构造器的行为是不确定的。

**在char序列和byte序列之间转换时，应该显式地指定字符集。**

```java
String str = new String(bytes, "ISO-8859-1");
```





## 谜题7： 不劳无获

下面的程序将打印一个单词，其首字母由一个随机数生成器选择，请描述改程序的行为：

```java
import java.util.Random;

public class Rhymes {
    private static Random rnd = new Random();
    
    public static void main(String[] args) {
        StringBuffer word = null;
        switch(rnd.nextInt(2)) {
            case 1: word = new StringBuffer('P');
            case 2: word = new StringBuffer('G');
            default: word = new StringBuffer('M');
        }
        word.append('a');
        word.append('i');
        word.append('n');
        System.out.println(word);
    }
}
```

## 解惑7： 不劳无获

它总是打印ain。

首先，nextInt的参数应该是3而不是2；其次，case中没有break语句；最后，StringBuffer有一个无参构造器和一个接受一个String作为字符串缓冲区初始内容的构造器，以及一个接受一个int作为缓冲区初始容量的构造器。在本例中，编译器会选择接受int的构造器，通过拓宽原生类型转换把字符数值‘M'转换为int数值77。换句话说，new StringBuffer('M')返回的是一个具有初始容量77的空字符串缓冲区。

正确版本如下：

```java
import java.util.Random;

public class Rhymes {
    private static Random rnd = new Random();
    
    public static void main(String[] args) {
        StringBuffer word = null;
        switch(rnd.nextInt(3)) {
            case 1:
                word = new StringBuffer("P");
                break;
            case 2:
                word = new StringBuffer("G");
                break;
            default:
                word = new StringBuffer("M");
                break;
        }
        word.append('a');
        word.append('i');
        word.append('n');
        System.out.println(word);
    }
}
```





# 循环之谜

## 谜题8： 在循环中

下面的程序计算了一个循环的迭代次数，并且在循环终止时打印这个计数值。那么他打印的是什么？

```java
public class InTheLoop {
    public static final int END = Integer.MAX_VALUE;
    public static final int START = END - 100;
    
    public static void main(String[] args) {
        int count = 0;
        for (int i = START; i <= END; i++) {
            count++;
        }
        System.out.println(count);
    }
}
```

## 解惑8： 在循环中

什么都不会打印，这是一个无限循环。

当i达到Integer.MAX_VALUE，并且再次执行增量操作时，它就又绕回到了Integer.MIN_VALUE。

可以用long变量作为循环索引来解决该问题。

**无论在何时使用了一个整数类型，都要意识到其边界条件。**





## 谜题9： 循环者

写一个变量声明，把它放在下面的循环之前，使之变为无限循环。

```java
while (i == i + 1) {}
```

## 解惑9： 循环者

任何足够大的浮点数都可以实现这个目的。例如：double i = 1.0e40;

一个浮点数值越大，它和其后继数值之间的间隔就越大。浮点数的这种分布是用固定数量的有效位来表示它们的必然结果。对一个足够大的浮点数加1不会改变它的值，因为1不足以填补它与其后继者之间的空隙。

**将一个很小的浮点数加到一个很大的浮点数上时，将不会改变大浮点数的值，二进制浮点算数只是对实际算数的一种近似。**





## 谜题10： 循环者的新娘

请提供一个对i的声明，将下面的循环转变为无限循环：

```java
while (i != i) {}
```

## 解惑10: 循环者的新娘

**NaN不等于任何浮点数值，包括它自身在内。**

可以用任何结果为NaN的浮点算数表达式来初始化i，例如double i = 0.0 / 0.0; 或 double i = Double.NaN;

**任何浮点操作，只要它的一个或多个操作数为NaN，那么其结果为NaN。





## 谜题11： 循环者的爱子

提供一个对i的声明，将下面的循环转变为一个无限循环：

```java
while (i != i + 0) {}
```

## 解惑11： 循环者的爱子

i是String类型即可。例如 String i = "hello";

+操作符被重载了：对于String类型，它执行的不是加法而是字符串连接。





## 谜题12： 循环者的诅咒

提供对i和j的声明，将下面的循环转变为无限循环：

```java
while (i <= j && j <= i && i != j) {}
```

## 解惑12： 循环者的诅咒

```java
Integer i = new Integer(0);
Integer j = new Integer(0);
```

前两个子表达式(i <= j 和 j <= i)在i和j上执行解包转换，并且在数字上比较所产生的int数值。第三个子表达式(i != j)在对象引用i和j上执行标识比较，因为它们都初始化为新的Integer实例，两变量引用不同的对象，因此第三个子表达式同样被计算为true。

**当两个操作数都是被包装的数字类型时，数值比较操作符和判等操作符的行为存在根本的差异：数值比较操作符执行的是值比较，而判等操作符执行的是引用标志的比较。**





## 谜题13： 循环者遇到了狼人

请提供一个对i的声明，将下面的循环转变为无限循环。

```java
while (i != 0 && i == -i) {}
```

## 解惑13： 循环者遇到了狼人

```java
int i = Integer.MIN_VALUE;
// long i = Long.MIN_VALUE;
```

对于每一种有符号的整数类型(int、long、byte和short)，负的数值总是比正的数值多一个，这个多出来的值总是这种类型所能表示的最小数值。对于Integer.MIN_VALUE取负值不会改变它的值。





## 谜题14： 被计数击倒了

下面这个程序会打印什么？

```java
public class Count {
    public static void main(String[] args) {
        final int START = 2000000000;
        int count = 0;
        for (float f = START; f < START + 50; f++) {
            count++;
        }
        System.out.println(count);
    }
}
```

## 解惑14： 被计数击倒了

打印结果是0。

f的初始值太大了，以至于加上50，然后转型为float时，所产生的数值等于直接将f转换成float的数值。换句话说，(float) 2000000000 == (float) 2000000050，因此循环体没有机会运行。

**不要使用浮点数作为循环索引，**因为它会导致无法预测的行为。如果在循环体内需要一个浮点数，那么使用int或long循环索引，并将其转换为float或double。**在将一个int或long转换成一个float或double时，可能会丢失精度。当使用浮点数时，要使用double而不是float。**





# 异常之谜

## 谜题15： 优柔寡断

下面的程序打印的是什么？它是合法的吗？

```java
public class Indecisive {
    public static void main(String[] args) {
        System.out.println(decision());
    }
    
    static boolean decision() {
        try {
            return true;
        } finally {
            return false;
        }
    }
}
```

## 解惑15： 优柔寡断

它打印的是false。

**在一个try-finally语句中，finally语句块总是在控制权离开try语句块时执行。**当try语句块和finally语句块都意外结束时，在try语句块中引发意外结束的原因将被丢弃，而整个try-finally语句意外结束的原因将与finally语句块意外结束的原因相同。

**千万不要用return、break、continue或throw来退出finally语句块，并且千万不要允许让受检查的异常传播到finally语句块之外。**





## 谜题16： 极端不可思议

下面三个程序每一个都会打印些什么？

```java
import java.io.IOException;
public class Arcanel1 {
    public static void main(String[] args) {
        try {
            System.out.println("Hello world");
        } catch (IOException e) {
            System.out.println("I've never seen println fail!");
        }
    }
}

public class Arcane2 {
    public static void main(String[] args) {
        try {
            // If you have nothing nice to say, say nothing
        } catch (Exception e) {
            System.out.println("This can't happen");
        }
    }
}

interface Type1 {
    void f() throws CloneNotSupportedException;
}

interface Type2 {
    void f() throws InterruptedException;
}

interface Type3 extends Type1, Type2 {
    
}

public class Aracne3 implements Type3 {
    public void f() {
        System.out.println("Hello world");
    }
    
    public static void main(String[] args) {
        Type3 t3 = new Arcane3();
        t3.f();
    }
}
```

## 解惑16： 极端不可思议

第一个程序不能编译，因为println方法没有声明会抛出任何受检测异常，而IOException却是一个受检测异常。**如果一个catch子句要捕获一个类型为E的受检查异常，而其相对应的try子句不能抛出E的某种子类型的异常，那么这就是一个编译器错误。**

第二个程序可以编译，**捕获Exception或Throwable的catch子句是合法的，不管与其对应的try子句的内容为何。**

第三个程序可以编译，**一个方法可以抛出的受检查异常集合是它所适用的所有类型声明要抛出的受检查异常集合的交集。**因此静态类型为Type3的对象上f方法根本就不能抛出任何受检查异常。





## 谜题17： 不受欢迎的宾客

本谜底中的程序所建模的系统，将尝试着从其环境中读取一个用户ID，如果这种尝试失败了，则缺省地认为它是一个来宾用户。

```java
public class UnwelcomeGuest {
    public static final long GUEST_USER_ID = -1;
    
    private static final long USER_ID;
    
    static {
        try {
            USER_ID = getUserIdFromEnvironment();
        } catch (IdUnavailableException e) {
            USER_ID = GUEST_USER_ID;
            System.out.println("Logging in as guest");
        }
    }
    
    private static long getUserIdFromEnvironment() 
        throws IdUnavailableException{
        throw new IdUnavailableException();
    }
    
    public static void main(String[] args) {
        System.out.println("User ID: " + USER_ID);
    }
}

class IdUnavailableException extends Exception {
    IdUnavailableException(){}
}
```

## 解惑17： 不受欢迎的宾客

该程序不能编译。

**在程序中，一个空final域只有在它的确未赋过值的地方才可以被赋值。编译器必须拒绝某些可以证明是安全的程序。**下面是解决方法。

```java
public class UnwelcomeGuest {
    public static final long GUEST_USER_ID = -1;
    private static final long USER_ID = getUserIdOrGuest();
    private static long getUserIdOrGuest() {
        try {
            return getUserIdFromEnvironment();
        } catch (IdUnavailableException e) {
            System.out.println("Logging in as guest");
            return GUEST_USER_ID;
        }
    }
    ...// The rest of the program is unchanged
}
```







## 谜题18： 您好，再见

下面的程序会打印什么？

```java
public class HelloGoodbye {
    public static void main(String[] args) {
        try {
            System.out.println("Hello world");
            System.exit(0);
        } finally {
            System.out.println("Goodbye world");
        }
    }
}
```

## 解惑18： 您好，再见

它只会打印Hello World。

不论try语句块的执行是正常地还是意外地结束，finally语句块确实都会执行。然而在这个程序中，try语句块根本就没有结束其执行过程。**System.exit方法将停止当前线程和所有其他当场死亡的线程。**finally子句的出现并不能给予线程继续执行的特殊权限。

当Sysetm.exit被调用时，虚拟机在关闭前要执行两项清理工作。首先它执行所有的关闭挂钩操作，这些挂钩已经注册到Runtime.addShutdownHook上。**务必为那些必须在VM退出之前发生的行为关闭挂钩。**

```java
public class HelloGoodbye {
    public static void main(String[] args) {
        System.out.println("Hello world");
        Runtime.getRuntime().addShutdownHook(
        	new Thread() {
                public void run() {
                    System.out.println("Goodbye world");
                }
            }
        );
        System.exit(0);
    }
}
```





## 谜题19： 不情愿的构造器

下面的程序将打印什么？

```java
public class Reluctant {
    private Reluctant internamInstance = new Reluctant();
    
    public Reluctant() throws Exception {
        throw new Exception("I'm not coming out");
    }
    
    public static void main(String[] args) {
        try {
            Reluctant b = new Reluctant();
            System.out.println("Surprise!");
        } catch (Exception ex) {
            System.out.println("I told you so");
        }
    }
}
```

## 解惑19： 不情愿的构造器

它抛出了StackOverflowError异常。

本程序包含了一个无限递归。当你调用一个构造器时，**实例变量的初始化操作将先于构造器的程序体而运行。**在本谜题中，internalInstance变量的初始化操作调用了构造器，而该构造器通过再次调用Reluctant构造器而初始化该变量自己的internalInstance域，如此递归下去。

**构造器必须声明其实例初始化操作会抛出的所有受检查异常。**





# 类之谜

## 谜题20： 令人混淆的构造器案例

main方法调用了一个构造器，但是它调用的是哪一个呢？

```java
public class Confusing {
    private Confusing(Object o) {
        System.out.println("Object");
    }
    
    private Confusing(double[] dArray) {
        System.out.println("double array");
    }
    
    public static void main(String[] args) {
        new Confusing(null);
    }
}
```

## 解惑20： 令人混淆的构造器案例

该程序打印的是double array。

Java的重载解析过程是分两阶段运行的。第一阶段选取所有可获得并且可应用的方法或构造器；第二阶段是在第一阶段选取的方法或构造器中选取最精确的一个。如果一个方法或构造器可以接受传递给另一个方法或构造器的任何参数，那么我们就说第一个方法比第二个方法缺乏精确性。

**要想强制要求编译器选择一个精确的重载版本，需要将实参转型为形参说声明的类型。**

理想状态下应避免使用重载。如果无法实现，可以通过将构造器设置为私有的并提供公有的静态工厂，以此来缓解这个问题。如果构造器有许多参数，可以用Builder模式来减少对重载版本的需求量。





## 谜题21： 我所得到的都是静态的

下面的程序将打印什么？

```java
class Dog {
    public static void bark() {
        System.out.print("woof ");
    }
}

class Basenji extends Dog {
    public static void bark() {}
}

public class Bark {
    public static void main(String[] args) {
        Dog woofer = new Dog();
        Dog nipper = new Basenji();
        woofer.bark();
        nipper.bark();
    }
}
```

## 解惑21： 我所得到的都是静态的

它打印的是woof  woof。

问题在于bark是一个静态方法，而**对静态方法的调用不存在任何动态的分派机制**。当一个程序调用了一个静态方法时，要被调用的方法都是在编译时刻被选定的，而这种选定是基于修饰符的编译器类型而做出的。在本例中woofer和nipper都是Dog类型，所以调用的是Dog.bark。

**千万不要用一个表达式来标志一个静态方法调用。**要用类名来修饰静态方法的调用，或者当你在静态方法所属的类中去调用它们时，根本不修饰这些方法，但不要用一个表达式去修饰它们。避免隐藏静态方法。





## 谜题22： 比生命更大

下面的程序会打印什么？

```java
public class Elvis {
    public static final Elvis INSTANCE = new Elvis();
    private final int beltSize;
    private static final int CURRENT_YEAR = 
        Calendar.getInstance().get(Calendar.YEAR);
    
    private Elvis() {
        beltSize = CURRENT_YEAR - 1930;
    }
    
    public int beltSize() {
        return beltSize;
    }
    
    public static void main() {
        System.out.println("Elvis wears a size " + 
                          INSTANCE.beltSize() + " belt.");
    }
}
```

## 解惑22： 比生命更大

该程序将打印Elvis wears a size -1930 belt.

Elvis类的初始化是有虚拟机对其main方法的调用而触发的。首先，其静态域被设置为缺省值，其中INSTANCE域被设置为null，CURRENT_YEAR被设置为0。接下来静态域除湿器按照其出现的顺序执行，第一个静态域是INSTANCE，它的值是通过调用Elvis()构造器而计算出来的。

这个构造器会用一个涉及静态域CURRENT_YEAR的表达式来初始化beltSize。通常，读取一个静态域是引起一个类被初始化的事件之一，但我们已经在初始化Elvis类了，递归的初始化尝试会被忽略。因此，CURRENT_YEAT的值仍旧是其缺省值0。

**在final类型的静态域被初始化之前，存在着读取其值的可能，**而此时该静态域包含的还只是其所述类型的缺省值。final类型的域只有在其初始化表达时是常量表达式时才是常量。

**要想改正一个类初始化循环，需要重新对静态域的初始器进行排序，使得每一个初始器都出现在任何依赖于它的初始器之前。**





##  谜题23： 不是你的类型

下面的程序每一个会做些什么？

```java
public class Type1 {
    public static void main(String[] args) {
        String s = null;
        System.out.println(s instanceof String);
    }
}

public class Type2 {
    public static void main(String[] args) {
        System.out.println(new Type2() instanceof String);
    }
}
```

## 解惑23： 不是你的类型

第一个程序展示了instanceof操作符应用于一个空对象引用时的行为。尽管null对于每一个引用类型来说都是其子类型，**但是instanceof操作符被定义为在其左操作数为null时返回false。**因此Type1将打印false。

第二个程序说明了instanceof操作符在测试一个类的实例，以查看它是否是某个不相关的类的实例时，所表现出来的行为。该程序在编译时刻就失败了，因为**instanceof操作符有这样的要求：如果两个操作数的类型都是类，其中一个必须是另一个的子类型。**





## 谜题24： 要点何在

下面的程序将打印什么？

```java
class Point {
    private final int x, y;
    private final String name;
    Point(int x, int y) {
        this.x = x;
        this.y = y;
        name = makeName();
    }
    
    protected String makeName() {
        return "[" + x + "," + y + "]";
    }
    
    public final String toString() {
        return name;
    }
}

public class ColorPoint extends Point {
    private final String color;
    ColorPoint(int x, int y, String color) {
        super(x, y);
        this.color = color;
    }
    
    protect String makeName() {
        return super.makeName() + ":" + color;
    }
    
    public static void main(String[] args) {
        System.out.println(new ColorPoint(4, 2, "purple"));
    }
}
```

## 解惑24： 要点何在

它打印的是[4, 2]:null。

```java
class Point {
    private final int x, y;
    private final String name;
    Point(int x, int y) {
        this.x = x;
        this.y = y;
        // 3.调用方法
        name = makeName();
    }
    
    protected String makeName() {
        return "[" + x + "," + y + "]";
    }
    
    public final String toString() {
        return name;
    }
}

public class ColorPoint extends Point {
    private final String color;
    ColorPoint(int x, int y, String color) {
        // 2.调用父类构造器
        super(x, y);
        // 5.初始化
        this.color = color;
    }
    
    protect String makeName() {
        // 4.在子类构造器结束前执行
        return super.makeName() + ":" + color;
    }
    
    public static void main(String[] args) {
        // 1.调用构造器
        System.out.println(new ColorPoint(4, 2, "purple"));
    }
}
```

**在一个final类型的实例域被赋值之前，存在着取用其值的可能，**而此时它包含的依旧是其所属类型的缺省值。**循环的实例初始化是可以且总是应该避免的，千万不要在构造器中调用可复写的方法。**





## 谜题25： 总和的玩笑

下面的程序在一个类中计算并缓存了一个总和，并且在另一个类中打印了这个总和，那么这个程序将打印什么？

```java
class Cache {
    static {
        initializeIfNecessary();
    }
    
    private static int sum;
    
    public static int getSum() {
        initializeIfNecessary();
        return sum;
    }
    
    private static boolean initialzed = false;
    
    private static synchronized void initializeIfNecessary() {
        if (!initialized) {
            for (int i = 0; i < 100; i++) {
                sum += i;
            }
            initialized = true;
        }
    }
}

public class Client {
    public static void main(String[] args) {
        System.out.println(Cache.getSum());
    }
}
```

## 解惑25： 总和的玩笑

它打印的是9900，是预期值的整整两倍。

Client.main方法调用了Cache.getsum方法，在getsum方法可以执行之前，VM必须初始化Cache类。类初始化是按照静态初始器在源代码中出现的顺序去执行这些初始器的。Cache类的静态语句块先出现，它调用了方法initializaIfNecessary，该方法将测试initialized域。因为该域还没有被赋予任何值，所以它具有缺省的布尔值false，sum具有缺省的int值0。该方法将4950添加到sum上，并将initialized设置为true。

在静态语句块执行之后，initialized域的静态初始器将其设置回false，从而完成Cache的类初始化。此后Client类的main方法调用Cache.getSum方法，因为initialized标志是false，所以会再次进入循环。

**要么使用积极初始化，要么使用惰性初始化，千万不要同时使用两者。**

```java
class Cache {
    private static final int sum = computeSum();
    
    private static int computeSum() {
        int result = 0;
        for (int i = 0; i < 100; i++) {
            result += i;
        }
        return result;
    }
    
    public static int getSum() {
        return sum;
    }
}
```

**请考虑类初始化的顺序，特别是当初始化显得很重要时更是如此。**

