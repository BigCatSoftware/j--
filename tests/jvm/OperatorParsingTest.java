public class OperatorParsingTest {
    public static void main(String[] args) {

        // assignment expression tests
        int a = 5;
        a += 5;
        a -= 5;
        a *= 10;
        a /= 5;
        a %= 5;
        a |= 12;
        a &= 24;
        a ^= 24;
        a <<= 3;
        a >>= 2;
        a >>>= 1;

        // ternary operator test
        int ab = (10 > 5) ? 10 : 4;

        // conditional and expression tests
        boolean b = true || false;
        boolean c = true && false;

        // bitwise espression tests
        int d = 5 | 3;
        int e = 10 ^ 5;
        int f = 20 & 5;

        // equality expression tests
        boolean g = 5 == 5;
        boolean h = 10 != 10;

        // relational expression tests
        boolean i = 10 > 5;
        boolean j = 10 < 5;
        boolean k = 20 >= 20;
        boolean l = 20 <= 15;
        // m test for instanceof not implemented yet

        // shift expression tests
        int n = 10 << 2;
        int o = 10 >> 2;
        int p = 100 >>> 2;

        // additive expression tests
        int q = 10 - 5;
        // fix addition operator
        // TODO
        int r = 10 + 5;

        // multiplicative exspression tests
        int s = 10 * 2;
        int t = 10 / 2;
        int u = 10 % 3;

        // unary expression tests
        int v = 10;
//        ++v;
//        --v;
        int w = 10;
        int w2 = ~w; //
        int w3 = !w;
        int x = -10; //
        int y = !10;

        // for loop parsing test as well as post dec/inc and pre post dec/inc
        // I am having trouble with parsing the pre incremenet and decrements properly
        // they are showing up as post when they should be pre
        for (int i = 0; i < 10; i++) {
            int x = i % 10;
        }

        for (int i = 10; i >= 0; i--) {
            int x = i % 10;
        }

        // I'm having trouble parsing pre increment and pre decrement
//        for (int i = 0; i < 10; ++i) {
//            int x = i % 10;
//        }
//
//        for (int i = 10; i >= 0; --i) {
//            int x = i % 10;
//        }
    }
}