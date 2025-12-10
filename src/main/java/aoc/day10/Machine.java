package aoc.day10;

import java.util.List;

public record Machine(int goalStatus, List<int[]> buttons, List<Integer> maskedButtons, int[] joltageRequirements) {
}
