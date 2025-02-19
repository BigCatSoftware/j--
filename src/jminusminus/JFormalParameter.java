// Copyright 2012- Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

/**
 * The AST node for a formal parameter declaration.
 */
class JFormalParameter extends JAST {
    // Parameter name.
    private String name;

    // Parameter type.
    private Type type;

    // Parameter type.
    private boolean isVarargs;

    /**
     * Constructs an AST node for a formal parameter declaration.
     *
     * @param line line in which the parameter occurs in the source file.
     * @param name parameter name.
     * @param type parameter type.
     */
    public JFormalParameter(int line, String name, Type type, boolean isVarargs) {
        super(line);
        this.name = name;
        this.type = type;
        this.isVarargs = isVarargs;
    }

    public boolean isVarArgs() { return isVarargs; }

    public void writeToStdOut(PrettyPrinter p) {
        String typeStr = isVarargs ? type.toString() + "..." : type.toString();
        p.printf("<JFormalParameter line=\"%d\" name=\"%s\" type=\"%s\" varargs=\"%b\"/>\n",
                line, name, typeStr, isVarargs);
    }

    /**
     * Returns the parameter's name.
     *
     * @return the parameter's name.
     */
    public String name() {
        return name;
    }

    /**
     * Returns the parameter's type.
     *
     * @return the parameter's type.
     */
    public Type type() {
        return type;
    }

    /**
     * Sets the parameter's type to the specified type, and returns the new type.
     *
     * @param newType the new type.
     * @return return the new type.
     */
    public Type setType(Type newType) {
        return type = newType;
    }

    /**
     * {@inheritDoc}
     */
    public JAST analyze(Context context) {
        // Nothing here.
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public void codegen(CLEmitter output) {
        // Nothing here.
    }
}
