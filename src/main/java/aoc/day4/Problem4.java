package aoc.day4;

import aoc.common.AbstractProblem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Problem4 extends AbstractProblem {
    final int LESS_THAN = 4;

    public Problem4() {
        super(4);
    }

    @Override
    public String[] solve() throws IOException {
        String[] lines = this.getInput();

        var part1 = solvePart1(lines);
        var part2 = solvePart2(lines);

        return new String[]{ String.valueOf(part1), String.valueOf(part2) };
    }

    private int solvePart1(String[] lines){
        int total = 0;

        for(int i=0;i<lines.length;i++){
            for(int j=0;j<lines[i].length();j++){
                if(lines[i].charAt(j) == '.') continue;
                int adjacentElements = countAdjacent(lines, i,j );
                if(adjacentElements < LESS_THAN) total++;
            }
        }

        return total;
    }

    private int solvePart2(String[] lines){
        String[] newBoard = lines.clone();
        List<int[]> toRemove;
        int total = 0;

        do{
            toRemove = new ArrayList<>();

            for(int i=0;i<newBoard.length;i++){
                for(int j=0;j<newBoard[i].length();j++){
                    if(newBoard[i].charAt(j) == '.') continue;
                    int adjacentElements = countAdjacent(newBoard, i,j );
                    if(adjacentElements < LESS_THAN) toRemove.add(new int[]{ i, j });
                }
            }

            total += toRemove.size();
            for(var position : toRemove) {
                StringBuilder sb = new StringBuilder(newBoard[position[0]]);
                sb.setCharAt(position[1], '.');
                newBoard[position[0]] = sb.toString();
            }

        }while(!toRemove.isEmpty());

        return total;
    }

    private int countAdjacent(String[] lines, int i, int j){
        int total = 0;

        total += getValue(lines, i - 1, j - 1);
        total += getValue(lines, i - 1, j);
        total += getValue(lines, i - 1, j + 1);
        total += getValue(lines, i, j - 1);
        total += getValue(lines, i, j + 1);
        total += getValue(lines, i + 1, j - 1);
        total += getValue(lines, i + 1, j);
        total += getValue(lines, i + 1, j + 1);

        return total;
    }

    int getValue(String[] lines, int i, int j) {
        if(i < 0) return 0;
        if(j < 0) return 0;
        if(i > lines.length - 1) return 0;
        if(j > lines.length - 1) return 0;
        char elem = lines[i].charAt(j);
        return elem == '.' ? 0 : 1;
    }

    public String[] getInput() throws IOException {
        return this.readLines("input.txt");
    }
}
