package it.azzalinferrati.semanticanalysis;

import java.util.ArrayList;
import java.util.List;

import it.azzalinferrati.ast.node.declaration.DecFunNode;
import it.azzalinferrati.ast.node.type.FunTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;

/**
 * {@code STEntry} represents an entry of the Symbol Table. The Symbol Table
 * contains information for the semantic analysis, type checking, effect
 * analysis, code generation.
 */
public class STEntry {
    // Nesting level.
    private final int nestingLevel;

    // Type of the identifier.
    private TypeNode type;

    // Offset for code generation.
    private final int offset;

    // Statuses of the variable in the stack and in the heap
    private final List<Effect> variableStatus;

    // Effects of a function.
    private final List<List<Effect>> functionStatus;

    // Reference to the node in the AST (which represents the function and its body, in order to perform the effect analysis with the correct variable statuses).
    private DecFunNode functionNode;

    public STEntry(int nestingLevel, int offset) {
        this.nestingLevel = nestingLevel;
        this.offset = offset;
        this.functionStatus = new ArrayList<>();
        this.variableStatus = new ArrayList<>();
    }

    public STEntry(int nestingLevel, TypeNode type, int offset) {
        this(nestingLevel, offset);
        this.type = type;

        if (type instanceof FunTypeNode) {
            for (var param : ((FunTypeNode) type).getParams()) {
                List<Effect> paramStatus = new ArrayList<>();
                int numberOfDereference = param.getDereferenceLevel();
                for (int i = 0; i < numberOfDereference; i++) {
                    paramStatus.add(new Effect(Effect.INITIALIZED));
                }
                this.functionStatus.add(paramStatus);
            }
        } else {
            // For all other types
            int numberOfDereference = type.getDereferenceLevel();
            for (int i = 0; i < numberOfDereference; i++) {
                this.variableStatus.add(new Effect(Effect.INITIALIZED));
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
        for (var fnStatus : s.functionStatus) {
            List<Effect> paramStatus = new ArrayList<>();
            for (var status : fnStatus) {
                paramStatus.add(new Effect(status));
            }
            this.functionStatus.add(paramStatus);
        }
        for (var varStatus : s.variableStatus) {
            this.variableStatus.add(new Effect(varStatus));
        }
        this.functionNode = s.functionNode;
    }

    /**
     * @return the type of the variable or function.
     */
    public TypeNode getType() {
        return type;
    }

    public DecFunNode getFunctionNode() {
        return functionNode;
    }

    public void setFunctionNode(DecFunNode functionNode) {
        this.functionNode = functionNode;
    }

    public Effect getVariableStatus(int dereferenceLevel) {
        return variableStatus.get(dereferenceLevel);
    }

    public int getMaxDereferenceLevel() {
        return variableStatus.size();
    }

    /**
     * @return the current status of the arguments of a function.
     */
    public List<List<Effect>> getFunctionStatus() {
        return functionStatus;
    }

    /**
     * @return the offset for code generation.
     */
    public int getOffset() {
        return offset;
    }


    /**
     * Sets the new effect for the entry in the Symbol Table at the given dereference level.
     *
     * @param status           new status for the variable
     * @param dereferenceLevel level in which {@code status} applies.
     */
    public void setVariableStatus(Effect status, int dereferenceLevel) {
        this.variableStatus.set(dereferenceLevel, new Effect(status));
    }

    /**
     * Sets the new effect for the {@code paramIndex}-th argument of the function.
     *
     * @param status new status for the argument
     */
    public void setParamStatus(int paramIndex, Effect status, int dereferenceLevel) {
        functionStatus.get(paramIndex).set(dereferenceLevel, new Effect(status));
    }

    /**
     * @return the nesting level of the declaration.
     */
    public int getNestingLevel() {
        return nestingLevel;
    }

    public String toPrint(String s) {
        return s + "(nesting level: " + nestingLevel + ", type: " + type + ", offset: " + offset
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

        if (!functionStatus.equals(entry.functionStatus)) {
            return false;
        }

        if (!type.equals(entry.type)) {
            return false;
        }

        return true;
    }
}
