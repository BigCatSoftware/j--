package jminusminus;

/**
 * The AST node for a hex integer literal.
 */
public class JLiteralHexInt extends JExpression{
    // String representation of the literal.
    private String text;

    /**
     * Constructs an AST node for a hex integer literal given its line number and string representation.
     *
     * @param line line in which the literal occurs in the source file.
     * @param text string representation of the literal.
     */
    public JLiteralHexInt(int line, String text) {
        super(line);
        this.text = text;
    }

    /**
     * Returns the literal as a hex integer.
     *
     * @return the literal as a hex integer.
     */
    public int toHexInteger() { return Integer.parseInt(text, 16); }

    /**
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {
        // TODO
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        // TODO
    }

    /**
     * {@inheritDoc}
     */
    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JLiteralDouble:" + line, e);
        e.addAttribute("type", type == null ? "" : type.toString());
        e.addAttribute("value", text);
    }
}
