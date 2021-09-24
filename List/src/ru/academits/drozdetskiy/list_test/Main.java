package ru.academits.drozdetskiy.list_test;

import ru.academits.drozdetskiy21.list.List;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        List<String> stringsList = new List<>(Arrays.asList("a", "b", "c", "d", "e"));
        System.out.println(stringsList.getSize());
        System.out.println(stringsList);

        stringsList.add(2, "f");
        stringsList.addFirstOne("0");
        System.out.println(stringsList);

        System.out.println(stringsList.set(0, "g"));
        System.out.println(stringsList);

        System.out.println(stringsList.remove("f"));
        System.out.println(stringsList.remove(1));
        System.out.println(stringsList.removeFirstOne());
        System.out.println(stringsList);

        System.out.println(stringsList.get(3));

        stringsList.addFirstOne("o");
        System.out.println(stringsList);
        System.out.println(stringsList.getFirstOne());

        stringsList = new List<>();
        System.out.println(stringsList);
        stringsList.getFirstOne();
    }
}
