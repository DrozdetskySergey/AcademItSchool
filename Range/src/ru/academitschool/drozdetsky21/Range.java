package ru.academitschool.drozdetsky21;

import java.util.Arrays;

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

    @Override
    public String toString() {
        return String.format("[%.4f, %.4f]", from, to);
    }

    public double getLength() {
        return to - from;
    }

    public boolean isInside(double number) {
        return from <= number && number <= to;
    }

    public Range getIntersection(Range range) {
        if (to <= range.from || from >= range.to) {
            return null;
        }

        if (from <= range.from) {
            if (to <= range.to) {
                return new Range(range.from, to);
            }

            return range;
        }

        if (to > range.to) {
            return new Range(from, range.to);
        }

        return this;
    }

    public Range[] getUnion(Range range) {
        if (to < range.from || from > range.to) {
            Range[] result = new Range[2];

            if (to < range.from) {
                result[0] = this;
                result[1] = range;
            } else {
                result[0] = range;
                result[1] = this;
            }

            return result;
        }

        Range[] result = new Range[1];

        if (from <= range.from) {
            if (to <= range.to) {
                result[0] = new Range(from, range.to);
            } else {
                result[0] = this;
            }
        } else {
            if (to > range.to) {
                result[0] = new Range(range.from, to);
            } else {
                result[0] = range;
            }
        }

        return result;
    }

    private static void displayMessageAboutRangeWithNumber(Range range, double number) {
        double rangeStart = range.getFrom();
        double rangeEnd = range.getTo();
        double rangeLength = range.getLength();
        String message = range.isInside(number) ? "входит в диапазон" : "за пределами диапазона";

        System.out.printf("Диапазон от %.4f до %.4f, его длина %.4f, число %.4f %s%n", rangeStart, rangeEnd, rangeLength, number, message);
    }

    public static void main(String[] args) {
        Range range1 = new Range(1, 5.7);

        displayMessageAboutRangeWithNumber(range1, 0);

        range1.setFrom(-3.3333);
        range1.setTo(21.3333);

        displayMessageAboutRangeWithNumber(range1, 1);

        System.out.println();

        Range range2 = new Range(0, 35.35);
        Range range3 = new Range(-4.9, 10);

        Range rangesIntersection = range1.getIntersection(range2).getIntersection(range3);
        System.out.println(range1 + " пересекаем с " + range2 + " пересекаем с " + range3 + " = " + rangesIntersection);

        System.out.println();

        Range[] rangesUnion1 = range1.getUnion(range2);
        System.out.println(range1 + " объединяем с " + range2 + " = " + Arrays.toString(rangesUnion1));

        Range range4 = new Range(21.3333, 30.5);
        Range[] rangesUnion2 = range1.getUnion(range4);
        System.out.println(range1 + " объединяем с " + range4 + " = " + Arrays.toString(rangesUnion2));

        Range range5 = new Range(28.8, 30.5);
        Range[] rangesUnion3 = range1.getUnion(range5);
        System.out.println(range1 + " объединяем с " + range5 + " = " + Arrays.toString(rangesUnion3));
    }
}
