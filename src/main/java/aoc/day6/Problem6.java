package aoc.day6;

import aoc.common.AbstractProblem;
import aoc.common.Tuple;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.LongBinaryOperator;
import java.util.stream.IntStream;

public class Problem6 extends AbstractProblem {


    public Problem6() {
        super(6);
    }

    @Override
    public String[] solve() throws IOException {
        var input = parseInput();

        var part1 = solvePart1(input.x, input.y);
        var part2 = solvePart2(input.x, input.y);

        return new String[]{ String.valueOf(part1), String.valueOf(part2) };
    }

    // Part 1
    private long solvePart1(ArrayList<ArrayList<CephNumber>> lines, ArrayList<Operation> operations){
        return IntStream
                .range(0, lines.getFirst().size())
                .mapToLong(i -> solvePart1Column(i, lines, operations.get(i))).sum();
    }

    private long solvePart1Column(int idx, ArrayList<ArrayList<CephNumber>> lines, Operation operation){
        long lineTotal = operation.getInitialValue();
        for(var line : lines){
            var elem = line.get(idx);
            lineTotal = operation.apply(lineTotal, elem.parsedNumber);
        }
        return lineTotal;
    }

    // Part 2
    private long solvePart2(ArrayList<ArrayList<CephNumber>> lines, ArrayList<Operation> operations){
        return IntStream
                .range(0, lines.getFirst().size())
                .mapToLong(i -> solvePart2Column(i, lines, operations.get(i))).sum();
    }

    private long solvePart2Column(int idx, ArrayList<ArrayList<CephNumber>> lines, Operation operation){
        long lineTotal = operation.getInitialValue();

        var length = 0;
        for(var line : lines){
            var l = line.get(idx).originalNumber.length;
            if(l > length) length = l;
        }

        for(var i=0;i<length;i++){
            StringBuilder newN = new StringBuilder();
            for(var line : lines){
                var elem = line.get(idx);
                var toAdd = i >= elem.originalNumber.length ? ' ' : elem.originalNumber[i];
                newN.append(toAdd);
            }
            lineTotal = operation.apply(lineTotal, Long.parseLong(newN.toString().trim()));
        }

        return lineTotal;
    }

    // Common utils
    private Tuple<ArrayList<ArrayList<CephNumber>>, ArrayList<Operation>> parseInput() throws IOException {
        var lines = this.readLines("input.txt");

        var parsedOperations = new ArrayList<Operation>();
        var lastLine = lines[lines.length - 1];
        for(var i=0;i<lastLine.length();i++){
            var c = lastLine.charAt(i);
            if(c == ' ') continue;
            if(!parsedOperations.isEmpty()) parsedOperations.getLast().toPosition = i - 2;

            parsedOperations.add(new Operation(c, i, -1));
        }

        var parsedNumericLines = new ArrayList<ArrayList<CephNumber>>();
        for(int ll=0;ll<lines.length-1;ll++){
            var line = lines[ll];
            var parsedLine = new ArrayList<CephNumber>();

            for(Operation op : parsedOperations){
                String strNumber = line.substring(op.fromPosition, op.toPosition == -1 ? line.length() : op.toPosition + 1);
                var number = new CephNumber(strNumber);
                parsedLine.add(number);
            }

            parsedNumericLines.add(parsedLine);
        }

        return new Tuple<>(parsedNumericLines, parsedOperations);
    }

}
