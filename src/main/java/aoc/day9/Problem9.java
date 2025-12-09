package aoc.day9;

import aoc.common.AbstractProblem;

import java.io.IOException;
import java.util.*;

public class Problem9 extends AbstractProblem {
    public Problem9() {
        super(9);
    }

    @Override
    public String[] solve() throws IOException {
        var points = getInput();
        return solveParts(points);
    }

    private String[] solveParts(List<int[]> points){
        // Part 1 + prepare the priority queue for part 2
        long biggestArea = 0;
        Queue<PointsWithSize> pairs = new PriorityQueue<>();

        for(int i=0;i<points.size();i++){
            long x1 = points.get(i)[0];
            long y1 = points.get(i)[1];

            for(int j=i+1;j<points.size();j++){
                long dx = Math.abs(points.get(j)[0] - x1) + 1;
                long dy = Math.abs(points.get(j)[1] - y1) + 1;
                long area = dx*dy;
                pairs.add(new PointsWithSize(i, j, area));
                if(area > biggestArea) biggestArea = area;
            }
        }

        // Part 2
        var shapeLines = buildShapeLines(points);
        var part2Biggest = getBiggestNonIntersectingRectangle(points, pairs, shapeLines);

        return new String[]{ String.valueOf(biggestArea), String.valueOf(part2Biggest) };
    }

    private long getBiggestNonIntersectingRectangle(List<int[]> points, Queue<PointsWithSize> queue, List<Line> shapeLines){
        while(!queue.isEmpty()) {
            var data = queue.poll();

            var point1 = points.get(data.point1());
            var x1 = point1[0];
            var y1 = point1[1];

            var point2 = points.get(data.point2());
            var x2 = point2[0];
            var y2 = point2[1];

            var minX = Math.min(x1, x2);
            var maxX = Math.max(x1, x2);
            var minY = Math.min(y1, y2);
            var maxY = Math.max(y1, y2);

            if(intersectsWithShape(minX, maxX, minY, maxY, shapeLines)) continue;
            return data.size();
        }

        return 0;
    }

    private List<Line> buildShapeLines(List<int[]> points) {
        List<Line> lines = new ArrayList<>();

        for (int i = 0; i < points.size() - 1; i++) {
            var start = points.get(i);
            var end = points.get(i + 1);
            lines.add(new Line(start[0], start[1], end[0], end[1]));
        }
        var lastPoint = points.getLast();
        var firstPoint = points.getFirst();
        lines.add(new Line(lastPoint[0], lastPoint[1], firstPoint[0], firstPoint[1]));

        return lines;
    }

    private boolean intersectsWithShape(int minX, int maxX, int minY, int maxY, List<Line> shapeLines){
        for (Line shapeLine : shapeLines) {
            if(shapeLine.intersects(minX, maxX, minY, maxY)) return true;
        }

        return false;
    }

    private List<int[]> getInput() throws IOException {
        List<int[]> points = new ArrayList<>();

        var lines = this.readLines("input.txt");
        for (var line : lines){
            var components = line.split(",");
            points.add(new int[]{ Integer.parseInt(components[0]), Integer.parseInt(components[1]) });
        }

        return points;
    }
}
