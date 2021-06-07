package it.azzalinferrati.ast.node;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Effect;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.STEntry;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.MissingDeclarationException;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

public class IdNode implements Node {
    //TODO: extends
    private final String id;
    private STEntry entry;
    private int currentNestingLevel;

    public IdNode(final String id) {
        this.id = id;
    }

    public void setEntry(STEntry entry) {
        this.entry = entry;
    }

    public void setStatus(Effect effect) {
        entry.setStatus(effect);
    }

    public Effect getStatus() {
        return entry.getStatus();
    }

    public String getId() {
        return id;
    }

    public int getNestingLevel() {
        return entry.getNestingLevel();
    }

    public int getCurrentNestingLevel() {
        return currentNestingLevel;
    }

    /**
     * Returns the offset in the Symbol Table entry.
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
    public TypeNode typeCheck() throws TypeCheckingException {
        return entry.getType();
    }

    @Override
    public String codeGeneration() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("mv $al $fp ;go bakck in the static chain to get the right variable\n");
        for (int i = 0; i < (currentNestingLevel - getNestingLevel()); i++) {
            buffer.append("lw $al 0($al)\n");
        }
        buffer.append("lw $a0 ").append(-getOffset()).append("($al) ;load in $a0 the value in ").append(id).append("\n");

        return buffer.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        try {
            entry = env.lookup(id);
            currentNestingLevel = env.getNestingLevel();
        } catch(MissingDeclarationException exception) {
            errors.add(new SemanticError(exception.getMessage()));
        }

        return errors;
    }
    
}
