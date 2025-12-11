package aoc.day11;

import aoc.common.AbstractProblem;

import java.io.IOException;
import java.util.*;

public class Problem11 extends AbstractProblem {
    public Problem11() {
        super(11);
    }

    @Override
    public String[] solve() throws IOException {
        var map = parseInput();

        var part1 = solvePart1(map);
        var part2 = solvePart2(map);

        return new String[]{ String.valueOf(part1), String.valueOf(part2) };
    }

    private int solvePart1(Map<String, String[]> map){
        return dfsForPart1("you", map, new HashMap<>());
    }

    private int dfsForPart1(String node, Map<String, String[]> map, Map<String, Integer> dp){
        if(node.equals("out")) return 1;
        if(!map.containsKey(node)) return 0;
        if(dp.containsKey(node)) return dp.get(node);

        int total = 0;
        for(var next : map.get(node)) total += dfsForPart1(next, map, dp);
        dp.put(node, total);
        return total;
    }

    private long solvePart2(Map<String, String[]> map){
        return dfsForPart2("svr", false, false, map, new HashMap<>());
    }

    private long dfsForPart2(String node, boolean includesFft, boolean includesDac, Map<String, String[]> map, Map<String, Long> dp){
        if(node.equals("out")) return includesFft && includesDac ? 1 : 0;
        if(!map.containsKey(node)) return 0;

        var dpKey = node + "," + includesFft + "," + includesDac;
        if(dp.containsKey(dpKey)) return dp.get(dpKey);

        long total = 0;
        for(var next : map.get(node)) total += dfsForPart2(next, includesFft || node.equals("fft"), includesDac || node.equals("dac"), map, dp);
        dp.put(dpKey, total);
        return total;
    }

    private Map<String, String[]> parseInput() throws IOException {
        var lines = this.readLines("input.txt");
        var map = new HashMap<String, String[]>();

        for(var line : lines){
            var mainParts = line.split(": ");
            var goTo = mainParts[1].split(" ");
            map.put(mainParts[0], goTo);
        }

        return map;
    }

}
