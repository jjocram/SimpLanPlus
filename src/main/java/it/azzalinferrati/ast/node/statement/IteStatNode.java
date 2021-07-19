package it.azzalinferrati.ast.node.statement;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

/**
 * Represents a wrapper for an if-then-else statement in the AST.
 */
public class IteStatNode extends StatementNode {

    final private IteNode ite;

    public IteStatNode(final IteNode ite) {
        this.ite = ite;
    }

    @Override
    public String toPrint(String indent) {
        return ite.toPrint(indent);
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return ite.typeCheck();
    }

    @Override
    public boolean hasReturnStatements() {
        return ite.hasReturnStatements();
    }

    @Override
    public String codeGeneration() {
        ite.setEndFunctionLabel(getEndFunctionLabel());
        return ite.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return ite.checkSemantics(env);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return ite.checkEffects(env);
    }

    public boolean hasElseBranch() {
        return ite.hasElseBranch();
    }

}
