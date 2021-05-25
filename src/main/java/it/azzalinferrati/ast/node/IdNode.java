package it.azzalinferrati.ast.node;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.STEntry;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.MissingDeclarationException;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

public class IdNode implements Node {
    //TODO: extends
    private final String id;
    private STEntry entry;

    public IdNode(final String id) {
        this.id = id;
    }

    public void setEntry(STEntry entry) {
        this.entry = entry;
    }

    public String getId() {
        return id;
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        try {
            entry = env.lookup(id);
        } catch(MissingDeclarationException exception) {
            errors.add(new SemanticError(exception.getMessage()));
        }

        return errors;
    }
    
}
