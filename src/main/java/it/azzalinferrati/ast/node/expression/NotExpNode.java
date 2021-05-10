package it.azzalinferrati.ast.node.expression;

import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;

public class NotExpNode extends ExpNode{
    private final ExpNode exp;

    public NotExpNode(ExpNode exp) {
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        return "!" + exp.toPrint(indent);
    }

    @Override
    public Node typeCheck() throws TypeCheckingException {
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
