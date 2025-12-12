package aoc.day12;

import aoc.common.AbstractProblem;
import aoc.common.Tuple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem12 extends AbstractProblem {
    public Problem12() {
        super(12);
    }

    @Override
    public String[] solve() throws IOException {
        var input = parseInput();

        var part1 = solvePart1(input.x, input.y);

        return new String[]{ String.valueOf(part1), "Happy Advent of Code 2025!" };
    }

    private int solvePart1(List<Integer> gifts, List<Region> regions){
        var total = 0;
        for(var region : regions) {
            if(fitsAll(gifts, region)) total++;
        }
        return total;
    }

    private boolean fitsAll(List<Integer> gifts, Region region){
        var regionSize = region.width() * region.height();

        var allGiftsSize = 0;
        for(var i=0;i<region.presents().length;i++){
            var number = region.presents()[i];
            var size = gifts.get(i);
            allGiftsSize += size * number;
        }

        return allGiftsSize < regionSize;
    }

    private Tuple<List<Integer>, List<Region>> parseInput() throws IOException {
        var input = this.read("input.txt");
        var parts = input.split(System.lineSeparator() + System.lineSeparator());

        List<Integer> presents = new ArrayList<>();
        for(var i=0;i<parts.length-1;i++){
            var presentStr = parts[i].split(System.lineSeparator());
            var total = 0;

            for(var j=1;j<presentStr.length;j++){
                for(var z=0;z<presentStr[j].length();z++){
                    if(presentStr[j].charAt(z) == '#') total++;
                }
            }

            presents.add(total);
        }

        List<Region> parsedRegions = new ArrayList<>();
        var regions = parts[parts.length - 1].split(System.lineSeparator());
        for(var region : regions){
            var subParts = region.split(": ");
            var sizing = subParts[0].split("x");
            var neededPresents = subParts[1].split(" ");
            parsedRegions.add(new Region(Integer.parseInt(sizing[0]), Integer.parseInt(sizing[1]), Arrays.stream(neededPresents).map(Integer::parseInt).toArray(Integer[]::new)));
        }

        return new Tuple<>(presents, parsedRegions);
    }

}
