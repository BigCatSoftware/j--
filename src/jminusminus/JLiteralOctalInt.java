package jminusminus;

public class JLiteralOctalInt extends JExpression {
    // String representation of the literal.
    private String text;

    /**
     * Constructs an AST node for an octal integer literal given its line number and string representation.
     *
     * @param line line in which the literal occurs in the source file.
     * @param text string representation of the literal.
     */
    protected JLiteralOctalInt(int line, String text) {
        super(line);
        this.text = text;
    }

    /**
     * Returns the literal as an integer.
     *
     * @return the literal as an integer.
     */
    public int toOctalInteger() { return Integer.parseInt(text, 8); }

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
        json.addChild("JLiteralOctalInteger:" + line, e);
        e.addAttribute("type", type == null ? "" : type.toString());
        e.addAttribute("value", text);
    }
}
