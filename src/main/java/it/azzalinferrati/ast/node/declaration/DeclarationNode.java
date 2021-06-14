package it.azzalinferrati.ast.node.declaration;

import it.azzalinferrati.ast.node.Node;

public abstract class DeclarationNode implements Node {
    @Override
    public String toString() {
        return toPrint("");
    }
}
