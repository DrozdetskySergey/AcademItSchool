package ru.academits.drozdetsky21.lambdas;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class Lambdas {
    public static void main(String[] args) {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Ivan", 23));
        persons.add(new Person("Roman", 12));
        persons.add(new Person("Ivan", 18));
        persons.add(new Person("Svetlana", 18));
        persons.add(new Person("Julia", 15));
        persons.add(new Person("Sergey", 24));
        persons.add(new Person("Ludmila", 35));

        // А. получить список уникальных имен
        List<String> uniqueNames = persons.stream()
                .map(Person::getName)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(uniqueNames);

        System.out.println();

        // Б. вывести список уникальных имен в формате: Имена: Иван, Сергей, Петр.
        String uniqueNamesMessage = uniqueNames.stream()
                .collect(Collectors.joining(", ", "Names: ", "."));
        System.out.println(uniqueNamesMessage);

        System.out.println();

        // В. получить список людей младше 18, посчитать для них средний возраст
        OptionalDouble minorsAverageAge = persons.stream()
                .filter(p -> p.getAge() < 18)
                .mapToInt(Person::getAge)
                .average();

        if (minorsAverageAge.isPresent()) {
            System.out.println(minorsAverageAge.getAsDouble());
        }

        System.out.println();

        // Г. при помощи группировки получить Map, в котором ключи имена, а значения средний возраст
        persons.stream()
                .collect(Collectors.groupingBy(Person::getName, Collectors.averagingInt(Person::getAge)))
                .forEach((n, a) -> System.out.printf("%s - %s%n", n, a));

        System.out.println();

        // Д. получить людей, возраст которых от 20 до 45, вывести в консоль их имена в порядке убывания возраста
        persons.stream()
                .filter(p -> p.getAge() >= 20 && p.getAge() <= 45)
                .sorted((p1, p2) -> p2.getAge() - p1.getAge())
                .forEach(p -> System.out.println(p.getName()));

        System.out.println();

        // * Создать бесконечный поток корней чисел. С консоли прочитать число сколько элементов нужно вычислить, затем распечатать эти элементы
        Scanner scanner = new Scanner(System.in);

        System.out.print("Сколько элементов последовательности корней чмсел вывести? n = ");
        int numbersRootsCount = scanner.nextInt();

        DoubleStream.iterate(1, n -> n + 1)
                .map(Math::sqrt)
                .limit(numbersRootsCount)
                .forEach(System.out::println);

        System.out.println();

        // ** Попробовать реализовать бесконечный поток чисел Фиббоначчи
        Stream.iterate(new int[]{0, 1}, n -> new int[]{n[1], n[0] + n[1]})
                .mapToInt(e -> e[0])
                .limit(9)
                .forEach(System.out::println);
    }
}
