public class Assignment4Testing {
    public static void main(String[] args) {
        // do while testing
        int c = 0;
        do {
            c++;
        } while (c < 10);

        // classic for testing
        for (int i = 0; i < 10; i++) {
            int a = i % 10;
        }

        // ternary operator test
        int x = 10;
        int y = 20;
        int min = (x < y) ? x : y;

        // conditional || or testing
        boolean b = false || true;

        // long testing
        long z = 200l;

        // throw test
        throw new RuntimeException("Test throw statement");
    }
}