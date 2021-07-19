package ru.acadevitschool.drozdetsky21.shapes_test;

import ru.acadevitschool.drozdetsky21.shapes.Shape;

import java.util.Comparator;

public class MaxPerimeterShapeComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape shape1, Shape shape2) {
        Double shapeOnePerimeter = shape1.getPerimeter();

        return shapeOnePerimeter.compareTo(shape2.getPerimeter());
    }
}
