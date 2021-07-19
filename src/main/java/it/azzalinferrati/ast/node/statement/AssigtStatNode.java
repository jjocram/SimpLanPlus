package it.azzalinferrati.ast.node.statement;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

/**
 * Represents the wrapper for an assignment statement.
 */
public class AssigtStatNode extends StatementNode {

    final private AssignmentNode assignment;

    public AssigtStatNode(final AssignmentNode assignment) {
        this.assignment = assignment;
    }

    @Override
    public String toPrint(String indent) {
        return indent + assignment;
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return assignment.typeCheck();
    }

    @Override
    public boolean hasReturnStatements() {
        return false;
    }

    @Override
    public String codeGeneration() {
        return assignment.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return assignment.checkSemantics(env);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return assignment.checkEffects(env);
    }

}
