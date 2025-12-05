package aoc.day5;

import aoc.common.AbstractProblem;
import aoc.common.Tuple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Problem5 extends AbstractProblem {
    public Problem5() {
        super(5);
    }

    @Override
    public String[] solve() throws IOException {
        var input = parseInput();

        int part1 = solvePart1(input.x, input.y);
        long part2 = solvePart2(input.x);

        return new String[]{ String.valueOf(part1), String.valueOf(part2) };
    }

    private int solvePart1(List<Interval> intervals, List<Long> ids){
        int total = 0;

        for(var id : ids){
            for(var interval : intervals){
                if(interval.contains(id)){
                    total++;
                    break;
                }
            }
        }

        return total;
    }

    private long solvePart2(List<Interval> intervals){
        long total = 0;

        for(var interval : intervals){
            total += interval.length();
        }

        return total;
    }

    private void mergeIntervals(List<Interval> currentIntervals, Interval newInterval){
        Interval current = newInterval;
        for (int i = 0; i < currentIntervals.size(); i++){
            Interval interval = currentIntervals.get(i);
            if (interval.intersects(current)){
                current = current.combine(interval);
                currentIntervals.remove(i);
                i--;
            }
        }
        currentIntervals.add(current);
    }

    private Tuple<List<Interval>, List<Long>> parseInput() throws IOException {
        String input = this.read("input.txt");

        List<Interval> parsedRanges = new ArrayList<>();
        List<Long> parsedIds = new ArrayList<>();

        String[] blocks = input.split("\\R\\R");
        String[] ranges = blocks[0].split("\\R");
        String[] ids = blocks[1].split("\\R");

        for(var range : ranges){
            String[] parts = range.split("-");
            long lower = Long.parseLong(parts[0]);
            long upper = Long.parseLong(parts[1]);
            Interval newInterval = new Interval(lower, upper);
            mergeIntervals(parsedRanges, newInterval);
        }

        for(var id : ids) parsedIds.add(Long.parseLong(id));

        return new Tuple<>(parsedRanges, parsedIds);
    }
}
