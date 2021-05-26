package it.azzalinferrati.semanticanalysis;

import it.azzalinferrati.ast.node.type.TypeNode;

public class STEntry {
    // Nesting level.
    private int nestingLevel;
    
    // Type of the identifier.
    private TypeNode type;

    // Offset for code generation
    private int offset;

    // Status of the variable
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

    public void setStatus(Effect status) {
        this.status = status;
    }

    /**
     * @return the nesting level of the declaration.
     */
    public int getNestinglevel() {
        return nestingLevel;
    }

    public String toPrint(String s) {
        return s + "STentry: (nesting level: " + nestingLevel + ", type: " + type.toPrint("") + ", offset: " + offset + ")";
    }
}
