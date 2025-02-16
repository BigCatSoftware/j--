public class TestVarargs {
    // Normal method
    public void normalMethod(int x, String y) {
        System.out.println(x + " " + y);
    }

    // Varargs method
    public void printAll(String label, int... numbers) {
        System.out.println(label);
        for (int num : numbers) {
            System.out.println(num);
        }
    }

    // Constructor with varargs
    public TestVarargs(String name, double... values) {
        System.out.println("Name: " + name);
        for (double v : values) {
            System.out.println(v);
        }
    }
}