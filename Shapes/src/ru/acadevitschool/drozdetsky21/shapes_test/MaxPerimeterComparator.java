package ru.acadevitschool.drozdetsky21.shapes_test;

import ru.acadevitschool.drozdetsky21.shapes.Shape;

import java.util.Comparator;

public class MaxPerimeterComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape o1, Shape o2) {
        return Double.compare(o1.getPerimeter(), o2.getPerimeter());
    }
}
