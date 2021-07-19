package it.azzalinferrati.ast.node.expression;

import it.azzalinferrati.ast.node.LhsNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;
import it.azzalinferrati.semanticanalysis.Effect;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the dereferencing of a variable or pointer in the AST.
 */
public class DereferenceExpNode extends ExpNode {
    final private LhsNode lhs;

    public DereferenceExpNode(LhsNode lhs) {
        this.lhs = lhs;
    }

    @Override
    public String toPrint(String indent) {
        return indent + lhs.toPrint(indent);
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return lhs.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return lhs.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return lhs.checkSemantics(env);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(lhs.checkEffects(env));

        if (lhs.getId().getStatus(lhs.getDereferenceLevel()).equals(Effect.INITIALIZED)) {
            errors.add(new SemanticError(lhs + " is used prior to initialization."));
        }

        errors.addAll(checkVariablesStatus(env));

        return errors;
    }

    @Override
    public List<LhsNode> variables() {
        List<LhsNode> variable = new ArrayList<>();

        variable.add(lhs);

        return variable;
    }

    public boolean isPointerType() {
        return lhs.isPointer();
    }
}
