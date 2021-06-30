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
    private Effect status;

    // Effects of a function.
    private List<Effect> statusFunction;

    public STEntry(int nestingLevel, int offset) {
        this.nestingLevel = nestingLevel;
        this.offset = offset;
        this.status = new Effect();
        this.statusFunction = new ArrayList<>();
    }

    public STEntry(int nestingLevel, TypeNode type, int offset) {
        this(nestingLevel, offset);
        this.type = type;
        if(type instanceof FunTypeNode) {
            var paramsNumber = ((FunTypeNode) type).getParams().size();
            for(int i = 0; i < paramsNumber; i++) {
                this.statusFunction.add(new Effect(Effect.INITIALIZED));
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
        this.status = new Effect(s.status);
        for(var fnStatus: s.statusFunction) {
            this.statusFunction.add(new Effect(fnStatus));
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
    public Effect getStatus() {
        return status;
    }

    public List<Effect> getStatusFunction() {
        return statusFunction;
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
    public void setStatus(Effect status) {
        this.status = new Effect(status);
    }

    public void setParamEffect(int paramIndex, Effect effect) {
        statusFunction.set(paramIndex, new Effect(effect));
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

        if (!status.equals(entry.status)) {
            return false;
        }

        if (!type.equals(entry.type)) {
            return false;
        }

        return true;
    }
}
