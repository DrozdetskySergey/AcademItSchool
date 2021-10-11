package ru.academits.drozdetskiy21.arraylist_test;

import ru.academits.drozdetskiy21.arraylist.ArrayList;

import java.util.Arrays;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(0, "0");
        strings.ensureCapacity(11);
        System.out.println(strings);

        strings = new ArrayList<>(0);
        strings.add("0");
        strings.add(1, "1");
        System.out.println(strings);

        strings = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e"));
        strings.trimToSize();
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

        for (String s : strings) {
            System.out.println(s);

            for (Iterator<String> iterator = strings.iterator(); iterator.hasNext(); ) {
                String string = iterator.next();
                System.out.println("-" + string);

                if ("d".equals(string)) {
                    iterator.remove();
                }
            }

            System.out.println("#");
        }
    }
}
