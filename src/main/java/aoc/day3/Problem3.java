package aoc.day3;

import aoc.common.AbstractProblem;

import java.io.IOException;

public class Problem3 extends AbstractProblem {
    public Problem3() {
        super(3);
    }

    @Override
    public void solve() throws IOException {
        var lines = this.readLines("input.txt");
        var part1 = solvePart(lines, 2);
        var part2 = solvePart(lines, 12);

        System.out.printf("Part 1: %d || Part 2: %d", part1, part2);
    }

    private long solvePart(String[] lines, int length){
        long total = 0;

        for(var line : lines){
            var fullNumber = getNumberWithLength(line, length);
            total += fullNumber;
        }
        return total;
    }

    private long getNumberWithLength(String line, int length){
        long currentNumber = 0;
        var startFrom = 0;

        for(var i=length;i>0;i--){
            var newPart = getBiggestNumberInRange(line, startFrom, line.length() - i);
            startFrom = newPart[1] + 1;
            currentNumber = currentNumber*10 + newPart[0];
        }

        return currentNumber;
    }

    private int[] getBiggestNumberInRange(String line, int from, int to){
        var biggestNumber = 0;
        var foundIn = 0;

        for(var i=from;i<=to;i++){
            var newNumber = line.charAt(i) - '0';
            if(newNumber > biggestNumber) {
                biggestNumber = newNumber;
                foundIn = i;
                if(biggestNumber == 9) break;
            }
        }
        return new int[]{ biggestNumber, foundIn };
    }
}
