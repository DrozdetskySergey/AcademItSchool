package ru.academits.drozdetsky21.matrix;

import ru.academits.drozdetsky21.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private Vector[] rows;

    public Matrix(int rowsCount, int columnsCount) {
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
            throw new IllegalArgumentException("Переданная матрица ссылается на NULL.");
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
            throw new IllegalArgumentException("Переданный массив double[][] пустой. У матрицы должен быть минимум 1 элемент.");
        }

        int rowMaxLength = 0;

        for (double[] row : array) {
            if (row.length > rowMaxLength) {
                rowMaxLength = row.length;
            }
        }

        if (rowMaxLength == 0) {
            throw new IllegalArgumentException("Переданный массив double[][] содержит только пустые подмассивы. У матрицы должен быть минимум 1 элемент.");
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
            throw new IllegalArgumentException("Переданный массив Vector[] пустой. У матрицы должен быть минимум 1 элемент.");
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
            rows[i] = new Vector(vectorMaxSize, vectors[i].toArray());
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

    public int getRowsCount() {
        return rows.length;
    }

    public int getColumnsCount() {
        return rows[0].getSize();
    }

    public Vector getRow(int index) {
        int lastIndex = rows.length - 1;

        if (index < 0 || index > lastIndex) {
            throw new IndexOutOfBoundsException("Передан rowIndex = " + index + "; Допустимые границы: [0, " + lastIndex + "].");
        }

        return new Vector(rows[index]);
    }

    public void setRow(int index, Vector row) {
        int lastIndex = rows.length - 1;

        if (index < 0 || index > lastIndex) {
            throw new IndexOutOfBoundsException("Передан rowIndex = " + index + "; Допустимые границы: [0, " + lastIndex + "].");
        }

        if (row == null) {
            throw new IllegalArgumentException("Переданный вектор ссылается на NULL.");
        }

        int vectorSize = row.getSize();
        int columnsCount = getColumnsCount();

        if (vectorSize != columnsCount) {
            throw new IllegalArgumentException("columnsCount = " + columnsCount + " не равно vectorSize = " + vectorSize + "; Переданный вектор не подходящего размера.");
        }

        rows[index] = new Vector(row);
    }

    public Vector getColumn(int index) {
        int lastIndex = getColumnsCount() - 1;

        if (index < 0 || index > lastIndex) {
            throw new IndexOutOfBoundsException("Передан columnIndex = " + index + "; Допустимые границы: [0, " + lastIndex + "].");
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
        Vector[] newRows = new Vector[getColumnsCount()];

        for (int i = 0; i < newRows.length; i++) {
            newRows[i] = getColumn(i);
        }

        rows = newRows;

        return this;
    }

    public Matrix multiply(double number) {
        for (Vector row : rows) {
            row.multiply(number);
        }

        return this;
    }

    public Matrix multiply(Matrix matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Матрица множитель ссылается на NULL.");
        }

        int columnsCount = getColumnsCount();

        if (columnsCount != matrix.rows.length) {
            throw new IllegalArgumentException("matrix1ColumnsCount = " + columnsCount + " должно быть равным matrix2RowsCount = " + matrix.rows.length + "; Матрицы не согласованны.");
        }

        int matrixColumnsCount = matrix.getColumnsCount();
        double[][] result = new double[rows.length][matrixColumnsCount];

        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < matrixColumnsCount; j++) {
                result[i][j] = Vector.getScalarProduct(rows[i], matrix.getColumn(j));
            }
        }

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Vector(result[i]);
        }

        return this;
    }

    public Vector multiply(Vector vector) {
        if (vector == null) {
            throw new IllegalArgumentException("Переданный вектор ссылается на NULL.");
        }

        int columnsCount = getColumnsCount();
        int vectorSize = vector.getSize();

        if (columnsCount != vectorSize) {
            throw new IllegalArgumentException("columnsCount = " + columnsCount + " должно быть равным vectorSize = " + vectorSize);
        }

        double[] result = new double[rows.length];

        for (int i = 0; i < rows.length; i++) {
            result[i] = Vector.getScalarProduct(rows[i], vector);
        }

        return new Vector(result);
    }

    public Matrix add(Matrix matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Прибавляемая матрица ссылается на NULL.");
        }

        checkSizesEquality(this, matrix);

        for (int i = 0; i < rows.length; i++) {
            rows[i].add(matrix.rows[i]);
        }

        return this;
    }

    public Matrix subtract(Matrix matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Вычитаемая матрица ссылается на NULL.");
        }

        checkSizesEquality(this, matrix);

        for (int i = 0; i < rows.length; i++) {
            rows[i].subtract(matrix.rows[i]);
        }

        return this;
    }

    public double getDeterminant() {
        int columnsCount = getColumnsCount();

        if (rows.length != columnsCount) {
            String message = String.format("Эта матрица размера [%d][%d]; Определитель вычисляется только для квадратной матрицы.", rows.length, columnsCount);

            throw new UnsupportedOperationException(message);
        }

        return getDeterminant(toArray());
    }

    private static void checkSizesEquality(Matrix matrix1, Matrix matrix2) {
        int matrix1ColumnsCount = matrix1.getColumnsCount();
        int matrix1RowsCount = matrix1.getRowsCount();
        int matrix2ColumnsCount = matrix2.getColumnsCount();
        int matrix2RowsCount = matrix2.getRowsCount();

        if (matrix1RowsCount != matrix2RowsCount || matrix1ColumnsCount != matrix2ColumnsCount) {
            String message = String.format("Базовая матрица размера [%d][%d], а вторая матрица размера [%d][%d]; Размерности матриц должны совпадать.", matrix1RowsCount, matrix1ColumnsCount, matrix2RowsCount, matrix2ColumnsCount);

            throw new IllegalArgumentException(message);
        }
    }

    public static Matrix getSum(Matrix base, Matrix addition) {
        if (base == null) {
            throw new IllegalArgumentException("Базовая матрица ссылается на NULL.");
        }

        if (addition == null) {
            throw new IllegalArgumentException("Прибавляемая матрица ссылается на NULL.");
        }

        return new Matrix(base).add(addition);
    }

    public static Matrix getDifference(Matrix base, Matrix deductible) {
        if (base == null) {
            throw new IllegalArgumentException("Базовая матрица ссылается на NULL.");
        }

        if (deductible == null) {
            throw new IllegalArgumentException("Вычитаемая матрица ссылается на NULL.");
        }

        return new Matrix(base).subtract(deductible);
    }

    public static Matrix getProduct(Matrix multiplier1, Matrix multiplier2) {
        if (multiplier1 == null) {
            throw new IllegalArgumentException("Левая матрица ссылается на NULL.");
        }

        if (multiplier2 == null) {
            throw new IllegalArgumentException("Правая матрица ссылается на NULL.");
        }

        return new Matrix(multiplier1).multiply(multiplier2);
    }

    private static double getDeterminant(double[][] array) {
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

            int k = 0;

            for (int j = 0; j < array.length; j++) {
                if (j != i) {
                    for (int n = 1; n < array.length; n++) {
                        minor[n - 1][k] = array[n][j]; // the null row is always ignored
                    }

                    k++;
                }
            }

            if (isSumming) {
                result += array[0][i] * getDeterminant(minor);
            } else {
                result -= array[0][i] * getDeterminant(minor);
            }

            isSumming = !isSumming;
        }

        return result;
    }
}
