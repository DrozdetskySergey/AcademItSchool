package ru.academitschool.drozdetsky21.range_test;

import ru.academitschool.drozdetsky21.range.Range;

import java.util.Arrays;

public class Main {
    private static void printMessageAboutRangeWithNumber(Range range, double number) {
        String message = range.isInside(number) ? "входит в диапазон" : "за пределами диапазона";
        System.out.printf("Диапазон от %.4f до %.4f, его длина %.4f, число %.4f %s%n", range.getFrom(), range.getTo(), range.getLength(), number, message);
    }

    public static void main(String[] args) {
        Range range1 = new Range(1, 5.7);

        printMessageAboutRangeWithNumber(range1, 0);

        range1.setFrom(-3.3333);
        range1.setTo(21.3333);

        printMessageAboutRangeWithNumber(range1, 1);

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

        System.out.println();

        Range[] rangesDifference1 = range1.getDifference(range3);
        System.out.println(range1 + " вычитаем " + range3 + " = " + Arrays.toString(rangesDifference1));

        Range[] rangesDifference2 = range1.getDifference(range4);
        System.out.println(range1 + " вычитаем " + range4 + " = " + Arrays.toString(rangesDifference2));

        Range range6 = new Range(0, 3.3333);
        Range[] rangesDifference3 = range1.getDifference(range6);
        System.out.println(range1 + " вычитаем " + range6 + " = " + Arrays.toString(rangesDifference3));
    }
}
