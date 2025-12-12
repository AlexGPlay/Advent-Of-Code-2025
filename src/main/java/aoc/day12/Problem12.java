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

    private int solvePart1(List<boolean[][]> gifts, List<Region> regions){
        var giftsSizes = getSizes(gifts);
        var total = 0;
        for(var region : regions) {
            if(fitsAll(giftsSizes, region)) total++;
        }
        return total;
    }

    private boolean fitsAll(int[] gifts, Region region){
        var regionSize = region.width() * region.height();

        var allGiftsSize = 0;
        for(var i=0;i<region.presents().length;i++){
            var number = region.presents()[i];
            var size = gifts[i];
            allGiftsSize += size * number;
        }

        return allGiftsSize < regionSize;
    }

    private int[] getSizes(List<boolean[][]> gifts){
        var sizes = new int[gifts.size()];
        for(var i=0;i<gifts.size();i++){
            var size = 0;
            var gift = gifts.get(i);

            for (boolean[] booleans : gift) {
                for (boolean aBoolean : booleans) {
                    if (aBoolean) size++;
                }
            }
            sizes[i] = size;
        }
        return sizes;
    }

    private Tuple<List<boolean[][]>, List<Region>> parseInput() throws IOException {
        var input = this.read("input.txt");
        var parts = input.split(System.lineSeparator() + System.lineSeparator());

        List<boolean[][]> presents = new ArrayList<>();
        for(var i=0;i<parts.length-1;i++){
            boolean[][] presentSize = new boolean[3][3];
            var presentStr = parts[i].split(System.lineSeparator());

            for(var j=1;j<presentStr.length;j++){
                for(var z=0;z<presentStr[j].length();z++){
                    presentSize[j-1][z] = presentStr[j].charAt(z) == '#';
                }
            }

            presents.add(presentSize);
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
