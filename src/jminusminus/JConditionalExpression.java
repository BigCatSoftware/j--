// Copyright 2012- Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for a conditional expression.
 */
class JConditionalExpression extends JExpression {
    // Test expression.
    private JExpression condition;

    // Then part.
    private JExpression thenPart;

    // Else part.
    private JExpression elsePart;

    /**
     * Constructs an AST node for a conditional expression.
     *
     * @param line      line in which the conditional expression occurs in the source file.
     * @param condition test expression.
     * @param thenPart  then part.
     * @param elsePart  else part.
     */
    public JConditionalExpression(int line, JExpression condition, JExpression thenPart,
                                  JExpression elsePart) {
        super(line);
        this.condition = condition;
        this.thenPart = thenPart;
        this.elsePart = elsePart;
    }

    /**
     * {@inheritDoc}
     */
    public JExpression analyze(Context context) {
        condition  = condition.analyze(context);
        System.out.println("Condition: " + condition);
        if (condition.type() != Type.BOOLEAN) {
            JAST.compilationUnit.reportSemanticError(line,
                    "Consitional expression condition must be a boolean.");
        }

        System.out.println("ElsePart: " + elsePart);

        thenPart = thenPart.analyze(context);
        elsePart = elsePart.analyze(context);

        if (thenPart.type() == elsePart.type()) {
            type = thenPart.type();
            System.out.println("ThenPart: " + thenPart);
        } else if (thenPart.type().isPrimitive() && elsePart.type().isPrimitive()) {
            type = promoteNumeric(thenPart.type(), elsePart.type());
        } else {
            JAST.compilationUnit.reportSemanticError(line,
                    "Incompatible types in conditional expression: " +
                    thenPart.type() + " and " + elsePart.type());
            type = Type.ANY;
        }
        return this;
    }

    public static Type promoteNumeric(Type t1, Type t2) {
        // If either type is double, the result will be double (wider)
        if (t1 == Type.DOUBLE || t2 == Type.DOUBLE) return Type.DOUBLE;

        // If either type is float, the result will be float
        if (t1 == Type.FLOAT || t2 == Type.FLOAT) return Type.FLOAT;

        // If either type is long, the result will be long
        if (t1 == Type.LONG || t2 == Type.LONG) return Type.LONG;

        // If neither is a wider type, the result will be int (default to the smaller type)
        return Type.INT;
    }


    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        // Step 1: Generate label for skipping the "else" part
        String elseLabel = output.createLabel();
        String doneLabel = output.createLabel();

        // Step 2: Generate code for the condition (evaluation of the boolean expression)
        condition.codegen(output, elseLabel, true);  // Jump to elseLabel if condition is false

        // Step 3: Generate code for the "thenPart" if condition is true
        thenPart.codegen(output);
        output.addBranchInstruction(GOTO, doneLabel);  // Skip else part after executing thenPart

        // Step 4: Generate label for the "else" part
        output.addLabel(elseLabel);
        // Generate code for the "elsePart" if condition is false
        System.out.println("ElsePart: " + elsePart);
        elsePart.codegen(output);

        // Step 5: Done, add final label after both then/else evaluations
        output.addLabel(doneLabel);
    }

    /**
     * {@inheritDoc}
     */
    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JConditionalExpression:" + line, e);
        JSONElement e1 = new JSONElement();
        e.addChild("Condition", e1);
        condition.toJSON(e1);
        JSONElement e2 = new JSONElement();
        e.addChild("ThenPart", e2);
        thenPart.toJSON(e2);
        JSONElement e3 = new JSONElement();
        e.addChild("ElsePart", e3);
        elsePart.toJSON(e3);
    }
}
