package ru.academits.drozdetsky21.matrix_test;

import ru.academits.drozdetsky21.matrix.Matrix;

public class Main {
    public static void main(String[] args) {
        Matrix matrix = new Matrix(new double[][]{
                {1, 2, 3, 3},
                {4, 5, 6},
                {7, 8, 9, 10, 11, 12}
        });

        System.out.println(matrix);
    }
}
