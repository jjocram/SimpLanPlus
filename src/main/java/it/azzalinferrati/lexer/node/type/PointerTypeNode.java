package it.azzalinferrati.lexer.node.type;

import java.util.ArrayList;

import it.azzalinferrati.lexer.node.Node;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;

public class PointerTypeNode extends TypeNode {

    final TypeNode pointedType;

    public PointerTypeNode(final TypeNode pointedType) {
        this.pointedType = pointedType;
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
