package ru.academits.drozdetskiy21.hashtable_test;

import ru.academits.drozdetskiy21.hashtable.HashTable;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        HashTable<String> hashTable = new HashTable<>(1);
        hashTable.add("0");
        hashTable.add(null);
        hashTable.add("0");
        System.out.println(hashTable);

        hashTable = new HashTable<>();
        hashTable.addAll(Arrays.asList("a", "b", "c", "d", "e"));

        for (String s : hashTable) {
            System.out.println(s);
        }
    }
}
