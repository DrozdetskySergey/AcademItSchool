package ru.academits.drozdetsky21.vector;

import java.util.Arrays;

public class Vector {
    private final double[] array;

    public double[] getArray() {
        return array;
    }

    public Vector(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Длина вектора не может быть меньше 1.");
        }

        array = new double[n];
        Arrays.fill(array, 0);
    }

    public Vector(Vector vector) {
        if (vector == null) {
            throw new IllegalArgumentException("Передан NULL.");
        }

        array = Arrays.copyOf(vector.getArray(), vector.getSize());
    }

    public Vector(double[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Передан NULL.");
        }

        if (array.length == 0) {
            throw new IllegalArgumentException("Передан пустой массив, а длина вектора не может быть меньше 1.");
        }

        this.array = Arrays.copyOf(array, array.length);
    }

    public Vector(int n, double[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Передан NULL.");
        }

        if (n <= 0) {
            throw new IllegalArgumentException("Длина вектора не может быть меньше 1.");
        }

        this.array = Arrays.copyOf(array, n);
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

        return Arrays.equals(array, vector.getArray());
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;

        for (double element : array) {
            result = PRIME * result + Double.hashCode(element);
        }

        return result;
    }

    public Vector doAddition(Vector vector) {
        final int LENGTH = Math.min(this.getSize(), vector.getSize());

        for (int i = 0; i < LENGTH; i++) {
            array[i] += vector.get(i);
        }

        return this;
    }

    public Vector doSubtraction(Vector vector) {
        final int LENGTH = Math.min(this.getSize(), vector.getSize());

        for (int i = 0; i < LENGTH; i++) {
            array[i] -= vector.get(i);
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

    public static Vector getSum(Vector vector1, Vector vector2) {
        return new Vector(vector1).doAddition(vector2);
    }

    public static Vector getSubtraction(Vector base, Vector deductible) {
        return new Vector(base).doSubtraction(deductible);
    }

    public static double getScalarProduct(Vector vector1, Vector vector2) {
        final int LENGTH = Math.min(vector1.getSize(), vector2.getSize());
        int scalarProduct = 0;

        for (int i = 0; i < LENGTH; i++) {
            scalarProduct += vector1.get(i) * vector2.get(i);
        }

        return scalarProduct;
    }
}
