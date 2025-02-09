public class NumericLiteralsTest {
    public static void main(String[] args) {
        // Integer types in different bases
        int decimalInt = 42;        // Base 10
        int binaryInt = 0b101010;   // Base 2 (Binary)
        int octalInt = 052;         // Base 8 (Octal)
        int hexInt = 0x2A;          // Base 16 (Hexadecimal)

        // Long types
        long decimalLong = 42L;
        long binaryLong = 0b101010L;
        long octalLong = 052L;
        long hexLong = 0x2AL;

        // Integer literals with underscores
        int decimalIntUnderscore = 1_000_000;
        int binaryIntUnderscore = 0b1010_1010;
        int octalIntUnderscore = 05_2;
        int hexIntUnderscore = 0x2A_3F;

        // Long literals with underscores
        long decimalLongUnderscore = 9_223_372_036_854_775_807L;
        long binaryLongUnderscore = 0b1010_1010_1010_1010L;
        long octalLongUnderscore = 07_777_777_777L;
        long hexLongUnderscore = 0x7F_FF_FF_FF_FF_FF_FF_FFL;

        // Floating-point types
        float decimalFloat = 3.14f;
        float scientificFloatPos = 3.14e2f;  // 3.14 * 10^2
        float scientificFloatNeg = 3.14e-2f; // 3.14 * 10^-2

        double decimalDouble = 3.14159;
        double scientificDoublePos = 3.14159e2;  // 3.14159 * 10^2
        double scientificDoubleNeg = 3.14159e-2; // 3.14159 * 10^-2

        // Floating-point literals with underscores
        float decimalFloatUnderscore = 1_234.567_8f;
        float scientificFloatUnderscore = 1.23_45e3f;
        double decimalDoubleUnderscore = 1_000_000.123_456;
        double scientificDoubleUnderscore = 6.02_2e23;
    }
}
