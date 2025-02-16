// Copyright 2012- Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import java.util.ArrayList;

import static jminusminus.CLConstants.*;

/**
 * The AST node for a switch-statement.
 */
public class JSwitchStatement extends JStatement {
    // Test expression.
    private JExpression condition;

    // List of switch-statement groups.
    private ArrayList<SwitchStatementGroup> stmtGroup;

    /**
     * Constructs an AST node for a switch-statement.
     *
     * @param line      line in which the switch-statement occurs in the source file.
     * @param condition test expression.
     * @param stmtGroup list of statement groups.
     */
    public JSwitchStatement(int line, JExpression condition,
                            ArrayList<SwitchStatementGroup> stmtGroup) {
        super(line);
        this.condition = condition;
        this.stmtGroup = stmtGroup;
    }

    // Note: By Java 11, switch statements could use short, int, char,
    // string and enum. This means I'll need to try and implement
    // use of int, char, and string

    // I think I need to analyze the initialization statements
    // then analyze the condition expression
    /**
     * {@inheritDoc}
     */
    public JStatement analyze(Context context) {
        condition = (JExpression) condition.analyze(context);

        Type condType = condition.type();
        if (!(condType == Type.INT || condType == Type.CHAR || condType == Type.STRING)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Switch condition must be int, char, or string");
        }

        for (SwitchStatementGroup group : stmtGroup) {
            for (JExpression label : group.switchLabels) {
                if (label != null) {
                    label = (JExpression) label.analyze(context);
                    if (!(label.type() == condType)) {
                        JAST.compilationUnit.reportSemanticError(line(),
                                "Case label type must match switch condition type.");
                    }
                }
            }
            for (JStatement stmt : group.block) {
                stmt.analyze(context);
            }
        }

        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        String endLabel = output.createLabel();

        condition.codegen(output);

        ArrayList<String> caseLabels = new ArrayList<>();
        for (int i = 0; i < stmtGroup.size(); i++) {
            caseLabels.add(output.createLabel());
        }

        for (int i = 0; i < stmtGroup.size(); i++) {
            SwitchStatementGroup group = stmtGroup.get(i);
            for (JExpression label : group.switchLabels) {
                if (label != null) {
                    label.codegen(output);
                    output.addBranchInstruction(IF_ICMPEQ, caseLabels.get(i));
                }
            }
        }

        String defaultLabel = output.createLabel();
        output.addBranchInstruction(GOTO, defaultLabel);

        for (int i = 0; i < stmtGroup.size(); i++) {
            output.addLabel(caseLabels.get(i));
            for (JStatement stmt : stmtGroup.get(i).block) {
                stmt.codegen(output);
            }
        }

        output.addLabel(defaultLabel);
        output.addLabel(endLabel);
    }

    /**
     * {@inheritDoc}
     */
    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JSwitchStatement:" + line, e);
        JSONElement e1 = new JSONElement();
        e.addChild("Condition", e1);
        condition.toJSON(e1);
        for (SwitchStatementGroup group : stmtGroup) {
            group.toJSON(e);
        }
    }
}

/**
 * A switch statement group consists of case labels and a block of statements.
 */
class SwitchStatementGroup {
    // Case labels.
    ArrayList<JExpression> switchLabels;

    // Block of statements.
    ArrayList<JStatement> block;

    /**
     * Constructs a switch-statement group.
     *
     * @param switchLabels case labels.
     * @param block        block of statements.
     */
    public SwitchStatementGroup(ArrayList<JExpression> switchLabels, ArrayList<JStatement> block) {
        this.switchLabels = switchLabels;
        this.block = block;
    }

    /**
     * Stores information about this switch statement group in JSON format.
     *
     * @param json the JSON emitter.
     */
    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("SwitchStatementGroup", e);
        for (JExpression label : switchLabels) {
            JSONElement e1 = new JSONElement();
            if (label != null) {
                e.addChild("Case", e1);
                label.toJSON(e1);
            } else {
                e.addChild("Default", e1);
            }
        }
        if (block != null) {
            for (JStatement stmt : block) {
                stmt.toJSON(e);
            }
        }
    }
}
