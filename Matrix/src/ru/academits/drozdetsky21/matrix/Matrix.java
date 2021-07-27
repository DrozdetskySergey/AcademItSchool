package ru.academits.drozdetsky21.matrix;

import ru.academits.drozdetsky21.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private Vector[] rows;

    public Matrix(int rows, int columns) {
        if (columns <= 0) {
            throw new IllegalArgumentException("Конструктор Matrix: columns = " + columns + "; У матрицы должен быть минимум 1 столбец.");
        }

        if (rows <= 0) {
            throw new IllegalArgumentException("Конструктор Matrix: rows = " + rows + "; У матрицы должна быть минимум 1 строка.");
        }

        this.rows = new Vector[rows];

        for (int i = 0; i < rows; i++) {
            this.rows[i] = new Vector(columns);
        }
    }

    public Matrix(Matrix matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Конструктор Matrix(Matrix): Переданная матрицы ссылается на NULL.");
        }

        rows = new Vector[matrix.rows.length];

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Vector(matrix.rows[i]);
        }
    }

    public Matrix(double[][] array) {
        if (array == null) {
            throw new IllegalArgumentException("Конструктор Matrix(double[][]): Переданный массив ссылается на NULL.");
        }

        if (array.length == 0) {
            throw new IllegalArgumentException("Конструктор Matrix(double[][]): Длинна массива = 0; У матрицы должна быть минимум 1 строка.");
        }

        int rowMaxLength = 0;

        for (double[] row : array) {
            if (row.length > rowMaxLength) {
                rowMaxLength = row.length;
            }
        }

        if (rowMaxLength == 0) {
            throw new IllegalArgumentException("Конструктор Matrix(double[][]): Длина всех вложенных массивов = 0; У матрицы должен быть минимум 1 столбец.");
        }

        rows = new Vector[array.length];

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Vector(rowMaxLength, array[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        if (vectors == null) {
            throw new IllegalArgumentException("Конструктор Matrix(Vector[]): Переданный массив ссылается на NULL.");
        }

        if (vectors.length == 0) {
            throw new IllegalArgumentException("Конструктор Matrix(Vector[]): Длинна массива = 0; У матрицы должна быть минимум 1 строка и 1 столбец.");
        }

        int vectorMaxSize = 0;

        for (Vector vector : vectors) {
            int vectorSize = vector.getSize();

            if (vectorSize > vectorMaxSize) {
                vectorMaxSize = vectorSize;
            }
        }

        rows = new Vector[vectors.length];

        for (int i = 0; i < vectors.length; i++) {
            this.rows[i] = new Vector(vectorMaxSize, vectors[i].toArray());
        }
    }

    public int getRowsCount() {
        return rows.length;
    }

    public int getColumnsCount() {
        return rows[0].getSize();
    }

    public Vector getRow(int index) {
        return rows[index];
    }

    public void setRow(int index, Vector row) {
        if (row == null) {
            throw new IllegalArgumentException("Замена ветора-строки в матрице по индексу: Переданный вектор ссылается на NULL.");
        }

        if (row.getSize() != rows[0].getSize()) {
            throw new IllegalArgumentException("Замена ветора-строки в матрице по индексу: Переданный вектор не подходящей длины.");
        }

        rows[index] = row;
    }

    public Vector getColumn(int index) {
        double[] array = new double[rows.length];

        for (int i = 0; i < rows.length; i++) {
            array[i] = rows[i].get(index);
        }

        return new Vector(array);
    }

    public double[][] toArray() {
        double[][] result = new double[rows.length][];

        for (int i = 0; i < result.length; i++) {
            result[i] = rows[i].toArray();
        }

        return result;
    }

    public Matrix transpose() {
        double[][] array = toArray();
        double[][] result = new double[array[0].length][array.length];

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                result[j][i] = array[i][j];
            }
        }

        rows = new Vector[result.length];

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Vector(result[i]);
        }

        return this;
    }

    public Matrix multiply(double number) {
        for (Vector row : rows) {
            row.multiply(number);
        }

        return this;
    }

    public Matrix multiply(Matrix matrix) {
        if (rows[0].getSize() != matrix.rows.length) {
            throw new IllegalArgumentException("Умножение матрицы на матрицу: не подходящие размерности (не согласованны).");
        }

        int matrixColumnsCount = matrix.rows[0].getSize();
        double[][] result = new double[rows.length][matrixColumnsCount];

        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < matrixColumnsCount; j++) {
                result[i][j] = Vector.getScalarProduct(rows[i], matrix.getColumn(j));
            }
        }

        rows = new Vector[rows.length];

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Vector(result[i]);
        }

        return this;
    }

    public Matrix multiply(Vector vector) {
        if (rows[0].getSize() != vector.getSize()) {
            throw new IllegalArgumentException("Умножение матрицы на вектор: не подходящие размерности.");
        }

        Matrix matrix = new Matrix(new Vector[]{vector});

        return multiply(matrix.transpose());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Matrix matrix = (Matrix) o;

        return Arrays.equals(rows, matrix.rows);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(rows);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (Vector row : rows) {
            stringBuilder.append(row).append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).append("}");

        return stringBuilder.toString();
    }
}
