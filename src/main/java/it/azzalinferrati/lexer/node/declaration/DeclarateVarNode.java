package it.azzalinferrati.lexer.node.declaration;

import java.util.ArrayList;

import it.azzalinferrati.lexer.node.Node;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;

public class DeclarateVarNode extends DeclarationNode {

    private final DecVarNode decVar;

    public DeclarateVarNode(final DecVarNode decVar) {
        this.decVar = decVar;
    }

    @Override
    public String toPrint(String indent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Node typeCheck() {
        // TODO Auto-generated method stub
        return null;
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
