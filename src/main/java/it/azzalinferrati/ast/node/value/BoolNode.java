package it.azzalinferrati.ast.node.value;

import it.azzalinferrati.ast.node.IdNode;
import it.azzalinferrati.ast.node.expression.ExpNode;
import it.azzalinferrati.ast.node.type.BoolTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;
import java.util.List;

public class BoolNode extends ExpNode {
    final private boolean value;

    public BoolNode(boolean value) {
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
        return new BoolTypeNode();
    }

    @Override
    public String codeGeneration() {
        int intValue = value ? 1 : 0;
        return "li $a0 " + intValue + " ; load in $a0 the value: " + value + "\n";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public List<IdNode> variables() {
        return new ArrayList<>();
    }
}
