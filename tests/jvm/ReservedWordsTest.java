public class ReservedWordsTest {
    abstract class AbstractClass {}

    interface TestInterface {}

    /*
     * The following keywords were omitted because they were causing errors when running tests on this file:
     * - package
     * - goto
     * - instanceof
     * - super
     * - volatile
     * - const
     * - assert
     */

    public static void main(String[] args) {
        final int a = 0;
        boolean b = true;
        byte c = 1;
        char d = 'd';
        double e = 10.0;
        float f = 1.0f;
        long g = 100L;
        short h = 10;
        final int i = 5;

        // 'continue' can be used in a loop, added to an inner loop
        for (int l = 0; l < 10; l++) {
            if (l == 5) {
                continue;  // Skip iteration when l is 5
            }
        }

        // 'this' can be used to refer to the current instance
        ReservedWordsTest test = new ReservedWordsTest();
//        test.testMethod();

        // 'throw' is used to throw an exception
        if (a == 0) {
            throw new IllegalArgumentException("a cannot be zero");
        }

        switch (a) {
            case 0:
                break;
            default:
                break;
        }

        for (int l = 0; l < 10; l++) {}
        while (b) break;
        do {} while (b);

        if (b) {} else {}
        try {} catch (Exception ex) {} finally {}
        synchronized (ReservedWordsTest.class) {}

        assert b;

        new ReservedWordsTest();

        TestInterface ti = new TestInterface() {};

    }

    private void privateMethod() {}

    strictfp void strictfpMethod() {}

    void throwsMethod() throws Exception {}

    enum Color { RED, GREEN, BLUE }

    transient int transientField;
}
