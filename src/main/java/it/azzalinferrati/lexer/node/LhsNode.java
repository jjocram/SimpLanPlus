package it.azzalinferrati.lexer.node;

import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;

import java.util.ArrayList;

public class LhsNode implements Node{
    final private String id;
    final private Node lhs;

    public LhsNode(String id, Node lhs) {
        this.id = id;
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
