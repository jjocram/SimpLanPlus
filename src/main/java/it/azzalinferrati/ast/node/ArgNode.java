package it.azzalinferrati.ast.node;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;

public class ArgNode implements Node {

    final private TypeNode type;
    final private IdNode id;

    public ArgNode(final TypeNode type, final IdNode id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public String toPrint(String indent) {
        return indent + id.toPrint("") + " : " + type.toPrint("");
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
