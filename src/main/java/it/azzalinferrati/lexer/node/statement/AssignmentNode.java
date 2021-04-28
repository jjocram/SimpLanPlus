package it.azzalinferrati.lexer.node.statement;

import it.azzalinferrati.lexer.node.LhsNode;
import it.azzalinferrati.lexer.node.Node;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;

import java.util.ArrayList;

public class AssignmentNode implements Node {
    final private LhsNode lhs;
    final private Node exp;

    public AssignmentNode(LhsNode lhs, Node exp) {
        this.lhs = lhs;
        this.exp = exp;
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
