public class MultiLineCommentTest {
    public static void main(String[] args) {
        System.out.println("Before comment");
        // This is a single line comment
        /* This is a simple multi-line comment.System.out.println("After comment");
           It should be ignored by the scanner. */
        System.out.println("After comment");

        /* Nested comments are not allowed in Java.
        /* This is an inner comment */
        /**
         *
         *
         *
         */

        /*
        System.out.println("This line should not be tokenized");

        System.out.println("This should be ignored as well");*/
    }
}
