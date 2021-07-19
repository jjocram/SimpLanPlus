package it.azzalinferrati.ast.node.statement;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

/**
 * Represents the wrapper for an invocation of a function in the AST.
 */
public class CallStatNode extends StatementNode {

    private final CallNode call;

    public CallStatNode(final CallNode call) {
        this.call = call;
    }

    @Override
    public String toPrint(String indent) {
        return indent + call;
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return call.typeCheck();
    }

    @Override
    public boolean hasReturnStatements() {
        return false;
    }

    @Override
    public String codeGeneration() {
        return call.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return call.checkSemantics(env);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return call.checkEffects(env);
    }

}
