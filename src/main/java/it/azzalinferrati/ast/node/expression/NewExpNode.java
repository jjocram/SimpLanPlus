package it.azzalinferrati.ast.node.expression;

import it.azzalinferrati.ast.node.LhsNode;
import it.azzalinferrati.ast.node.type.PointerTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the new operator in the AST.
 */
public class NewExpNode extends ExpNode {
    final private TypeNode type;

    public NewExpNode(TypeNode type) {
        this.type = type;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "new " + type;
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return new PointerTypeNode(type);
    }

    @Override
    public String codeGeneration() {
        return "li $t1 -1\nsw $t1 0($hp) ; Saves the value -1 in the first free position of the heap (automatically set the address used in $a0)\n";
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
        return new ArrayList<>(); // Never will it have any variables in it.
    }
}
