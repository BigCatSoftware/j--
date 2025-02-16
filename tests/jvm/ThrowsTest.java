public class TestThrows {
    // Method without throws
    public void noExceptionMethod() {
        System.out.println("No exceptions here.");
    }

    // Method with a throws clause
    public void riskyMethod() throws IOException, NullPointerException {
        if (Math.random() > 0.5) {
            throw new IOException("Random IO failure");
        } else {
            throw new NullPointerException("Random Null failure");
        }
    }
}