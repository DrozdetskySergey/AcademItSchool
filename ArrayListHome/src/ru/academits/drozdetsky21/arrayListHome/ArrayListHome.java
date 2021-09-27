package ru.academits.drozdetsky21.arrayListHome;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListHome {
    public static void main(String[] args) {
        // 1. Прочитать в список все строки из файла
        List<String> strings = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("in.txt"))) {
            String string = reader.readLine();

            while (string != null) {
                strings.add(string);
                string = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Problem with file!");
        }

        System.out.println(strings);

        System.out.println();

        /* 2. Есть список из целых чисел. Удалить из него все четные числа.
              В этой задаче новый список создавать нельзя */
        List<Integer> numbers = new ArrayList<>(Arrays.asList(2, 3, 7, 1, -2, 7, 3, 5, 1, 4, 8, 9, 1));
        System.out.println(numbers);

        int i = 0;

        while (i < numbers.size()) {
            if (numbers.get(i) % 2 == 0) {
                numbers.remove(i);
            } else {
                i++;
            }
        }

        System.out.println(numbers);

        System.out.println();

        /* 3. Есть список из целых чисел, в нём некоторые числа могут
              повторяться. Надо создать новый список, в котором будут
              элементы первого списка в таком же порядке, но без
              повторений */
        List<Integer> uniqueNumbers = new ArrayList<>();

        for (Integer n : numbers) {
            if (!uniqueNumbers.contains(n)) {
                uniqueNumbers.add(n);
            }
        }

        System.out.println(uniqueNumbers);
    }
}
