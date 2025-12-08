package aoc.day8;

import aoc.common.AbstractProblem;

import java.io.IOException;
import java.util.*;

public class Problem8 extends AbstractProblem {
    int CONNECTIONS_TO_MAKE = 1000;

    public Problem8() {
        super(8);
    }

    @Override
    public String[] solve() throws IOException {
        var points = readFile();
        return solveParts(points);
    }

    private String[] solveParts(List<Point> points){
        Map<Point, Set<Point>> belongsTo = new HashMap<>();
        int currentGroups = points.size();

        for(var point : points){
            var pointGroup = new HashSet<Point>();
            pointGroup.add(point);
            belongsTo.put(point, pointGroup);
        }

        Queue<PointsWithDistance> closest = new PriorityQueue<>();
        for(int i = 0; i < points.size(); i++){
            for(int j = i+1; j < points.size(); j++){
                closest.add(new PointsWithDistance(points.get(i), points.get(j), points.get(i).distanceTo(points.get(j))));
            }
        }

        int builtConnections = 0;
        while(builtConnections < CONNECTIONS_TO_MAKE) {
            builtConnections++;

            var closestElem = closest.poll();
            var point1 = closestElem.point1();
            var point2 = closestElem.point2();

            var group1 = belongsTo.get(point1);
            var group2 = belongsTo.get(point2);

            // If they are in the same group we just skip it
            if(group1.equals(group2)) continue;

            // Otherwise we merge the groups
            currentGroups--;
            Set<Point> mergedGroup = new HashSet<>();
            mergedGroup.addAll(group1);
            mergedGroup.addAll(group2);

            for (var p : mergedGroup) belongsTo.put(p, mergedGroup);
        }
        // At this point the ITERATIONS for part 1 par finished so we can store the value for it
        int part1 = getBiggestGroupsTotal(belongsTo);

        // Now we have to continue with the same logic until we get a big circuit for part 2
        long part2;
        while(true) {
            var closestElem = closest.poll();
            var point1 = closestElem.point1();
            var point2 = closestElem.point2();

            var group1 = belongsTo.get(point1);
            var group2 = belongsTo.get(point2);

            // If they are in the same group we just skip it
            if(group1.equals(group2)) continue;

            // Otherwise we merge the groups
            currentGroups--;
            group1.addAll(group2);
            for (var p : group2) belongsTo.put(p, group1);

            if(currentGroups == 1) {
                part2 = point1.x * point2.x;
                break;
            }
        }

        return new String[]{ String.valueOf(part1), String.valueOf(part2) };
    }

    private int getBiggestGroupsTotal(Map<Point, Set<Point>> groups){
        Set<Set<Point>> allGroups = new HashSet<>(groups.values());

        var sortedGroups = allGroups.stream().sorted((a, b) -> b.size() - a.size()).toList();
        return sortedGroups.get(0).size() * sortedGroups.get(1).size() * sortedGroups.get(2).size();
    }

    private List<Point> readFile() throws IOException {
        var lines = this.readLines("input.txt");
        var points = new ArrayList<Point>();

        for (String line : lines) {
            var components = line.split(",");
            var point = new Point(Long.parseLong(components[0]), Long.parseLong(components[1]), Long.parseLong(components[2]));
            points.add(point);
        }

        return points;
    }

}
