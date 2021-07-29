package ru.academits.drozdetsky21.matrix;

import ru.academits.drozdetsky21.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private Vector[] rows;

    public Matrix(int rowsCount, int columnsCount) {
        if (columnsCount <= 0) {
            throw new IllegalArgumentException("Конструктор Matrix: columnsCount = " + columnsCount + "; У матрицы должен быть минимум 1 столбец.");
        }

        if (rowsCount <= 0) {
            throw new IllegalArgumentException("Конструктор Matrix: rowsCount = " + rowsCount + "; У матрицы должна быть минимум 1 строка.");
        }

        this.rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            this.rows[i] = new Vector(columnsCount);
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
        if (index < 0 || index >= rows.length) {
            throw new IllegalArgumentException("Выдача ветора-строки в матрице по индексу: Передан index = " + index + " такого индекса не существует.");
        }

        return rows[index];
    }

    public void setRow(int index, Vector row) {
        if (index < 0 || index >= rows.length) {
            throw new IllegalArgumentException("Замена ветора-строки в матрице по индексу: Передан index = " + index + " такого индекса не существует.");
        }

        if (row == null) {
            throw new IllegalArgumentException("Замена ветора-строки в матрице по индексу: Переданный вектор ссылается на NULL.");
        }

        if (row.getSize() != rows[0].getSize()) {
            throw new IllegalArgumentException("Замена ветора-строки в матрице по индексу: Переданный вектор не подходящей длины.");
        }

        rows[index] = row;
    }

    public Vector getColumn(int index) {
        if (index < 0 || index >= rows[0].getSize()) {
            throw new IllegalArgumentException("Выдача ветора-столбца в матрице по индексу: Передан index = " + index + " такого индекса не существует.");
        }

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
        double[][] transposedArray = new double[array[0].length][array.length];

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                transposedArray[j][i] = array[i][j];
            }
        }

        rows = new Vector[transposedArray.length];

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Vector(transposedArray[i]);
        }

        return this;
    }

    public Matrix multiply(double number) {
        for (Vector row : rows) {
            row.multiply(number);
        }

        return this;
    }

    public Matrix multiply(Matrix multiplier) {
        if (multiplier == null) {
            throw new IllegalArgumentException("Умножение матрицы на матрицу: Переданная матрица ссылается на NULL.");
        }

        if (rows[0].getSize() != multiplier.rows.length) {
            throw new IllegalArgumentException("Умножение матрицы на матрицу: Не подходящие размерности матриц (не согласованны).");
        }

        int multiplierColumnsCount = multiplier.rows[0].getSize();
        double[][] result = new double[rows.length][multiplierColumnsCount];

        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < multiplierColumnsCount; j++) {
                result[i][j] = Vector.getScalarProduct(rows[i], multiplier.getColumn(j));
            }
        }

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Vector(result[i]);
        }

        return this;
    }

    public Matrix multiply(Vector vector) {
        if (vector == null) {
            throw new IllegalArgumentException("Умножение матрицы на вектор: Переданный вектор ссылается на NULL.");
        }

        if (rows[0].getSize() != vector.getSize()) {
            throw new IllegalArgumentException("Умножение матрицы на вектор: не подходящие размерности.");
        }

        Matrix matrix = new Matrix(new Vector[]{vector});

        return multiply(matrix.transpose());
    }

    private Matrix add(Matrix matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Сложение матриц: Переданная матрица ссылается на NULL.");
        }

        if (matrix.rows.length != rows.length || matrix.rows[0].getSize() != rows[0].getSize()) {
            throw new IllegalArgumentException("Сложение матриц: Размерности матриц на совпадают.");
        }

        for (int i = 0; i < rows.length; i++) {
            rows[i].add(matrix.rows[i]);
        }

        return this;
    }

    public Matrix deduct(Matrix matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Вычитание матрицы: Переданная матрица ссылается на NULL.");
        }

        if (matrix.rows.length != rows.length || matrix.rows[0].getSize() != rows[0].getSize()) {
            throw new IllegalArgumentException("Вычитание матрицы: Размерности матриц на совпадают.");
        }

        for (int i = 0; i < rows.length; i++) {
            rows[i].deduct(matrix.rows[i]);
        }

        return this;
    }

    public double getDeterminant() {
        if (rows.length != rows[0].getSize()) {
            throw new IllegalArgumentException("Вычисление определителя матрицы: Определитель вычисляется только для квадратной матрицы.");
        }

        return getArrayDeterminant(toArray());
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

    public static Matrix getSum(Matrix base, Matrix addition) {
        return new Matrix(base).add(addition);
    }

    public static Matrix getDifference(Matrix base, Matrix deductible) {
        return new Matrix(base).deduct(deductible);
    }

    public static Matrix getMultiplication(Matrix multiplier1, Matrix multiplier2) {
        return new Matrix(multiplier1).multiply(multiplier2);
    }

    private static double getArrayDeterminant(double[][] array) {
        if (array.length == 1) {
            return array[0][0];
        }

        if (array.length == 2) {
            return array[0][0] * array[1][1] - array[0][1] * array[1][0];
        }

        double result = 0;
        boolean isSumming = true;

        for (int i = 0; i < array.length; i++) { // i <- ignoreColumnNumber
            double[][] minor = new double[array.length - 1][array.length - 1];

            int t = 0;

            for (int j = 0; j < array.length; j++) {
                if (j != i) {
                    for (int n = 1; n < array.length; n++) {
                        minor[n - 1][t] = array[n][j]; // the null row is always ignored
                    }

                    t++;
                }
            }

            if (isSumming) {
                result += array[0][i] * getArrayDeterminant(minor);
            } else {
                result -= array[0][i] * getArrayDeterminant(minor);
            }

            isSumming = !isSumming;
        }

        return result;
    }
}
