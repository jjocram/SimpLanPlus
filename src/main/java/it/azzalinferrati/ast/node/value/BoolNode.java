package it.azzalinferrati.ast.node.value;

import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.expression.ExpNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;

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
    public Node typeCheck() {
        return null;
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }
}
