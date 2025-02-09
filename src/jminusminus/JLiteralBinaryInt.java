package jminusminus;

public class JLiteralBinaryInt extends JExpression {
    // String representation of the literal.
    private String text;

    /**
     * Constructs an AST node for a binary integer literal given its line number and string representation.
     *
     * @param line line in which the literal occurs in the source file.
     * @param text string representation of the literal.
     */
    public JLiteralBinaryInt(int line, String text) {
        super(line);
        this.text = text;
    }

    /**
     * Returns the literal as an integer.
     *
     * @return the literal as an integer.
     */
    public int toBinaryInteger() { return Integer.parseInt(text, 2); }

    /**
     * {@inheritDoc}
     */
    @Override
    public JExpression analyze(Context context) {
        // TODO
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void codegen(CLEmitter output) {
        // TODO
    }

    /**
     * {@inheritDoc}
     */
    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JLiteralBinaryInteger:" + line, e);
        e.addAttribute("type", type == null ? "" : type.toString());
        e.addAttribute("value", text);
    }
}
