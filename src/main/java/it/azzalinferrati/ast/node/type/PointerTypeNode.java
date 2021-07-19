package it.azzalinferrati.ast.node.type;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

/**
 * Represents a pointer type token in the AST.
 */
public class PointerTypeNode extends TypeNode {

    final TypeNode pointedType;

    public PointerTypeNode(final TypeNode pointedType) {
        this.pointedType = pointedType;
    }

    public TypeNode getPointedType() {
        return pointedType;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "^" + pointedType;
    }

    @Override
    public Node typeCheck() throws TypeCheckingException {
        return null; // Nothing to return
    }

    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        PointerTypeNode pointerTypeNode = (PointerTypeNode) obj;

        if (!pointedType.equals(pointerTypeNode.pointedType)) {
            return false;
        }

        return true;
    }

    @Override
    public int getDereferenceLevel() {
        return 1 + pointedType.getDereferenceLevel();
    }

}
