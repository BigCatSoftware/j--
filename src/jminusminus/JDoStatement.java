// Copyright 2012- Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for a do-statement.
 */
public class JDoStatement extends JStatement {
    // Body.
    private JStatement body;

    // Test expression.
    private JExpression condition;

    /**
     * Constructs an AST node for a do-statement.
     *
     * @param line      line in which the do-statement occurs in the source file.
     * @param body      the body.
     * @param condition test expression.
     */
    public JDoStatement(int line, JStatement body, JExpression condition) {
        super(line);
        this.body = body;
        this.condition = condition;
    }

    /**
     * {@inheritDoc}
     */
    public JStatement analyze(Context context) {
        // analyze the body
        body = (JStatement) body.analyze(context);

        // analyze the condition
        condition = condition.analyze(context);

        if (condition.type() != Type.BOOLEAN) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Loop condition must be a boolean expression.");
        }

        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        String loopStart = output.createLabel(); // Label for start of loop
        String loopCondition = output.createLabel(); // Label for condition evaluation
        String loopEnd = output.createLabel(); // Label for loop exit

        // Step 1: Mark the beginning of the loop
        output.addLabel(loopStart);

        // Step 2: Generate code for the loop body
        body.codegen(output);

        // Step 3: Evaluate condition
        output.addLabel(loopCondition);
        condition.codegen(output, loopStart, true); // If true, jump back to loop start

        // Step 4: Mark the end of the loop
        output.addLabel(loopEnd);
    }

    /**
     * {@inheritDoc}
     */
    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JDoStatement:" + line, e);
        JSONElement e1 = new JSONElement();
        e.addChild("Body", e1);
        body.toJSON(e1);
        JSONElement e2 = new JSONElement();
        e.addChild("Condition", e2);
        condition.toJSON(e2);
    }
}
