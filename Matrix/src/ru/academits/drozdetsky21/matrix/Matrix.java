package ru.academits.drozdetsky21.matrix;

import ru.academits.drozdetsky21.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private Vector[] rows;

    public Matrix(int columnsCount, int rowsCount) {
        if (columnsCount <= 0) {
            throw new IllegalArgumentException("columnsCount = " + columnsCount + "; У матрицы должен быть минимум 1 столбец.");
        }

        if (rowsCount <= 0) {
            throw new IllegalArgumentException("rowsCount = " + rowsCount + "; У матрицы должна быть минимум 1 строка.");
        }

        rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            rows[i] = new Vector(columnsCount);
        }
    }

    public Matrix(Matrix matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Переданная матрицы ссылается на NULL.");
        }

        rows = new Vector[matrix.rows.length];

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Vector(matrix.rows[i]);
        }
    }

    public Matrix(double[][] array) {
        if (array == null) {
            throw new IllegalArgumentException("Переданный массив double[][] ссылается на NULL.");
        }

        if (array.length == 0) {
            throw new IllegalArgumentException("array.length == 0; У матрицы должна быть минимум 1 строка.");
        }

        int rowMaxLength = 0;

        for (double[] row : array) {
            if (row.length > rowMaxLength) {
                rowMaxLength = row.length;
            }
        }

        if (rowMaxLength == 0) {
            throw new IllegalArgumentException("У матрицы должен быть минимум 1 столбец.");
        }

        rows = new Vector[array.length];

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Vector(rowMaxLength, array[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        if (vectors == null) {
            throw new IllegalArgumentException("Переданный массив Vector[] ссылается на NULL.");
        }

        if (vectors.length == 0) {
            throw new IllegalArgumentException("vectors.length == 0; У матрицы должна быть минимум 1 строка.");
        }

        int vectorMaxSize = 0;

        for (Vector vector : vectors) {
            int vectorSize = vector.getSize();

            if (vectorSize > vectorMaxSize) {
                vectorMaxSize = vectorSize;
            }
        }

        if (vectorMaxSize == 0) {
            throw new IllegalArgumentException("У матрицы должен быть минимум 1 столбец.");
        }

        rows = new Vector[vectors.length];

        for (int i = 0; i < vectors.length; i++) {
            this.rows[i] = new Vector(vectorMaxSize, vectors[i].toArray());
        }
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
