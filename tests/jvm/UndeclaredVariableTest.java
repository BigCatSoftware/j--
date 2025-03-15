public class UndeclaredVariableTest {
    public static void main(String[] args) {
        x = 10;  // Should trigger a single error for 'x' being undeclared
        int y = x;  // Should NOT trigger another error if 'x' is assigned Type.ANY
    }
}