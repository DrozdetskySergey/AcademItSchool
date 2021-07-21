package ru.academits.drozdetsky21.vector_test;

import ru.academits.drozdetsky21.vector.Vector;

public class Main {
    public static void main(String[] args) {
        Vector vector1 = new Vector(new double[]{1.23, 2, 3.333333, 4, 5});
        Vector vector2 = new Vector(vector1);
        Vector vector3 = new Vector(3, new double[]{10, 10, 10, 10, 10, 10, 10});

        vector1.doReverse().doAddition(vector3).doSubtraction(vector2).set(1, vector1.get(4));
        System.out.println(vector1);

        Vector vector4 = new Vector(-3);
    }
}
