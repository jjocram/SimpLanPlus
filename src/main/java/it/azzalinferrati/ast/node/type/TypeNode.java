package it.azzalinferrati.ast.node.type;

import it.azzalinferrati.ast.node.Node;

/**
 * <p>Represents a generic type token in the AST.</p>
 *
 * <p><strong>Type checking</strong>: {@code null}.</p>
 * <p><strong>Semantic analysis</strong>: empty</p>
 * <p><strong>Code generation</strong>: empty.</p>
 */
public abstract class TypeNode implements Node {
    @Override
    public String toString() {
        return toPrint("");
    }
}
