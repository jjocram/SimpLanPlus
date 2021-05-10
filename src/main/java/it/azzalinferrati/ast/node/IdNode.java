package it.azzalinferrati.ast.node;

import java.util.ArrayList;

import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

public class IdNode implements Node {
    //TODO: extends
    private final String id;

    public IdNode(final String id) {
        this.id = id;
    }

    @Override
    public String toPrint(String indent) {
        return indent + id;
    }

    @Override
    public Node typeCheck() throws TypeCheckingException {
        return null; // Nothing to return
    }

    @Override
    public String codeGeneration() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
