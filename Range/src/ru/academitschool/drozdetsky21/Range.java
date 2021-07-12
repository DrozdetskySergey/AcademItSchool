package ru.academitschool.drozdetsky21;

public class Range {
    private double from;
    private double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public double getLength() {
        return to - from;
    }

    public boolean isInside(double number) {
        return from <= number && number <= to;
    }

    private static void displayMessageAboutRangeWithNumber(Range range, double number) {
        double rangeStart = range.getFrom();
        double rangeEnd = range.getTo();
        double rangeLength = range.getLength();
        String message = range.isInside(number) ? "входит в диапазон" : "за пределами диапазона";

        System.out.printf("Диапазон от %.4f до %.4f, его длина %.4f, число %.4f %s%n", rangeStart, rangeEnd, rangeLength, number, message);
    }

    public static void main(String[] args) {
        Range range = new Range(1, 5.7);

        displayMessageAboutRangeWithNumber(range, 0);

        range.setFrom(-3.3333);
        range.setTo(21.3333);

        displayMessageAboutRangeWithNumber(range, 1);
    }
}
