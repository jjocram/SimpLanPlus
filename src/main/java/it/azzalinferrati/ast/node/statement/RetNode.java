package it.azzalinferrati.ast.node.statement;

import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.expression.ExpNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;

public class RetNode implements Node {
    final private ExpNode exp;

    public RetNode(ExpNode exp) {
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "return " + exp.toPrint(indent);
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
