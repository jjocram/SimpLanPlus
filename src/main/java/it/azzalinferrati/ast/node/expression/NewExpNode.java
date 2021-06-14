package it.azzalinferrati.ast.node.expression;

import it.azzalinferrati.ast.node.IdNode;
import it.azzalinferrati.ast.node.type.PointerTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;
import java.util.List;

public class NewExpNode extends ExpNode{
    final private TypeNode type;

    public NewExpNode(TypeNode type) {
        this.type = type;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "new";
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        /*
            It's wrong just to return type!
            Example:
            int a = new int; <-- WRONG
            ^int a = new int; <-- CORRECT
         */
        return new PointerTypeNode(type);
    }

    @Override
    public String codeGeneration() {
        return "li $t1 -1\nsw $t1 0($hp) ;saves in the first free position of the heap the value 0 (automatically set the address used in $a0)\n";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public List<IdNode> variables() {
        return new ArrayList<>(); // Never will it have any variables in it.
    }
}
