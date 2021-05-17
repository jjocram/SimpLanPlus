package it.azzalinferrati.ast.node.value;

import it.azzalinferrati.ast.node.expression.ExpNode;
import it.azzalinferrati.ast.node.type.BoolTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;

public class BoolNode extends ExpNode {
    final private boolean value;

    public BoolNode(boolean value) {
        this.value = value;
    }

    @Override
    public String toPrint(String indent) {
        return indent + value;
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return new BoolTypeNode();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }
}
