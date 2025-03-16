// Copyright 2012- Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import java.util.ArrayList;

import static jminusminus.CLConstants.*;

/**
 * The AST node for a for-statement.
 */
class JForStatement extends JStatement {
    // Initialization.
    private ArrayList<JStatement> init;

    // Test expression
    private JExpression condition;

    // Update.
    private ArrayList<JStatement> update;

    // The body.
    private JStatement body;

    /**
     * Constructs an AST node for a for-statement.
     *
     * @param line      line in which the for-statement occurs in the source file.
     * @param init      the initialization.
     * @param condition the test expression.
     * @param update    the update.
     * @param body      the body.
     */
    public JForStatement(int line, ArrayList<JStatement> init, JExpression condition,
                         ArrayList<JStatement> update, JStatement body) {
        super(line);
        this.init = init;
        this.condition = condition;
        this.update = update;
        this.body = body;
    }

    /**
     * {@inheritDoc}
     */
    public JForStatement analyze(Context context) {
        // Analyze initialization statements
        for (JStatement stmt : init) {
            stmt.analyze(context);
        }

        // Analyze the condition expression
        if (condition != null) {
            condition = condition.analyze(context);
            if (condition.type() != Type.BOOLEAN) {
                throw new RuntimeException("Condition must be a boolean expression.");
            }
        }

        // Analyze update statements (Fix: Ensure all update expressions are analyzed)
        for (JStatement stmt : update) {
            stmt.analyze(context);
        }

        // Analyze the body of the loop
        body.analyze(context);

        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        String conditionLabel = output.createLabel();
        String updateLabel = output.createLabel();
        String doneLabel = output.createLabel();

        // Step 1: Code for the initialization part
        for (JStatement stmt : init) {
            stmt.codegen(output);
        }

        // step 2: Code for the condition
//        output.addLabel(conditionLabel);
//        if (condition != null) {
//            condition.codegen(output, doneLabel, false);
//        }
        output.addBranchInstruction(GOTO, conditionLabel);

        // Step 3: Code for the body
        output.addLabel(output.createLabel()); // Make the start of the loop body
        body.codegen(output);

        // Step 4: Code for the update
        output.addLabel(updateLabel);
        for (JStatement stmt : update) {
            stmt.codegen(output);
        }

        // Step 5: Jump back to condition evaluation
//        output.addBranchInstruction(GOTO, conditionLabel);
        output.addLabel(conditionLabel);
        if (condition != null) {
            condition.codegen(output);
            output.addBranchInstruction(IFNE, updateLabel); // Loop again if condition is true
        } else {
            output.addBranchInstruction(GOTO, updateLabel); // Infinite loop
        }

        // Step 6: Done label
        output.addLabel(doneLabel);
    }

    /**
     * {@inheritDoc}
     */
    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JForStatement:" + line, e);
        if (init != null) {
            JSONElement e1 = new JSONElement();
            e.addChild("Init", e1);
            for (JStatement stmt : init) {
                stmt.toJSON(e1);
            }
        }
        if (condition != null) {
            JSONElement e1 = new JSONElement();
            e.addChild("Condition", e1);
            condition.toJSON(e1);
        }
        if (update != null) {
            JSONElement e1 = new JSONElement();
            e.addChild("Update", e1);
            for (JStatement stmt : update) {
                stmt.toJSON(e1);
            }
        }
        if (body != null) {
            JSONElement e1 = new JSONElement();
            e.addChild("Body", e1);
            body.toJSON(e1);
        }
    }
}
