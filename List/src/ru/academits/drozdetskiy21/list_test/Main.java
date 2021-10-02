package ru.academits.drozdetskiy21.list_test;

import ru.academits.drozdetskiy21.list.List;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<String> stringsList = new List<>(Arrays.asList("a", "b", "c", "d", "e"));
        System.out.println(stringsList.getSize());
        System.out.println(stringsList);

        System.out.println();

        stringsList.add(stringsList.getSize(), "f");
        stringsList.addFirst("0");
        System.out.println(stringsList);

        System.out.println();

        System.out.println(stringsList.set(0, "g"));
        System.out.println(stringsList);

        System.out.println();

        Scanner scanner = new Scanner(System.in);

        System.out.print("remove String = ");
        String inputString = scanner.nextLine();

        System.out.println(stringsList.remove(inputString));
        System.out.println(stringsList.remove("y"));
        System.out.println(stringsList.remove(1));
        System.out.println(stringsList.removeFirst());
        System.out.println(stringsList);

        System.out.println();

        stringsList.addFirst("o");
        System.out.println(stringsList);
        System.out.println(stringsList.getFirst());
        System.out.println(stringsList.get(3));

        System.out.println();

        System.out.println(stringsList.getClone() + " - " + stringsList.reverse());

        System.out.println();

        stringsList = new List<>();
        System.out.println(stringsList);
        stringsList.getFirst();
    }
}
