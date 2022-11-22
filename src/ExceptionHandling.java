/**
 * A program that handles an exception.
 *
 * @author Ruhan
 */
public class ExceptionHandling {
    /**
     * A variable required to demonstrate the exception.
     */
    static int[] exampleArray;

    /**
     * A main method to demonstrate exception handling
     *
     * @param args
     */
    public static void main(String[] args) {

        try {
            double exampleVar = exampleArray[0] * exampleArray[1];
            System.out.println("The product of the first two values is " + exampleVar);
        }
        catch ( NullPointerException e ) {
            System.out.print("Programming error! Array doesn’t exist." + e);
        }
    }
}