package aoc.day8;

public record PointsWithDistance(Point point1, Point point2, double distance) implements Comparable<PointsWithDistance> {
    @Override
    public int compareTo(PointsWithDistance o) {
        return Double.compare(this.distance, o.distance);
    }

    @Override
    public String toString() {
        return String.format("%s -> %s (%f)", point1, point2, distance);
    }
}
