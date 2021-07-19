package it.azzalinferrati.ast.node.statement;

import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.expression.ExpNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.ast.node.type.VoidTypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;

/**
 * Represents a return statement in the AST.
 */
public class RetNode implements Node {
    final private ExpNode exp;
    private String endFunctionLabel;

    public RetNode(ExpNode exp) {
        this.exp = exp;
    }

    public void setEndFunctionLabel(String endFunctionLabel) {
        this.endFunctionLabel = endFunctionLabel;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Return:\t" + (exp == null ? "void" : exp.toPrint(indent));
    }

    @Override
    public String toString() {
        return toPrint("");
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        if (exp == null) {
            return new VoidTypeNode();
        }

        return exp.typeCheck();
    }

    @Override
    public String codeGeneration() {
        StringBuilder buffer = new StringBuilder();

        if (exp != null) {
            buffer.append(exp.codeGeneration());
        }

        buffer.append("b ").append(endFunctionLabel).append("\n");
        return buffer.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        if (exp == null) {
            return new ArrayList<>();
        }

        return exp.checkSemantics(env);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        if (exp == null) {
            return new ArrayList<>();
        }

        return exp.checkEffects(env);
    }
}
