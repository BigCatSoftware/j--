package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for a float literal.
 */
public class JLiteralFloat extends JExpression {
    // String representation of the literal.
    private String text;

    /**
     * Constructs an AST node for a float literal given its line number and string representation.
     *
     * @param line
     * @param text
     */
    public JLiteralFloat(int line, String text) {
        super(line);
        this.text = text;
    }

    /**
     * Returns the literal as a float.
     *
     * @return the literal as a float
     */
    public float toFloat() { return Float.parseFloat(text); }

    /**
     *
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {
        type = Type.FLOAT;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        float f = toFloat();
        if (f == 0.0f) {
            output.addNoArgInstruction(FCONST_0);
        } else if (f == 1.0f) {
            output.addNoArgInstruction(FCONST_1);
        } else if (f == 2.0f) {
            output.addNoArgInstruction(FCONST_2);
        } else {
            output.addLDCInstruction(f);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JLiteralFloat:" + line, e);
        e.addAttribute("type", type == null ? "" : type.toString());
        e.addAttribute("value", text);
    }
}
