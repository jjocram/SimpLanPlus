package it.azzalinferrati.ast.node.statement;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

/**
 * Represents a wrapper for a return statement in the AST.
 */
public class RetStatNode extends StatementNode {

    final private RetNode ret;

    public RetStatNode(final RetNode ret) {
        this.ret = ret;
    }

    @Override
    public String toPrint(String indent) {
        return indent + ret;
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return ret.typeCheck();
    }

    @Override
    public boolean hasReturnStatements() {
        return true;
    }

    @Override
    public String codeGeneration() {
        ret.setEndFunctionLabel(getEndFunctionLabel());
        return ret.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return ret.checkSemantics(env);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return ret.checkEffects(env);
    }

}
