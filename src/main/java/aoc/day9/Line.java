package aoc.day9;

public record Line(int x1, int y1, int x2, int y2) {
    public boolean intersects(int minX, int maxX, int minY, int maxY) {
        int segMinX = Math.min(this.x1, this.x2);
        int segMaxX = Math.max(this.x1, this.x2);
        int segMinY = Math.min(this.y1, this.y2);
        int segMaxY = Math.max(this.y1, this.y2);

        return segMaxX > minX && segMinX < maxX && segMaxY > minY && segMinY < maxY;
    }
}
