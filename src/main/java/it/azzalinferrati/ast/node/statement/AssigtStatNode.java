package it.azzalinferrati.ast.node.statement;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

/**
 * <p>Represents the wrapper for an assignment statement.</p>
 *
 * <p><strong>Type checking</strong>: {@code void} if the assignment is correct, throws a type checking exception if the assignment is incorrect.</p>
 * <p><strong>Semantic analysis</strong>: it performs the semantic analysis on both the LHS and RHS.</p>
 * <p><strong>Code generation</strong>: Generates the expression and saves its value in the stack, loads in <strong>$a0</strong> the memory address in which to save the value and then stores it there.</p>
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
