package it.azzalinferrati.lexer.node.expression;

import it.azzalinferrati.lexer.node.Node;
import it.azzalinferrati.lexer.node.statement.CallNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;

import java.util.ArrayList;

public class CallExpNode extends ExpNode{
    final private CallNode call;

    public CallExpNode(CallNode call) {
        this.call = call;
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
