package it.azzalinferrati.ast.node.expression;

import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

public abstract class ExpNode implements Node {

    @Override
    public abstract TypeNode typeCheck() throws TypeCheckingException;
}
