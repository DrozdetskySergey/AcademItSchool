package ru.acadevitschool.drozdetsky21.shapes_test;

import ru.acadevitschool.drozdetsky21.shapes.*;

import java.util.Arrays;

public class Main {
    private static Shape getShapeWithMaxArea(Shape[] shapes) {
        if (shapes == null) {
            throw new IllegalArgumentException("Передан NULL.");
        }

        if (shapes.length == 0) {
            throw new IllegalArgumentException("Shape[] должен иметь минимум один элемент.");
        }

        Arrays.sort(shapes, new AreaComparator());

        return shapes[shapes.length - 1];
    }

    private static Shape getShapeWithSecondPerimeter(Shape[] shapes) {
        if (shapes == null) {
            throw new IllegalArgumentException("Передан NULL.");
        }

        if (shapes.length <= 1) {
            throw new IllegalArgumentException("Shape[] должен иметь минимум два элемента.");
        }

        Arrays.sort(shapes, new PerimeterComparator());

        return shapes[shapes.length - 2];
    }

    public static void main(String[] args) {
        Shape[] shapes = {
                new Square(5),
                new Square(4),
                new Triangle(0, 0, 5, 0, 0, 5),
                new Triangle(8, 0, 0, 8, 9, 9),
                new Rectangle(4, 6),
                new Rectangle(10, 3),
                new Circle(3),
                new Circle(4)
        };

        Shape maxAreaShape = getShapeWithMaxArea(shapes);
        System.out.printf("Фигурой с максимальной площадью = %.4f, является %s%n", maxAreaShape.getArea(), maxAreaShape);

        Shape secondPerimeterShape = getShapeWithSecondPerimeter(shapes);
        System.out.printf("Фигурой со вторым по величине периметром = %.4f, является %s%n", secondPerimeterShape.getPerimeter(), secondPerimeterShape);
    }
}
