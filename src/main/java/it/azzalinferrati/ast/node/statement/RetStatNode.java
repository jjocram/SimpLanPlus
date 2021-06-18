package it.azzalinferrati.ast.node.statement;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

/**
 * <p>Represents a wrapper for a return statement in the AST.</p>
 * 
 * <p><strong>Type checking</strong>: {@code void} if the expression is correctly typed, throws a type checking exception if it is not.</p>
 * <p><strong>Semantic analysis</strong>: it performs the semantic analysis on the expression and returns its results.</p>
 * <p><strong>Code generation</strong>: Generates the value of the expression in <strong>$a0</strong>.</p>
 */
public class RetStatNode extends StatementNode {

    final private RetNode ret;

    public RetStatNode(final RetNode ret) {
        this.ret = ret;
    }

    @Override
    public String toPrint(String indent) {
        return indent + ret.toPrint("");
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return ret.typeCheck();
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
    
}
