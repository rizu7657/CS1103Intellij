package Unit4;

import java.util.Set;
import java.util.TreeSet;

public class SetCalculator {
    Set<Integer> left;
    Set<Integer> right;
    String operator;

    SetCalculator() {
        left = new TreeSet<>();
        right = new TreeSet<>();
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n\nEnter a fully parenthesized expression,");
            System.out.println("or press return to end.");
            System.out.print("\n?  ");
            TextIO.skipBlanks();
            if (TextIO.peek() == '\n')
                break;
            try {
                TreeSet<Integer> result = expressionValue();
                TextIO.skipBlanks();
                if (TextIO.peek() != '\n')
                    throw new ParseError("Extra data after end of expression.");
                TextIO.getln();
                System.out.println("\nValue is " + val);
            } catch (ParseError e) {
                System.out.println("\n*** Error in input:    " + e.getMessage());
                System.out.println("*** Discarding input:  " + TextIO.getln());
            }
        }
    }

}
