package it.azzalinferrati.ast.node.type;

import it.azzalinferrati.ast.node.Node;

/**
 * Represents a generic type token in the AST.
 */
public abstract class TypeNode implements Node {
    @Override
    public String toString() {
        return toPrint("");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        return true;
    }

    /**
     * Returns the number of possible dereferentiations with this type.
     * @return an integer >= 1.
     */
    public int getDereferenceLevel() {
        return 1;
    }
}
