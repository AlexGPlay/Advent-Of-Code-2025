package aoc.day6;

import java.util.function.LongBinaryOperator;

public class Operation {

    char operation;
    int fromPosition;
    int toPosition;

    private final LongBinaryOperator operationFn;

    public Operation(char operation, int fromPosition, int toPosition){
        this.operation = operation;
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
        this.operationFn = operation == '+' ? Long::sum : (long currentTotal, long number) -> currentTotal * number;
    }

    public long apply(long total, long number){
        return this.operationFn.applyAsLong(total, number);
    }

    public long getInitialValue(){
        return this.operation == '+' ? 0 : 1;
    }

    public int getLength(){
        return toPosition - fromPosition;
    }

}
