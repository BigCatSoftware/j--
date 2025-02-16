public class TryCatchFinallyTest {
    public static void main(String[] args) {
        try {
            int x = 5 / 0; // This will cause an exception
        } catch (ArithmeticException e) {
            System.out.println("Caught an exception: " + e);
        } finally {
            System.out.println("Finally block executed.");
        }
    }
}