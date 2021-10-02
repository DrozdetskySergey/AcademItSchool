package ru.academits.drozdetskiy21.arraylist_test;

import ru.academits.drozdetskiy21.arraylist.ArrayList;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(0, "0");
        System.out.println(strings);

        strings = new ArrayList<>(0);
        strings.add("0");
        strings.add(1, "1");
        System.out.println(strings);

        strings = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e"));
        System.out.println(strings);

        strings.addAll(2, Arrays.asList("0", "1"));
        System.out.println(strings);

        strings.remove(2);
        System.out.println(strings);

        strings.remove("1");
        System.out.println(strings);

        strings.addAll(strings);
        System.out.println(strings);

        strings.removeAll(Arrays.asList("a", "e"));
        System.out.println(strings);

        strings.retainAll(Arrays.asList("b", "d"));
        System.out.println(strings);
    }
}
