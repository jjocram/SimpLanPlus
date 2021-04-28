package it.azzalinferrati.lexer.node.expression;

import it.azzalinferrati.lexer.node.LhsNode;
import it.azzalinferrati.lexer.node.Node;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;

import java.util.ArrayList;

public class DereferenceExpNode extends ExpNode{
    final private LhsNode lhs;

    public DereferenceExpNode(LhsNode lhs) {
        this.lhs = lhs;
    }

    @Override
    public String toPrint(String indent) {
        return null;
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
