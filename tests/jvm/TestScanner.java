public class TestScanner {
    // test_scanner.java - Tests for Scanner modifications in j--

/* Multi-line Comment Test
   This should be ignored by the scanner.
*/

    // Operators Test
    class OperatorsTest {
        void test() {
            int a = 10 + 5 - 2 * 3 / 4 % 2;  // Arithmetic
            a &= 2; a |= 3; a ^= 1;          // Bitwise
            a <<= 1; a >>= 2; a >>>= 3;      // Shift
            boolean b = (a == 10) && (a != 5) || !(a < 20); // Logical
        }
    }

    // Reserved Words Test
    class ReservedWordsTest {
        public void main(String[] args) {
            if (true) {
//                while (false) {
//                    break;
//                }
            } else {
                return;
            }
        }
    }

    // Literals Test
    class LiteralsTest {
        void test() {
            double d1 = 3.1415;
            double d2 = 2.0e10;
            float f = 3.14f;
            long l = 10000000000L;
            boolean b = true;
            char c = 'A';
            String s = "Hello, world!";
        }
    }

    // Bonus: Integer Representations Test (Hex, Octal, Binary)
    class IntegerRepresentationsTest {
        void test() {
            int decimal = 42;
            int hex = 0x2A;
            int octal = 052;
            int binary = 0b101010;
        }
    }

}
