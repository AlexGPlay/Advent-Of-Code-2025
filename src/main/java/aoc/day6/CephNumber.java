package aoc.day6;

public class CephNumber {
    long parsedNumber;
    char[] originalNumber;

    public CephNumber(String s){
        this.originalNumber = parseNumber(s);
        this.parsedNumber = Long.parseLong(s.trim());
    }

    private char[] parseNumber(String s){
        var parsedNumber = new char[s.length()];

        for(var i=0;i<s.length();i++){
            var c = s.charAt(i);
            if(c == ' ') parsedNumber[i] = 0;
            else parsedNumber[i] = c;
        }

        return parsedNumber;
    }

}
