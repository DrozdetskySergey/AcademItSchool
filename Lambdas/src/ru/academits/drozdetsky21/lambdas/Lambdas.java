package ru.academits.drozdetsky21.lambdas;

import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;

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

        // получить список уникальных имен
        List<String> uniqueNames = persons.stream().map(Person::getName).distinct().collect(Collectors.toList());
        System.out.println(uniqueNames);

        System.out.println();

        // вывести список уникальных имен в формате: Имена: Иван, Сергей, Петр.
        String message = uniqueNames.stream().collect(Collectors.joining(", ", "Names: ", "."));
        System.out.println(message);

        System.out.println();

        // получить список людей младше 18, посчитать для них средний возраст
        OptionalDouble minorsAverageAge = persons.stream().filter(p -> p.getAge() < 18).mapToInt(Person::getAge).average();

        if (minorsAverageAge.isPresent()) {
            System.out.println(minorsAverageAge.getAsDouble());
        }

        System.out.println();

        // при помощи группировки получить Map, в котором ключи имена, а значения средний возраст
        Map<String, Double> averageAgesForNames = new HashMap<>();

        persons.stream().collect(Collectors.groupingBy(Person::getName)).forEach((name, listForName) -> {
            OptionalDouble averageAge = listForName.stream().mapToInt(Person::getAge).average();

            if (averageAge.isPresent()) {
                averageAgesForNames.put(name, averageAge.getAsDouble());
            }
        });

        System.out.println(averageAgesForNames);

        System.out.println();

        // получить людей, возраст которых от 20 до 45, вывести в консоль их имена в порядке убывания возраста
        persons.stream()
                .filter(p -> p.getAge() >= 20 & p.getAge() <= 45)
                .sorted((p1, p2) -> p2.getAge() - p1.getAge())
                .forEach(p -> System.out.println(p.getName()));
    }
}
