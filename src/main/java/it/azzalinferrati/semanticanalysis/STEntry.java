package it.azzalinferrati.semanticanalysis;

import it.azzalinferrati.ast.node.type.TypeNode;

/**
 * STEntry represents an entry of the Symbol Table. The Symbol Table contains
 * information for the semantic analysis, type checking, effect analysis, code
 * generation.
 */
public class STEntry {
    // Nesting level.
    private int nestingLevel;

    // Type of the identifier.
    private TypeNode type;

    // Offset for code generation.
    private int offset;

    // Status of the variable.
    private Effect status;

    public STEntry(int nestingLevel, int offset) {
        this.nestingLevel = nestingLevel;
        this.offset = offset;
        this.status = new Effect();
    }

    public STEntry(int nestingLevel, TypeNode type, int offset) {
        this(nestingLevel, offset);
        this.type = type;
    }

    /**
     * Copy constructor of STEntry.
     * 
     * @param s Symbol Table Entry
     */
    public STEntry(STEntry s) {
        this(s.nestingLevel, s.type, s.offset);
        this.status = new Effect(s.status);
    }

    public void addType(TypeNode type) {
        this.type = type;
    }

    /**
     * @return the type of the variable or function.
     */
    public TypeNode getType() {
        return type;
    }

    /**
     * @return the current status of the variable.
     */
    public Effect getStatus() {
        return status;
    }

    /**
     * @return the offset for code generation.
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Sets the new effect for the entry in the Symbol Table.
     * @param status new status for the variable
     */
    public void setStatus(Effect status) {
        this.status = new Effect(status);
    }

    /**
     * @return the nesting level of the declaration.
     */
    public int getNestingLevel() {
        return nestingLevel;
    }

    public String toPrint(String s) {
        return s + "(nesting level: " + nestingLevel + ", type: " + type.toPrint("") + ", offset: " + offset
                + ", status: " + status + ")";
    }

    @Override
    public String toString() {
        return toPrint("");
    }
}
