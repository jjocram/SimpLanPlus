package it.azzalinferrati.ast.node.expression.value;

import it.azzalinferrati.ast.node.LhsNode;
import it.azzalinferrati.ast.node.expression.ExpNode;
import it.azzalinferrati.ast.node.type.IntTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an integer constant expression in the AST.
 */
public class NumberNode extends ExpNode {
    final private int value;

    public NumberNode(int value) {
        this.value = value;
    }

    @Override
    public String toPrint(String indent) {
        return indent + value;
    }

    @Override
    public String toString() {
        return toPrint("");
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return new IntTypeNode();
    }

    @Override
    public String codeGeneration() {
        return "li $a0 " + value + " ; load in $a0 the value: " + value + "\n";
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
    public List<LhsNode> variables() {
        return new ArrayList<>();
    }
}
