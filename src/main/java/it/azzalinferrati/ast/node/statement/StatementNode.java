package it.azzalinferrati.ast.node.statement;

import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

public abstract class StatementNode implements Node {
    @Override
    public abstract TypeNode typeCheck() throws TypeCheckingException;
}
