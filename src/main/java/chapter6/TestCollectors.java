package chapter6;

import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

public class TestCollectors {

  public static void main(String[] args) {
    List<Dish> menu = Arrays.asList(
        new Dish("pork", false, 800, Dish.Type.MEAT),
        new Dish("beef", false, 700, Dish.Type.MEAT),
        new Dish("chicken", false, 400, Dish.Type.MEAT),
        new Dish("french fries", true, 530, Dish.Type.OTHER),
        new Dish("rice", true, 350, Dish.Type.OTHER),
        new Dish("season fruit", true, 120, Dish.Type.OTHER),
        new Dish("pizza", true, 550, Dish.Type.OTHER),
        new Dish("prawns", false, 300, Dish.Type.FISH),
        new Dish("salmon", false, 450, Dish.Type.FISH));


    // Maximum Calorie dish
    Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
    Optional<Dish> mostCalorieDish = menu.stream()
            .collect(maxBy(dishCaloriesComparator));

    // total calories
    int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));

    //average calories
    double avgCalories =
        menu.stream().collect(averagingInt(Dish::getCalories));

    IntSummaryStatistics menuStatistics =
        menu.stream().collect(summarizingInt(Dish::getCalories));
    System.out.println(menuStatistics);
    // joining string
    String shortMenu = menu.stream().map(Dish::getName).collect(joining());
    System.out.println(shortMenu);
    String shortMenuCommaSeparated = menu.stream().map(Dish::getName).collect(joining(", "));

    // All of above are special cases of reducing
    int totalCaloriesGeneral = menu.stream().collect(reducing(
        0, Dish::getCalories, (i, j) -> i + j));
    Optional<Dish> mostCalorieDishGen =
        menu.stream().collect(reducing(
            (d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));

  }
}
