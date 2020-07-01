package stream.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import static java.util.stream.Collector.Characteristics.*;

/**
 * Collector<T, A, R>
 * T:流中要收集的项目的范围
 * A:累加器的类型，累加器是在收集过程中累计部分结果的对象
 * R:收集操作得到的对象的类型
 * @author Administrator
 *
 * @param <T>
 */
public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {
	/**
	 * 建立新的结果容器，供数据收集使用
	 */
	@Override
	public Supplier<List<T>> supplier() {
		return ArrayList::new;
	}
	
	/**
	 * 将元素添加到结果容器
	 */
	@Override
	public BiConsumer<List<T>, T> accumulator() {
		return List::add;
	}
	
	/**
	 * 对结果容器应用最终转换
	 */
	@Override
	public Function<List<T>, List<T>> finisher() {
		return Function.identity();
	}

	/**
	 * 对流的各个子部分进行并行处理时，各个子部分归约得到的累加器要如何合并
	 */
	@Override
	public BinaryOperator<List<T>> combiner() {
		return (list1, list2) -> {
			list1.addAll(list2);
			return list1;
		};
	}

	/**
	 * 定义了收集器的行为
	 * UNORDERED:规约结果不受流中项目的遍历和累计顺序的影响
	 * CONCURRENT:accumulator函数可以从多个线程同时调用，且该收集齐可以并行归约流。
	 * 			     如果收集器没有标为UNORDERED，那它仅在用于无序数据源时才可以并行规约。
	 * IDENTITY_FINISH:表明完成器方法返回的函数是恒等函数，可以跳过。
	 */
	@Override
	public Set<Characteristics> characteristics() {
		return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, CONCURRENT));
	}


}
