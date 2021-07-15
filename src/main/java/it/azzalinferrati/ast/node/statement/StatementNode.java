package it.azzalinferrati.ast.node.statement;

import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

/**
 * <p>Represents a generic statement node in the AST.</p>
 * <p>It can one of the many nodes in {@code it.azzalinferrati.ast.node.statement}.</p>
 */
public abstract class StatementNode implements Node {
    private String endFunctionLabel;

    protected void setEndFunctionLabel(String endFunctionLabel) {
        this.endFunctionLabel = endFunctionLabel;
    }

    public String getEndFunctionLabel() {
        return endFunctionLabel;
    }

    @Override
    public abstract TypeNode typeCheck() throws TypeCheckingException;

    @Override
    public String toString() {
        return toPrint("");
    }

    public abstract boolean hasReturnStatements();
}
