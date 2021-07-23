package ru.acadevitschool.drozdetsky21.shapes;

public class Triangle implements Shape, Comparable<Shape> {
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private double x3;
    private double y3;

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public double getX3() {
        return x3;
    }

    public void setX3(double x3) {
        this.x3 = x3;
    }

    public double getY3() {
        return y3;
    }

    public void setY3(double y3) {
        this.y3 = y3;
    }

    private static double getSegmentLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    @Override
    public double getWidth() {
        return Math.max(x1, Math.max(x2, x3)) - Math.min(x1, Math.min(x2, x3));
    }

    @Override
    public double getHeight() {
        return Math.max(y1, Math.max(y2, y3)) - Math.min(y1, Math.min(y2, y3));
    }

    @Override
    public double getArea() {
        double sideLength1 = getSegmentLength(x1, y1, x2, y2);
        double sideLength2 = getSegmentLength(x1, y1, x3, y3);
        double sideLength3 = getSegmentLength(x2, y2, x3, y3);

        double semiPerimeter = (sideLength1 + sideLength2 + sideLength3) / 2;

        return Math.sqrt(semiPerimeter * (semiPerimeter - sideLength1) * (semiPerimeter - sideLength2) * (semiPerimeter - sideLength3));
    }

    @Override
    public double getPerimeter() {
        return getSegmentLength(x1, y1, x2, y2) + getSegmentLength(x1, y1, x3, y3) + getSegmentLength(x2, y2, x3, y3);
    }

    @Override
    public String toString() {
        return String.format("Triangle((%.4f; %.4f), (%.4f; %.4f), (%.4f; %.4f))", x1, y1, x2, y2, x3, y3);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Triangle triangle = (Triangle) o;

        return triangle.x1 == x1 && triangle.y1 == y1 && triangle.x2 == x2 && triangle.y2 == y2 && triangle.x3 == x3 && triangle.y3 == y3;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + Double.hashCode(x1);
        result = PRIME * result + Double.hashCode(y1);
        result = PRIME * result + Double.hashCode(x2);
        result = PRIME * result + Double.hashCode(y2);
        result = PRIME * result + Double.hashCode(x3);
        result = PRIME * result + Double.hashCode(y3);

        return result;
    }

    @Override
    public int compareTo(Shape o) {
        return Double.compare(getArea(), o.getArea());
    }
}
