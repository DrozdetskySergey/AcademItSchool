package ru.academits.drozdetsky21.vector;

import java.util.Arrays;

public class Vector {
    private double[] array;

    public Vector(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n < 1");
        }

        array = new double[n];
        Arrays.fill(array, 0);
    }

    public Vector(Vector vector) {
        if (vector == null) {
            throw new IllegalArgumentException("NULL");
        }

        array = vector.toArray();
    }

    public Vector(double[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("NULL | n < 1");
        }

        this.array = Arrays.copyOf(array, array.length);
    }

    public Vector(int n, double[] array) {
        if (array == null || n <= 0) {
            throw new IllegalArgumentException("NULL | n < 1");
        }

        this.array = Arrays.copyOf(array, n);
    }

    public int getSize() {
        return array.length;
    }

    public double[] toArray() {
        return Arrays.copyOf(array, array.length);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (double element : array) {
            stringBuilder.append(element).append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).append("}");

        return stringBuilder.toString();
    }
}
