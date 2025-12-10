package aoc.day10;

import aoc.common.AbstractProblem;

import java.io.IOException;
import java.util.*;

import com.microsoft.z3.*;

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
        Map<Integer, List<IntExpr>> buttonMapping = new HashMap<>();
        List<IntExpr> variables = new ArrayList<>();
        var context = new Context();
        var opt = context.mkOptimize();

        for(int i=0;i<machine.buttons().size();i++){
            var buttonVariable = "v_" + (char)(97 + i);
            var ctxVariable = context.mkIntConst(buttonVariable);
            variables.add(ctxVariable);
            opt.Add(context.mkGe(ctxVariable, context.mkInt(0)));

            for(var position : machine.buttons().get(i)){
                List<IntExpr> list = new ArrayList<>();
                if(buttonMapping.containsKey(position)) list = buttonMapping.get(position);
                list.add(ctxVariable);
                buttonMapping.put(position, list);
            }
        }

        for(var entry : buttonMapping.entrySet()){
            var sum = context.mkAdd(entry.getValue().toArray(IntExpr[]::new));
            var joltageValue = machine.joltageRequirements()[entry.getKey()];
            opt.Add(context.mkEq(sum, context.mkInt(joltageValue)));
        }

        var buttonVariablesArray = variables.toArray(IntExpr[]::new);
        var totalPresses = context.mkAdd(buttonVariablesArray);
        opt.MkMinimize(totalPresses);

        if (opt.Check() != Status.SATISFIABLE) {
            throw new RuntimeException("No solution exists for this machine!");
        }

        Model model = opt.getModel();
        var output = (IntNum) model.evaluate(totalPresses, false);

        return output.getInt();
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
