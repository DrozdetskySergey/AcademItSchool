package ru.academits.drozdetsky21.vector;

import java.util.Arrays;

public class Vector {
    private double[] array;

    public Vector(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("length < 1");
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
            throw new IllegalArgumentException("NULL | length < 1");
        }

        this.array = Arrays.copyOf(array, array.length);
    }

    public Vector(int n, double[] array) {
        if (array == null || n <= 0) {
            throw new IllegalArgumentException("NULL | length < 1");
        }

        this.array = Arrays.copyOf(array, n);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Vector vector = (Vector) o;

        return Arrays.equals(array, vector.array);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        for (double element : array) {
            result = prime * result + (int) element;
        }

        return result;
    }

    public Vector doAddition(Vector vector) {
        int length = Math.min(this.array.length, vector.array.length);

        for (int i = 0; i < length; i++) {
            array[i] += vector.array[i];
        }

        return this;
    }

    public Vector doSubtraction(Vector vector) {
        int length = Math.min(this.array.length, vector.array.length);

        for (int i = 0; i < length; i++) {
            array[i] -= vector.array[i];
        }

        return this;
    }

    public Vector doMultiplication(double number) {
        for (int i = 0; i < array.length; i++) {
            array[i] *= number;
        }

        return this;
    }

    public Vector doReverse() {
        return doMultiplication(-1);
    }

    public int getSize() {
        return array.length;
    }

    public double get(int index) {
        return array[index];
    }

    public void set(int index, double element) {
        array[index] = element;
    }
}
