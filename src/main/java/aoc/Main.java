package aoc;

import aoc.common.AbstractProblem;
import aoc.day1.Problem1;
import aoc.day2.Problem2;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<AbstractProblem> problems = new ArrayList<>();

        problems.add(new Problem1());
        problems.add(new Problem2());

        long fullStartTime = System.currentTimeMillis();

        for(var problem : problems){
            System.out.printf("Solving %s\n", problem);

            long startTime = System.currentTimeMillis();
            problem.solve();
            long endTime = System.currentTimeMillis();

            long duration = (endTime - startTime);
            System.out.printf("\nSolved in %dms\n", duration);
            System.out.println("=====================");
            System.out.println("=====================");
        }

        long fullEndTime = System.currentTimeMillis();
        long fullDuration = (fullEndTime - fullStartTime);
        System.out.printf("\nSolved everything in %dms\n", fullDuration);
    }
}