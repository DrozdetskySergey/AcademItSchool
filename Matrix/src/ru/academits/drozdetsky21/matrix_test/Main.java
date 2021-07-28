package ru.academits.drozdetsky21.matrix_test;

import ru.academits.drozdetsky21.matrix.Matrix;
import ru.academits.drozdetsky21.vector.Vector;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Matrix matrix1 = new Matrix(new double[][]{
                {1, 2, 3, 3},
                {4, 5, 6},
                {7, 8, 9, 10, 11, 12}
        });

        System.out.println(matrix1);
        System.out.println(Arrays.deepToString(matrix1.toArray()));
        System.out.println(matrix1.getColumnsCount() + ", " + matrix1.getRowsCount());

        matrix1.multiply(0.5).transpose();
        System.out.println(Arrays.deepToString(matrix1.toArray()));

        Matrix matrix2 = new Matrix(new double[][]{
                {1, 2},
                {3, 4},
                {5, 6},
                {7, 8}
        });

        Matrix matrix3 = new Matrix(new double[][]{
                {1, 2, 3},
                {4, 5, 6}
        });

        System.out.println(matrix2.multiply(matrix3));

        Vector vector1 = new Vector(new double[]{1, 2, 3});
        System.out.print(matrix1);
        System.out.println(" * " + vector1 + " = " + matrix1.multiply(vector1));

        Matrix matrix4 = new Matrix(new double[][]{
                {-2, 2, 1, 0},
                {1, -3, 3, 7},
                {2, -1, 2, -3},
                {-5, 4, -1, 2}
        });

        System.out.println(matrix4.getDeterminant());
    }
}
