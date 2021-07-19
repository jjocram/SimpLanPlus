package it.azzalinferrati.ast.node.expression;

import it.azzalinferrati.ast.node.LhsNode;
import it.azzalinferrati.ast.node.type.IntTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an expression which value is the returned value of the wrapped expression, negated, in the AST.
 */
public class NegativeExpNode extends ExpNode {
    final private ExpNode exp;

    public NegativeExpNode(ExpNode exp) {
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        return "-" + exp.toPrint(indent);
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        if (!(exp.typeCheck() instanceof IntTypeNode)) {
            throw new TypeCheckingException("Expression: " + exp + " must be of type int.");
        }

        return new IntTypeNode();
    }

    @Override
    public String codeGeneration() {
        return exp.codeGeneration() +
                "multi $a0 $a0 -1\n";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return exp.checkSemantics(env);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(exp.checkEffects(env));

        errors.addAll(checkVariablesStatus(env));

        return errors;
    }

    @Override
    public List<LhsNode> variables() {
        return exp.variables();
    }
}
