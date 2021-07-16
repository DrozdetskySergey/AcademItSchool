package ru.academitschool.drozdetsky21;

public class Range {
    private double from;
    private double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public Range(Range range) {
        from = range.from;
        to = range.to;
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
        return String.format("(%.4f; %.4f)", from, to);
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

            return new Range(range);
        }

        if (to > range.to) {
            return new Range(from, range.to);
        }

        return new Range(this);
    }

    public Range[] getUnion(Range range) {
        if (to < range.from || from > range.to) {
            if (to < range.from) {
                return new Range[]{new Range(this), new Range(range)};
            }

            return new Range[]{new Range(range), new Range(this)};
        }

        if (from <= range.from) {
            if (to <= range.to) {
                return new Range[]{new Range(from, range.to)};
            }

            return new Range[]{new Range(this)};
        }

        if (to > range.to) {
            return new Range[]{new Range(range.from, to)};
        }

        return new Range[]{new Range(range)};
    }

    public Range[] getDifference(Range range) {
        if (from >= range.from && to <= range.to) {
            return new Range[0];
        }

        if (to <= range.from || from >= range.to) {
            return new Range[]{new Range(this)};
        }

        if (from >= range.from) {
            return new Range[]{new Range(range.to, to)};
        }

        if (to <= range.to) {
            return new Range[]{new Range(from, range.from)};
        }

        return new Range[]{new Range(from, range.from), new Range(range.to, to)};
    }
}
