package it.azzalinferrati.ast.node.statement;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

/**
 * Represents a wrapper for a deletion statement in the AST.
 */
public class DelStatNode extends StatementNode {

    final private DeletionNode deletion;

    public DelStatNode(final DeletionNode deletion) {
        this.deletion = deletion;
    }

    @Override
    public String toPrint(String indent) {
        return indent + deletion;
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return deletion.typeCheck();
    }

    @Override
    public boolean hasReturnStatements() {
        return false;
    }

    @Override
    public String codeGeneration() {
        return deletion.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return deletion.checkSemantics(env);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return deletion.checkEffects(env);
    }

}
