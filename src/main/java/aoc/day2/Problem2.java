package aoc.day2;

import aoc.common.AbstractProblem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Problem2 extends AbstractProblem {

    public Problem2() {
        super(2);
    }

    @Override
    public String[] solve() throws IOException {
        var limits = getInput();
        var part1 = solvePart1(limits);
        var part2 = solvePart2(limits);

        return new String[]{ String.valueOf(part1), String.valueOf(part2) };
    }

    // Part 1 + Helpers
    private long solvePart1(List<long[]> limits){
        long total = 0;
        for(var limit : limits){
            total += getInvalidIdsInRangeForPart1(limit);
        }
        return total;
    }

    private long getInvalidIdsInRangeForPart1(long[] limit){
        long lowerLimit = limit[0];
        long upperLimit = limit[1];

        long maxHalfLength = getNumberLength(upperLimit)/2;

        var currentHalf = getInitialHalf(lowerLimit);
        long currentHalfLength = getNumberLength(currentHalf);
        long total = 0;

        while(currentHalfLength <= maxHalfLength){
            long pow = (long) Math.pow(10, currentHalfLength);
            long duplicatedNumber = currentHalf * pow + currentHalf;
            if(duplicatedNumber < lowerLimit) {
                currentHalf++;
                currentHalfLength = getNumberLength(currentHalf);
                continue;
            }
            if(duplicatedNumber > upperLimit) break;
            total += duplicatedNumber;
            currentHalf++;
            currentHalfLength = getNumberLength(currentHalf);
        }

        return total;
    }

    private long getInitialHalf(long lowerLimit){
        long length = getNumberLength(lowerLimit);
        if(length % 2 == 0) {
            long half = length / 2;
            long pow = (long) Math.pow(10, half);
            return lowerLimit / pow;
        }

        long halfLen = length / 2;
        return (long) Math.pow(10, halfLen);
    }


    // Part 2 + Helpers
    private long solvePart2(List<long[]> limits){
        long total = 0;
        for(var limit : limits){
            total += getInvalidIdsInRangeForPart2(limit);
        }
        return total;
    }

    private long getInvalidIdsInRangeForPart2(long[] limit){
        Set<Long> alreadySummed = new HashSet<>();

        long lowerLimit = limit[0];
        long upperLimit = limit[1];

        long upperLimitLength = getNumberLength(upperLimit);
        long maxHalfLength = upperLimitLength/2;
        var currentNumber = 1;
        long currentNumberLength = 1;

        long total = 0;

        while(currentNumberLength <= maxHalfLength){
            for(var i=currentNumberLength*2;i<=upperLimitLength;i++){
                var newNumber = composeNumber(currentNumber, i);
                if(newNumber < lowerLimit) continue;
                if(newNumber > upperLimit) break;
                if(alreadySummed.contains(newNumber)) continue;
                alreadySummed.add(newNumber);
                total += newNumber;
            }

            currentNumber++;
            currentNumberLength = getNumberLength(currentNumber);
        }

        return total;
    }

    private long composeNumber(long base, long length){
        var seqLen = getNumberLength(base);

        long result = 0;
        for (int i = 0; i < length / seqLen; i++) {
            result = result * (long) Math.pow(10, seqLen) + base;
        }

        return result;
    }

    // Common helpers
    private long getNumberLength(long number){
        return (long) Math.log10(number) + 1;
    }

    private List<long[]> getInput() throws IOException {
        var data = this.read("input.txt");
        var limits = data.split(",");

        List<long[]> parsedLimits = new ArrayList<>();
        for(var limit : limits){
            var parts = limit.split("-");
            long part1 = Long.parseLong(parts[0]);
            long part2 = Long.parseLong(parts[1]);
            parsedLimits.add(new long[]{part1, part2});
        }
        return parsedLimits;
    }


}
