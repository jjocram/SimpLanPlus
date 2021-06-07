package it.azzalinferrati.ast.node;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

public class ArgNode implements Node {

    final private TypeNode type;
    final private IdNode id;

    public ArgNode(final TypeNode type, final IdNode id) {
        this.type = type;
        this.id = id;
    }

    public TypeNode getType() {
        return type;
    }

    public IdNode getId() {
        return id;
    }

    @Override
    public String toPrint(String indent) {
        return indent + id.toPrint("") + " : " + type.toPrint("") + ", " + id.getOffset();
    }

    @Override
    public Node typeCheck() throws TypeCheckingException {
        return null; // Nothing to return
    }

    @Override
    public String codeGeneration() {
        return "addi $sp $sp 1 ;allocates space on the stack for the argument";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }
    
}
