package Unit2;

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

    public static Integer factorial(Integer x) {
        if (x == 0) {
            return 1;
        } else {
            return x * factorial(x - 1);
        }
    }

    public static Integer fibonacci(Integer x) {
        if (x == 0 || x == 1) {
            return 1;
        } else {
            return fibonacci(x - 1) + fibonacci(x - 2);
        }

    }
}
