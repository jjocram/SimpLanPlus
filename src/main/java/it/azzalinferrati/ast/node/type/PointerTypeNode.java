package it.azzalinferrati.ast.node.type;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

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
        return indent + "^" + pointedType.toPrint("");
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
    
}
