package aoc.common;

import java.io.IOException;

public abstract class AbstractProblem {

    private final int day;

    public AbstractProblem(int day){
        this.day = day;
    }

    public abstract String[] solve() throws IOException;

    public String toString(){
        return String.format("AOC 2025 - Day %d", this.day);
    }

    public String read(String filename) throws IOException {
        String path = String.format("/aoc/day%d/%s", this.day, filename);
        var is = this.getClass().getResourceAsStream(path);
        if (is == null) {
            throw new IOException("Resource not found: " + path);
        }

        return new String(is.readAllBytes());
    }

    public String[] readLines(String filename) throws IOException {
        var lineSeperator = System.lineSeparator();
        return this.read(filename).split(lineSeperator);
    }

}
