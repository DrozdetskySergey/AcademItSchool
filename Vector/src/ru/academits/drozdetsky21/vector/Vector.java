package ru.academits.drozdetsky21.vector;

import java.util.Arrays;

public class Vector {
    private double[] components;

    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size = " + size + " У вектора должен быть минимум 1 компонент.");
        }

        components = new double[size];
    }

    public Vector(Vector vector) {
        if (vector == null) {
            throw new IllegalArgumentException("Передан vector == NULL.");
        }

        components = Arrays.copyOf(vector.components, vector.components.length);
    }

    public Vector(double[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Передан double[] array == NULL.");
        }

        if (array.length == 0) {
            throw new IllegalArgumentException("Передан пустой массив. У вектора должен быть минимум 1 компонент.");
        }

        this.components = Arrays.copyOf(array, array.length);
    }

    public Vector(int size, double[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Передан double[] array == NULL.");
        }

        if (size <= 0) {
            throw new IllegalArgumentException("size = " + size + " У вектора должен быть минимум 1 компонент.");
        }

        this.components = Arrays.copyOf(array, size);
    }

    public double[] toArray() {
        return Arrays.copyOf(components, components.length);
    }

    public double getLength() {
        double componentsInSquareSum = 0;

        for (double component : components) {
            componentsInSquareSum += component * component;
        }

        return Math.sqrt(componentsInSquareSum);
    }

    public int getSize() {
        return components.length;
    }

    public double get(int index) {
        return components[index];
    }

    public void set(int index, double component) {
        components[index] = component;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (double component : components) {
            stringBuilder.append(component).append(", ");
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

        return Arrays.equals(components, vector.components);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        for (double element : components) {
            result = prime * result + Double.hashCode(element);
        }

        return result;
    }

    public Vector add(Vector vector) {
        int vectorSize = vector.components.length;

        if (components.length < vectorSize) {
            components = Arrays.copyOf(components, vectorSize);
        }

        for (int i = 0; i < vectorSize; i++) {
            components[i] += vector.components[i];
        }

        return this;
    }

    public Vector deduct(Vector vector) {
        int vectorSize = vector.components.length;

        if (components.length < vectorSize) {
            components = Arrays.copyOf(components, vectorSize);
        }

        for (int i = 0; i < vectorSize; i++) {
            components[i] -= vector.components[i];
        }

        return this;
    }

    public Vector multiply(double number) {
        for (int i = 0; i < components.length; i++) {
            components[i] *= number;
        }

        return this;
    }

    public Vector flip() {
        return multiply(-1);
    }

    public static Vector getSum(Vector vector1, Vector vector2) {
        return new Vector(vector1).add(vector2);
    }

    public static Vector getDifference(Vector base, Vector deductible) {
        return new Vector(base).deduct(deductible);
    }

    public static double getScalarProduct(Vector vector1, Vector vector2) {
        final int minSize = Math.min(vector1.components.length, vector2.components.length);
        int scalarProduct = 0;

        for (int i = 0; i < minSize; i++) {
            scalarProduct += vector1.components[i] * vector2.components[i];
        }

        return scalarProduct;
    }
}
