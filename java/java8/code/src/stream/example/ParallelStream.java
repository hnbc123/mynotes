package stream.example;

import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStream {
	public static void main(String[] args) {
//		System.out.println("Sequential sum done in:" + measureSumPerf(ParallelStream::sequentialSum, 10_000_000) + " msecs");
//		System.out.println("iterative sum done in:" + measureSumPerf(ParallelStream::iterativeSum, 10_000_000) + " msecs");
//		System.out.println("parallelSum sum done in:" + measureSumPerf(ParallelStream::parallelSum, 10_000_000) + " msecs");
		System.out.println("sideEffect sum done in:" + measureSumPerf(ParallelStream::sideEffectSum, 10_000_000) + " msecs");
		System.out.println("sideEffect parallel sum done in:" + measureSumPerf(ParallelStream::sideEffectParallelSum, 10_000_000) + " msecs");
	}
	
	public static long parallelSum(long n) {
		return LongStream.rangeClosed(1, n)
					 	 .parallel()
					 	 .reduce(0L, Long::sum);
	}
	
	public static long sequentialSum(long n) {
		return LongStream.rangeClosed(1, n)
			 	 		 .reduce(0L, Long::sum);
	}
	
	public static long iterativeSum(long n) {
		long result = 0L;
		for (long i = 1L; i <= n; i++) {
			result += i;
		}
		return result;
	}
	
	public static long measureSumPerf(Function<Long, Long> adder, long n) {
		long fastest = Long.MAX_VALUE;
		for (int i = 0; i < 10; i++) {
			long start = System.nanoTime();
			long sum = adder.apply(n);
			long duration = (System.nanoTime() - start) / 1_000_000;
			System.out.println("Result: " + sum);
			if (duration < fastest) {
				fastest = duration;
			}
		}
		return fastest;
	}
	
	public static long sideEffectSum(long n) {
		Accumulator accumulator = new Accumulator();
		LongStream.rangeClosed(1, n).forEach(accumulator::add);
		return accumulator.total;
	}
	
	public static long sideEffectParallelSum(long n) {
		Accumulator accumulator = new Accumulator();
		LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
		return accumulator.total;
	}
}

class Accumulator {
	public long total = 0;
	
	public void add(long value) {
		total += value;
	}
}
