package it.azzalinferrati.lexer.node.statement;

import it.azzalinferrati.lexer.node.Node;
import it.azzalinferrati.lexer.node.expression.ExpNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;

import java.util.ArrayList;
import java.util.List;

public class CallNode implements Node {
    final private String id;
    final private List<ExpNode> args;

    public CallNode(String id, List<ExpNode> args) {
        this.id = id;
        this.args = args;
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
