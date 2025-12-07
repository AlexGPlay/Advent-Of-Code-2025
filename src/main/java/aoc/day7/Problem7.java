package aoc.day7;

import aoc.common.AbstractProblem;

import java.io.IOException;
import java.util.*;

public class Problem7 extends AbstractProblem {
    public Problem7() {
        super(7);
    }

    @Override
    public String[] solve() throws IOException {
        var input = parseInput();

        var part1 = solvePart1(input);
        var part2 = solvePart2(input);

        return new String[]{ String.valueOf(part1), String.valueOf(part2) };
    }

    // Part 1
    private int solvePart1(String[] input){
        Set<String> visited = new HashSet<>();
        Set<String> startingPoints = new HashSet<>();

        Queue<int[]> queue = new ArrayDeque<>();
        int startingPoint = input[0].indexOf("S");
        queue.add(new int[]{ 0, startingPoint });

        while(!queue.isEmpty()){
            var position = queue.poll();
            var posString = arrayToString(position);
            if(visited.contains(posString)) continue;

            visited.add(posString);

            while(true){
                var newPosition = new int[]{ position[0] + 1, position[1] };
                if(newPosition[0] >= input.length) break;

                var newPosString = arrayToString(newPosition);
                if(visited.contains(newPosString)) break;
                visited.add(newPosString);

                var c = input[newPosition[0]].charAt(newPosition[1]);
                if (c == '^') {
                    startingPoints.add(newPosString);
                    var leftPosition = new int[]{ newPosition[0], newPosition[1] - 1 };
                    var leftPositionStr = arrayToString(leftPosition);
                    if(!visited.contains(leftPositionStr)) {
                        queue.add(leftPosition);
                    }

                    var rightPosition = new int[]{ newPosition[0], newPosition[1] + 1 };
                    var rightPositionStr = arrayToString(rightPosition);
                    if(!visited.contains(rightPositionStr)) {
                        queue.add(rightPosition);
                    }
                    break;
                }
                position = newPosition;
            }

        }

        return startingPoints.size();
    }

    // Part 2
    private long solvePart2(String[] input){
        int startingPoint = input[0].indexOf("S");
        Map<String, Long> options = new HashMap<>();
        return calculateParticleOptions(input, new int[]{0, startingPoint}, options);
    }

    private long calculateParticleOptions(String[] input, int[] position, Map<String, Long> options){
        var positionKey = arrayToString(position);
        if(options.containsKey(positionKey)) return options.get(positionKey);

        var newPosition = new int[]{ position[0] + 1, position[1] };
        if(newPosition[0] >= input.length) return 1;

        char c = input[newPosition[0]].charAt(newPosition[1]);
        long total = 0;
        if(c == '^') {
            total += calculateParticleOptions(input, new int[]{ newPosition[0], newPosition[1] - 1 }, options);
            total += calculateParticleOptions(input, new int[]{ newPosition[0], newPosition[1] + 1 }, options);
        } else {
            total += calculateParticleOptions(input, newPosition, options);
        }

        options.put(positionKey, total);
        return total;
    }

    // Common utils
    private String arrayToString(int[] a){
        return String.valueOf(a[0]) + ',' + String.valueOf(a[1]);
    }

    private String[] parseInput() throws IOException {
        return this.readLines("input.txt");
    }
}
