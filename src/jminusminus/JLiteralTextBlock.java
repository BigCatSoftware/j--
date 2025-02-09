package jminusminus;

public class JLiteralTextBlock extends JExpression {

    private String text;

    /**
     * Constructs an AST node for a TextBlock literal given its line number and string representation.
     *
     * @param line line in which the literal occurs in the source file.
     * @param text string representation of the literal.
     */
    public JLiteralTextBlock (int line, String text) {
        super(line);
        this.text = text;
    }

    /**
     * Returns the literal as a String.
     *
     * @return the literal as a String.
     */
    public String toString() { return text; }

    /**
     * {@inheritDoc}
     */
    @Override
    public JExpression analyze(Context context) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void codegen(CLEmitter output) {

    }

    /**
     * {@inheritDoc}
     */
    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JLiteralTextBlock:" + line, e);
        e.addAttribute("type", type == null ? "" : type.toString());
        e.addAttribute("value", text);
    }
}
