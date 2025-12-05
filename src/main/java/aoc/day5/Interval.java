package aoc.day5;

public class Interval {
    public final long lowerLimit;
    public final long upperLimit;

    public Interval(long lowerLimit, long upperLimit){
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    public boolean contains(long n){
        return n >= lowerLimit && n <= upperLimit;
    }

    public long length(){
        return upperLimit - lowerLimit + 1;
    }

    public boolean intersects(Interval other){
        return this.lowerLimit <= other.upperLimit && other.lowerLimit <= this.upperLimit;
    }

    public Interval combine(Interval other){
        if(!this.intersects(other)) return null;

        long newLower = Math.min(this.lowerLimit, other.lowerLimit);
        long newUpper = Math.max(this.upperLimit, other.upperLimit);
        return new Interval(newLower, newUpper);
    }

}
