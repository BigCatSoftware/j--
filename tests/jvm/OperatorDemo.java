public class OperatorDemo {
    public static void main(String[] args) {
        int a = 10, b = 20;
        boolean flag = true;

        // Assignment
        a = b;

        // Comparison
        boolean greater = a > b;
        boolean less = a < b;
        boolean not = !flag;
        int bitwiseNot = ~a;
        int ternary = flag ? a : b;

        // Lambda expression (Used in functional interfaces)
        Runnable r = () -> System.out.println("Lambda Expression");
        r.run();

        // Equality and Relational Operators
        boolean equal = (a == b);
        boolean greaterOrEqual = (a >= b);
        boolean lessOrEqual = (a <= b);
        boolean notEqual = (a != b);

        // Logical Operators
        boolean logicalAnd = (a > 5 && b > 15);
        boolean logicalOr = (a < 5 || b > 15);

        // Increment & Decrement
        int increment = ++a;
        int decrement = --b;

        // Arithmetic Operators
        int addition = a + b;
        int subtraction = a - b;
        int multiplication = a * b;
        int division = a / b;
        int modulo = a % b;

        // Bitwise Operators
        int bitwiseAnd = a & b;
        int bitwiseOr = a | b;
        int bitwiseXor = a ^ b;
        int leftShift = a << 2;
        int rightShift = a >> 2;
        int unsignedRightShift = a >>> 2;

        // Compound Assignment Operators
        a += b;
        a -= b;
        a *= b;
        a /= b;
        a &= b;
        a |= b;
        a ^= b;
        a %= b;
        a <<= 2;
        a >>= 2;
        a >>>= 2;
    }
}
