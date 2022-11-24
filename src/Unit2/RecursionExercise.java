package Unit2;

/** A class that demonstrates the application of recursion for calculating a factorial
 * and fibonacci with a given range.
 *
 * @author Ruhan
 */
public class RecursionExercise {
    public static void main(String[] args) {
        System.out.println("For factorial: ");
        for (int i = 0; i < 10; i++) {
            System.out.println(i + ": " + factorial(i));
        }
        System.out.println("");
        System.out.println("For fibonacci: ");
        for (int i = 0; i < 10; i++) {
            System.out.println(i + ": " + fibonacci(i));
        }
    }

    /**
     * A method that takes a integer and calculates the factorial for that number.
     * The factorial calculates !x = 1 * 2 * ... * x
     *
     * @param x Integer
     * @return
     */
    public static Integer factorial(Integer x) {
        if (x == 0) {
            return 1;
        } else {
            return x * factorial(x - 1);
        }
    }

    /**
     * A method that takes an integer number and calculates the next sequence in the fibonacci range.
     *
     * param x Integer
     * @return
     */
    public static Integer fibonacci(Integer x) {
        if (x == 0 || x == 1) {
            return 1;
        } else {
            return fibonacci(x - 1) + fibonacci(x - 2);
        }
    }
}
