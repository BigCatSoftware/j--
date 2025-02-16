// Copyright 2012- Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import java.util.ArrayList;

import static jminusminus.CLConstants.*;

/**
 * The AST node for a try-catch-finally statement.
 */
class JTryStatement extends JStatement {
    // The try block.
    private JBlock tryBlock;

    // The catch parameters.
    private ArrayList<JFormalParameter> parameters;

    // The catch blocks.
    private ArrayList<JBlock> catchBlocks;

    // The finally block.
    private JBlock finallyBlock;

    /**
     * Constructs an AST node for a try-statement.
     *
     * @param line         line in which the while-statement occurs in the source file.
     * @param tryBlock     the try block.
     * @param parameters   the catch parameters.
     * @param catchBlocks  the catch blocks.
     * @param finallyBlock the finally block.
     */
    public JTryStatement(int line, JBlock tryBlock, ArrayList<JFormalParameter> parameters,
                         ArrayList<JBlock> catchBlocks, JBlock finallyBlock) {
        super(line);
        this.tryBlock = tryBlock;
        this.parameters = parameters;
        this.catchBlocks = catchBlocks;
        this.finallyBlock = finallyBlock;
    }

    /**
     * {@inheritDoc}
     */
    public JTryStatement analyze(Context context) {
        tryBlock = (JBlock) tryBlock.analyze(context);

        for (int i = 0; i < catchBlocks.size(); i++) {
            JFormalParameter param = parameters.get(i);
            JBlock catchBlock = catchBlocks.get(i);

            Type exceptionType = param.type();

            LocalContext catchContext = new LocalContext(context);
            catchContext.addEntry(line(), param.name(), new LocalVariableDefn(exceptionType, 0));

            catchBlocks.set(i, catchBlock.analyze(catchContext));
        }

        if (finallyBlock != null) {
            finallyBlock = finallyBlock.analyze(context);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        String startTry = output.createLabel();
        String endTry = output.createLabel();
        String finallyStart = (finallyBlock != null) ? output.createLabel() : null;
        String endFinally = output.createLabel();

        ArrayList<String> catchLabels = new ArrayList<>();
        ArrayList<String> endCatches = new ArrayList<>();

        // Start Try Block
        output.addLabel(startTry);
        tryBlock.codegen(output);
        output.addLabel(endTry);

        // Jump to finally block if present
        if (finallyBlock != null) {
            output.addBranchInstruction(GOTO, finallyStart);
        } else {
            output.addBranchInstruction(GOTO, endFinally);
        }

        // Generate Catch Blocks
        for (int i = 0; i < catchBlocks.size(); i++) {
            String catchLabel = output.createLabel();
            String endCatch = output.createLabel();
            catchLabels.add(catchLabel);
            endCatches.add(endCatch);

            output.addLabel(catchLabel);
            output.addNoArgInstruction(ASTORE_1);  // Store exception in local variable
            catchBlocks.get(i).codegen(output);
            output.addBranchInstruction(GOTO, endFinally);

            // Add exception table entry
            output.addExceptionHandler(startTry, endTry, catchLabel, parameters.get(i).type().jvmName());
        }

        // Generate Finally Block if present
        if (finallyBlock != null) {
            output.addLabel(finallyStart);
            finallyBlock.codegen(output);
        }

        output.addLabel(endFinally);
    }

    /**
     * {@inheritDoc}
     */
    public void toJSON(JSONElement json) {
        JSONElement e = new JSONElement();
        json.addChild("JTryStatement:" + line, e);
        JSONElement e1 = new JSONElement();
        e.addChild("TryBlock", e1);
        tryBlock.toJSON(e1);
        if (catchBlocks != null) {
            for (int i = 0; i < catchBlocks.size(); i++) {
                JFormalParameter param = parameters.get(i);
                JBlock catchBlock = catchBlocks.get(i);
                JSONElement e2 = new JSONElement();
                e.addChild("CatchBlock", e2);
                String s = String.format("[\"%s\", \"%s\"]", param.name(), param.type() == null ?
                        "" : param.type().toString());
                e2.addAttribute("parameter", s);
                catchBlock.toJSON(e2);
            }
        }
        if (finallyBlock != null) {
            JSONElement e2 = new JSONElement();
            e.addChild("FinallyBlock", e2);
            finallyBlock.toJSON(e2);
        }
    }
}
