package aoc.day8;

public class Point {
    public final  long x;
    public final long y;
    public final long z;

    public Point(long x, long y, long z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double distanceTo(Point other) {
        double dx = x - other.x;
        double dy = y - other.y;
        double dz = z - other.z;
        return Math.sqrt(dx*dx + dy*dy + dz*dz);
    }

    @Override
    public String toString() {
        return String.format("%d,%d,%d", this.x, this.y, this.z);
    }
}
