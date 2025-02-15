public class OperatorParsingTest {
    public static void main(String[] args) {

        // tests for all binary operators from JBinaryExpression
        long ZZ = 1L + 2;
        int za = 1 * 2;
        int zb = 2 - 1;
        int d = 4 / 2;

        // remainder %
        int modding = 15 % 4;
        // or |
        int orring = 15l | 4;
        // Xor op ^
        long xoring = 10l ^ 10;
        // BitAnd &
        int anding = 15l & 3;
        // Left Shift <<
        int leftshifting = 15l << 2;
        // Right Shift >>
        int rightShifting = 10 >> 2;
        // Logical Right Shift >>>
        int logicalrightshifting = 24000l >>> 3;

        // tests for JBooleanBinaryExpressions
        // equals ==
        boolean equalTest = true == false;
        // and &&
        boolean andTest = true && false;
        // or ||
        boolean orTest = false || true;
        // not equals !=
        boolean notEqualTest = true != false;

        // tests for all unary operators from JUnaryExpression
        // logical not !
        boolean result1 = !true;
//        // unary negation -
        int result2 = -5;
//        // post decrement --
//        int result3 = 10;
//        result3--;
//        // pre increment ++
        int result4 = ++5;
//        ++result4;
//        // unary plus +
//        int result5 = +3;
//        // unary complement ~
        int result6 = ~5;
//        // post increment ++
//        int result7 = 20;
//        result7++;
//        // pre decrement --
        int result8 = --15;
//        --result8;
        // logical not op !
        int result9 = !10;

        int result10 = (7 > 5) ? 10 : 20;

    }
}