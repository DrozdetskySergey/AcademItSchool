package ru.acadevitschool.drozdetsky21.shapes_test;

import ru.acadevitschool.drozdetsky21.shapes.*;

import java.util.Arrays;
import java.util.Comparator;

public class Main {
    private static Shape getMaxArea(Shape[] shapes) {
        if (shapes == null || shapes.length == 0) {
            throw new IllegalArgumentException("Shape[] должен иметь минимум один элемент.");
        }

        Arrays.sort(shapes);

        return shapes[shapes.length - 1];
    }

    private static Shape getSecondPerimeter(Shape[] shapes) {
        if (shapes == null || shapes.length <= 1) {
            throw new IllegalArgumentException("Shape[] должен иметь минимум два элемента.");
        }

        Comparator<Shape> comparator = (shape1, shape2) -> {
            Double shapeOnePerimeter = shape1.getPerimeter();

            return shapeOnePerimeter.compareTo(shape2.getPerimeter());
        };

        Arrays.sort(shapes, comparator);

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

        Shape maxAreaShape = getMaxArea(shapes);
        System.out.printf("Фигурой с максимальной площадью = %.4f, является %s%n", maxAreaShape.getArea(), maxAreaShape);

        Shape secondPerimeterShape = getSecondPerimeter(shapes);
        System.out.printf("Фигурой со вторым по величине периметром = %.4f, является %s%n", secondPerimeterShape.getPerimeter(), secondPerimeterShape);
    }
}
