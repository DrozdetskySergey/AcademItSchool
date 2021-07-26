package ru.academits.drozdetsky21.vector_test;

import ru.academits.drozdetsky21.vector.Vector;

public class Main {
    public static void main(String[] args) {
        Vector vector1 = new Vector(new double[]{1, 2, 3, 4, 5});
        Vector vector2 = new Vector(vector1);
        Vector vector3 = new Vector(10, new double[]{10, 10, 10, 10, 10, 10, 10});

        System.out.println(vector3.getLength());
        System.out.println(vector3.getSize());

        vector1.flip().add(vector3).deduct(vector2).set(1, vector1.get(4));
        System.out.println(vector1);

        Vector vector4 = Vector.getSum(vector2, vector1);
        System.out.println(vector2 + " + " + vector1 + " = " + vector4);

        Vector vector5 = Vector.getDifference(vector4, vector3);
        System.out.println(vector4 + " - " + vector3 + " = " + vector5);

        double scalarProduct = Vector.getScalarProduct(vector5, vector3);
        System.out.println(vector5 + " SP " + vector3 + " = " + scalarProduct);

        Vector vector6 = new Vector(-3);
        System.out.println(vector6);
    }
}
