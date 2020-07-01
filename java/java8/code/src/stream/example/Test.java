package stream.example;

import java.util.Arrays;
import java.util.List;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class Test {
	public static void main(String[] args) {
		Trader raoul = new Trader("Raoul", "Cambridge");
		Trader mario = new Trader("Mario", "Milan");
		Trader alan = new Trader("Alan", "Cambridge");
		Trader brian = new Trader("Brian", "Cambridge");
		
		List<Trader> traders = Arrays.asList(raoul, mario, alan, brian);
		
		List<Transaction> transactions = Arrays.asList(
			new Transaction(brian, 2011, 300),
			new Transaction(raoul, 2012, 1000),
			new Transaction(raoul, 2011, 400),
			new Transaction(mario, 2012, 710),
			new Transaction(mario, 2012, 700),
			new Transaction(alan, 2012, 950)
		); 

		// 1.2011年发生的所有交易，并按交易额从低到高排序
		List<Transaction> result = 
			transactions.stream()
						.filter(t -> t.getYear() == 2011)
						.sorted(comparing(Transaction::getValue))
						.collect(toList());
		System.out.println(result);

		// 2.交易员都在哪些不同城市工作过
		List<String> citys = 
			traders.stream()
				   .map(Trader::getCity)
				   .distinct()
				   .collect(toList());
		System.out.println(citys);
		
		// 3.查找所有来自剑桥的交易员，并按姓名排序
		List<String> traderFromCambridge =
			traders.stream()
				   .filter(t -> "Cambridge".equals(t.getCity()))
				   .map(Trader::getName)
				   .sorted()
				   .collect(toList());
		System.out.println(traderFromCambridge);
		
		// 4.返回所有交易员的姓名，并按字母顺序排序
		String traderName =
			traders.stream()
				   .map(Trader::getName)
				   .sorted()
				   .reduce("", (a, b) -> a + b + " ");
		System.out.println(traderName);
		
		// 5.是否有交易员在米兰工作
		if (traders.stream()
			   .anyMatch(t -> "Milan".equals(t.getCity()))
		   ) {
			System.out.println("有交易员在米兰工作");
		} else {
			System.out.println("没有交易员在米兰工作");
		}
		
		// 6.剑桥交易员的所有交易额
		int sum = transactions.stream()
				  	.filter(t -> "Cambridge".equals(t.getTrader().getCity()))
				  	.map(Transaction::getValue)
				  	.reduce(0, Integer::sum);
		System.out.println(sum);
	
		// 7.所有交易额中最高的交易额
		transactions.stream()
			.map(Transaction::getValue)
			.reduce(Integer::max)
			.ifPresent(maxValue -> System.out.println(maxValue));
		
		// 8.交易额最小的交易
		transactions.stream()
			.sorted(comparing(Transaction::getValue))
			.findFirst()
			.ifPresent(minTransaction -> System.out.println(minTransaction));
		
		transactions.stream()
			.min(comparing(Transaction::getValue))
			.ifPresent(minTransaction -> System.out.println(minTransaction));
	}
}
