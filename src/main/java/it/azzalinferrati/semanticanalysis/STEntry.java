package it.azzalinferrati.semanticanalysis;

import it.azzalinferrati.ast.node.type.TypeNode;

public class STEntry {
    // Nesting level.
    private int nestingLevel;
    
    // Type of the identifier.
    private TypeNode type;

    // Offset for code generation
    private int offset;


    public STEntry(int nestingLevel, int offset) {
        this.nestingLevel = nestingLevel;
        this.offset = offset;
    }

    public STEntry(int nestingLevel, TypeNode type, int offset) {
        this.nestingLevel = nestingLevel;
        this.type = type;
        this.offset = offset;
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
     * @return the offset for code generation.
     */
    public int getOffset() {
        return offset;
    }

    /**
     * @return the nesting level of the declaration.
     */
    public int getNestinglevel() {
        return nestingLevel;
    }

    public String toPrint(String s) {
        return s + "STentry: nestlev " + Integer.toString(nestingLevel) + "\n" +
                s + "STentry: type\n" +
                type.toPrint(s + "  ") +
                s + "STentry: offset " + Integer.toString(offset) + "\n";
    }
}
