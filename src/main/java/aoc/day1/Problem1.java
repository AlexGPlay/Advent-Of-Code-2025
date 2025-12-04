package aoc.day1;

import aoc.common.AbstractProblem;

import java.io.IOException;

public class Problem1 extends AbstractProblem {

    public Problem1() {
        super(1);
    }

    @Override
    public String[] solve() throws IOException {
        var input = this.readLines("input.txt");
        return this.solveParts(input);
    }

    private String[] solveParts(String[] lines){
        var start = 50;
        var zeroStops = 0;
        var passesByZero = 0;

        for (var line : lines){
            int change = getRotation(line);
            int newStartValue = start + change;

            if(newStartValue >= 100) {
                passesByZero += newStartValue / 100;
            }
            else if(newStartValue <= 0){
                passesByZero += (Math.abs(newStartValue) / 100) + 1;
                if(start == 0) passesByZero--;
            }
            start = ((newStartValue % 100) + 100) % 100;

            if(start == 0) zeroStops++;
        }

        return new String[]{ String.valueOf(zeroStops), String.valueOf(passesByZero) };
    }

    private int getRotation(String line){
        int multiplier = 1;
        if(line.startsWith("L")) multiplier = -1;

        int quantity = Integer.parseInt(line.substring(1));
        return quantity * multiplier;
    }

}
