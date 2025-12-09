package aoc.day9;

public record PointsWithSize(int point1, int point2, long size) implements Comparable<PointsWithSize> {
    @Override
    public int compareTo(PointsWithSize o) {
        return -Double.compare(this.size, o.size);
    }
}
