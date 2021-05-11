package it.azzalinferrati.ast.node;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.FunTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.STEntry;
import it.azzalinferrati.semanticanalysis.SemanticError;
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

    public TypeNode getType() {
        return entry.getType();
    }

    @Override
    public String toPrint(String indent) {
        return indent + id;
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        if(entry.getType() instanceof FunTypeNode){
            throw new TypeCheckingException("Type FunTypeNode is not allowed for identifiers");
        }

        return entry.getType();
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
