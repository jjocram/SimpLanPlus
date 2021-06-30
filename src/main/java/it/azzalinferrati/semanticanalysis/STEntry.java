package it.azzalinferrati.semanticanalysis;

import java.util.ArrayList;
import java.util.List;

import it.azzalinferrati.ast.node.type.FunTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;

/**
 * {@code STEntry} represents an entry of the Symbol Table. The Symbol Table
 * contains information for the semantic analysis, type checking, effect
 * analysis, code generation.
 */
public class STEntry {
    // Nesting level.
    private int nestingLevel;

    // Type of the identifier.
    private TypeNode type;

    // Offset for code generation.
    private int offset;

    // Status of the variable.
    private Effect variableStatus;

    // Effects of a function.
    private List<Effect> functionStatus;

    public STEntry(int nestingLevel, int offset) {
        this.nestingLevel = nestingLevel;
        this.offset = offset;
        this.variableStatus = new Effect();
        this.functionStatus = new ArrayList<>();
    }

    public STEntry(int nestingLevel, TypeNode type, int offset) {
        this(nestingLevel, offset);
        this.type = type;
        if(type instanceof FunTypeNode) {
            var paramsNumber = ((FunTypeNode) type).getParams().size();
            for(int i = 0; i < paramsNumber; i++) {
                this.functionStatus.add(new Effect(Effect.INITIALIZED));
            }
        }
    }

    /**
     * Copy constructor of STEntry.
     * 
     * @param s Symbol Table Entry
     */
    public STEntry(STEntry s) {
        this(s.nestingLevel, s.offset);
        this.type = s.type;
        this.variableStatus = new Effect(s.variableStatus);
        for(var fnStatus: s.functionStatus) {
            this.functionStatus.add(new Effect(fnStatus));
        }
    }

    /**
     * Sets the type of the entry.
     * 
     * @param type to set
     */
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
    public Effect getVariableStatus() {
        return variableStatus;
    }

    /**
     * @return the current status of the arguments of a function.
     */
    public List<Effect> getFunctionStatus() {
        return functionStatus;
    }

    /**
     * @return the offset for code generation.
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Sets the new effect for the entry in the Symbol Table.
     * 
     * @param status new status for the variable
     */
    public void setVariableStatus(Effect status) {
        this.variableStatus = new Effect(status);
    }

    /**
     * Sets the new effect for the {@code paramIndex}-th argument of the function.
     * 
     * @param status new status for the argument
     */
    public void setParamStatus(int paramIndex, Effect status) {
        functionStatus.set(paramIndex, new Effect(status));
    }

    /**
     * @return the nesting level of the declaration.
     */
    public int getNestingLevel() {
        return nestingLevel;
    }

    public String toPrint(String s) {
        return s + "(nesting level: " + nestingLevel + ", type: " + type.toPrint("") + ", offset: " + offset
                + ", status: " + variableStatus + ")";
    }

    @Override
    public String toString() {
        return toPrint("");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        STEntry entry = (STEntry) obj;

        if (nestingLevel != entry.nestingLevel) {
            return false;
        }

        if (offset != entry.offset) {
            return false;
        }

        if (!variableStatus.equals(entry.variableStatus)) {
            return false;
        }

        if(!functionStatus.equals(entry.functionStatus)) {
            return false;
        }

        if (!type.equals(entry.type)) {
            return false;
        }

        return true;
    }
}
