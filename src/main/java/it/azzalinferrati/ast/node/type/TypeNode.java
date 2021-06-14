package it.azzalinferrati.ast.node.type;

import it.azzalinferrati.ast.node.Node;

public abstract class TypeNode implements Node {
    @Override
    public String toString() {
        return toPrint("");
    }
}
