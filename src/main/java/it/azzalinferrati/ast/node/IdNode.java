package it.azzalinferrati.ast.node;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Effect;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.STEntry;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.MissingDeclarationException;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

/**
 * Represents an identifier of a variable, pointer or function in the AST.
 */
public class IdNode implements Node {
    private final String id;
    private STEntry entry;
    private int currentNestingLevel;

    public IdNode(final String id) {
        this.id = id;
    }

    public void setEntry(STEntry entry) {
        this.entry = entry;
    }

    public void setStatus(Effect effect, int dereferenceLevel) {
        entry.setVariableStatus(effect, dereferenceLevel);
    }

    public Effect getStatus(int dereferenceLevel) {
        return entry.getVariableStatus(dereferenceLevel);
    }

    public String getIdentifier() {
        return id;
    }

    public int getNestingLevel() {
        return entry.getNestingLevel();
    }

    public STEntry getSTEntry() {
        return entry;
    }

    public int getCurrentNestingLevel() {
        return currentNestingLevel;
    }

    /**
     * Returns the offset in the Symbol Table entry.
     *
     * @return the offset or -1 if the entry is not set.
     */
    public int getOffset() {
        return entry != null ? entry.getOffset() : -1;
    }

    @Override
    public String toPrint(String indent) {
        return indent + id;
    }

    @Override
    public String toString() {
        return toPrint("");
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return entry.getType();
    }

    @Override
    public String codeGeneration() {
        StringBuilder buffer = new StringBuilder();

        buffer.append("mv $al $fp ;[get address of an LHS node pt1] the variable is declared in the same scope where it is used\n");
        for (int i = 0; i < (getCurrentNestingLevel() - getNestingLevel()); i++) {
            buffer.append("lw $al 0($al)\n");
        }

        int offsetWithAL = -(getOffset() + 1);
        buffer.append("lw $a0 ").append(offsetWithAL).append("($al) ; loads in $a0 the value in ").append(id).append("\n");

        return buffer.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        try {
            entry = env.lookup(id);
            currentNestingLevel = env.getNestingLevel();
        } catch (MissingDeclarationException exception) {
            errors.add(new SemanticError(exception.getMessage()));
        }

        return errors;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        entry = env.safeLookup(id);
        currentNestingLevel = env.getNestingLevel();
        return new ArrayList<>(); 
    }
}
