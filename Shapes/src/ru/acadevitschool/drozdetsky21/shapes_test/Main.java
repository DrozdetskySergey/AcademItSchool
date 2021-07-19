package ru.acadevitschool.drozdetsky21.shapes_test;

import ru.acadevitschool.drozdetsky21.shapes.*;

public class Main {
    private static Shape getAreaMax(Shape[] shapes) {
        if (shapes == null || shapes.length == 0) {
            return null;
        }

        //TODO

        return shapes[0];
    }

    private static Shape getSecondPerimeter(Shape[] shapes) {
        if (shapes == null || shapes.length < 2) {
            return null;
        }

        //TODO

        return shapes[1];
    }

    public static void main(String[] args) {
        Shape[] shapes = {
                new Square(5),
                new Square(4),
                new Triangle(0, 0, 5, 0, 0, 5),
                new Triangle(2, 0, 0, 2, 7, 7),
                new Rectangle(4, 6),
                new Rectangle(10, 3),
                new Circle(5),
                new Circle(6.5)};

        System.out.printf("Фигурой с максимальной площадью является %s%n", getAreaMax(shapes));
        System.out.printf("Фигурой со вторым по величине периметром является %s%n", getSecondPerimeter(shapes));
    }
}
