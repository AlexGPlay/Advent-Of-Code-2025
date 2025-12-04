package aoc;

import aoc.common.AbstractProblem;
import aoc.day1.Problem1;
import aoc.day2.Problem2;
import aoc.day3.Problem3;
import aoc.day4.Problem4;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<AbstractProblem> problems = new ArrayList<>();

        problems.add(new Problem1());
        problems.add(new Problem2());
        problems.add(new Problem3());
        problems.add(new Problem4());

        long fullStartTime = System.currentTimeMillis();

        for(var problem : problems){
            System.out.printf("Solving %s\n", problem);

            long startTime = System.currentTimeMillis();
            var solution = problem.solve();
            long endTime = System.currentTimeMillis();

            long duration = (endTime - startTime);

            StringBuilder sb = new StringBuilder();
            if(solution.length > 0) sb.append(String.format("Part 1: %s", solution[0]));
            if(solution.length > 1) sb.append(String.format(" || Part 2: %s", solution[1]));

            System.out.print(sb);
            System.out.printf("\nSolved in %dms\n", duration);
            System.out.println("=====================");
            System.out.println("=====================");
        }

        long fullEndTime = System.currentTimeMillis();
        long fullDuration = (fullEndTime - fullStartTime);
        System.out.printf("\nSolved everything in %dms\n", fullDuration);
    }
}