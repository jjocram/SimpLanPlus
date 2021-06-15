package it.azzalinferrati.ast.node.declaration;

import it.azzalinferrati.ast.node.Node;

/**
 * <p>Represents a generic declaration node in the AST.</p>
 * <p>It can either be a {@code DeclarateVarNode} or {@code DeclarateFunNode}.</p>
 */
public abstract class DeclarationNode implements Node {
    @Override
    public String toString() {
        return toPrint("");
    }
}
