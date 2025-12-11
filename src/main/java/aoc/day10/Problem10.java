package aoc.day10;

import aoc.common.AbstractProblem;

import java.io.IOException;
import java.util.*;

import org.ojalgo.optimisation.Expression;
import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.Variable;

public class Problem10 extends AbstractProblem {
    public Problem10() {
        super(10);
    }

    @Override
    public String[] solve() throws IOException {
        var machines = parseInput();

        var part1 = solvePart1(machines);
        var part2 = solvePart2(machines);

        return new String[]{ String.valueOf(part1), String.valueOf(part2) };
    }

    private int solvePart1(List<Machine> machines){
        return machines.stream().mapToInt(this::solveMachineGoal).sum();
    }

    private int solveMachineGoal(Machine machine){
        Queue<int[]> queue = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();

        // We are in the status 0 with 0 presses
        queue.add(new int[]{0, 0});
        visited.add(0);

        while(!queue.isEmpty()) {
            var current = queue.poll();
            int state = current[0];
            int presses = current[1];

            if(state == machine.goalStatus()) return presses;

            for(int button : machine.maskedButtons()){
                int next = state ^ button;
                if(visited.add(next)) queue.add(new int[]{ next, presses + 1 });
            }
        }

        return -1;
    }


    private int solvePart2(List<Machine> machines){
        return machines.stream().parallel().mapToInt(this::solveMachineJoltage).sum();
    }

    private int solveMachineJoltage(Machine machine){
        ExpressionsBasedModel model = new ExpressionsBasedModel();

        int buttons = machine.buttons().size();
        Variable[] vars = new Variable[buttons];

        for (int i = 0; i < buttons; i++) {
            vars[i] = model.addVariable("v" + i).lower(0).integer(true).weight(1);
        }

        for (int j = 0; j < machine.joltageRequirements().length; j++) {
            Expression expr = model.addExpression("c" + j).level(machine.joltageRequirements()[j]);

            for (int i = 0; i < buttons; i++) {
                for (int pos : machine.buttons().get(i)) {
                    if (pos == j) {
                        expr.set(vars[i], 1);
                    }
                }
            }
        }

        var result = model.minimise();
        return (int) Math.round(result.getValue());
    }


    private List<Machine> parseInput() throws IOException {
        var lines = this.readLines("input.txt");
        List<Machine> machines = new ArrayList<>();

        for(var line : lines){
            var components = line.split(" ");

            // Parse goalStatus
            var goal = components[0];
            int goalStatus = 0;
            for(var i=1;i<goal.length() - 1;i++){
                if (goal.charAt(i) == '#') goalStatus |= 1 << (i - 1);
            }

            // Parse buttons
            List<Integer> maskedButtons = new ArrayList<>();
            List<int[]> buttons = new ArrayList<>();
            for(var i=1;i<components.length-1;i++){
                var buttonsStr = components[i];
                var buttonComponents = parseNumbers(buttonsStr);

                int mask = 0;
                for(var button : buttonComponents) mask |= (1 << button);
                maskedButtons.add(mask);
                buttons.add(buttonComponents);
            }

            // Parse joltage
            int[] joltage = parseNumbers(components[components.length-1]);

            machines.add(new Machine(goalStatus, buttons, maskedButtons, joltage));
        }

        return machines;
    }

    private int[] parseNumbers(String group){
        var subStr = group.substring(1, group.length() - 1);
        var elements = subStr.split(",");

        var parsedElements = new int[elements.length];
        for(var i=0;i<parsedElements.length;i++) parsedElements[i] = Integer.parseInt(elements[i]);
        return parsedElements;
    }

}
