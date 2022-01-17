package chapter5;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {

  public static void main(String[] args) {
    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");
    List<Transaction> transactions = Arrays.asList(
        new Transaction(brian, 2011, 300),
        new Transaction(raoul, 2012, 1000),
        new Transaction(raoul, 2011, 400),
        new Transaction(mario, 2012, 710),
        new Transaction(mario, 2012, 700),
        new Transaction(alan, 2012, 950)
    );

    //Finds all transactions in 2011 and sort by value (small to high)
    List<Transaction> tr2011 = transactions.stream()
        .filter(transaction -> transaction.getYear() == 2011)
        .sorted(Comparator.comparing(Transaction::getValue))
        .collect(Collectors.toList());

    //What are all the unique cities where the traders work?
    List<String> cities = transactions.stream()
        .map(transaction -> transaction.getTrader().getCity())
        .distinct()
        .collect(Collectors.toList());

    Set<String> citiesSet =
        transactions.stream()
            .map(transaction -> transaction.getTrader().getCity())
            .collect(toSet());

    // Finds all traders from Cambridge and sort them by name
    List<Trader> traders = transactions.stream()
        .map(Transaction::getTrader)
        .filter(trader -> "Cambridge".equals(trader.getCity()))
        .distinct()
        .sorted(Comparator.comparing(Trader::getName))
        .collect(Collectors.toList());

    // Returns a string of all traders’ names sorted alphabetically
    String traderStr =
        transactions.stream()
            .map(transaction -> transaction.getTrader().getName())
            .distinct()
            .sorted()
            .reduce("", (n1, n2) -> n1 + n2);
//    Note that this solution is inefficient (all Strings are repeatedly concatenated, which
//        creates a new String object at each iteration).
    String traderNames = transactions.stream()
        .map(transaction -> transaction.getTrader().getName())
        .distinct()
        .sorted()
        .collect(joining());

    // Are any traders based in Milan?
    boolean milan = transactions.stream()
        .anyMatch(transaction -> "Milan".equals(transaction.getTrader().getCity()));

    // Prints all transactions’ values from the traders living in Cambridge
    transactions.stream()
        .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
        .map(Transaction::getValue)
        .forEach(System.out::println);

    // What’s the highest value of all the transactions?
    Optional<Integer> highestValue = transactions.stream()
        .map(Transaction::getValue)
        .reduce(Integer::max);

    //Finds the transaction with the smallest value
    Optional<Transaction> highestValueTransaction = transactions.stream()
        .min(Comparator.comparing(Transaction::getValue));
  }


}
