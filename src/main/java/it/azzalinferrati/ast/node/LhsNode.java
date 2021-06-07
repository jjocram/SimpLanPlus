package it.azzalinferrati.ast.node;

import it.azzalinferrati.ast.node.type.PointerTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;

public class LhsNode implements Node {
    final private IdNode id;
    // LhsNode is just a plain identifier only when lhs == null.
    final private LhsNode lhs;

    public LhsNode(IdNode id, LhsNode lhs) {
        this.id = id;
        this.lhs = lhs;
    }

    public IdNode getId() {
        return id;
    }

    @Override
    public String toPrint(String indent) {
        if(lhs == null) {
            return indent + id.toPrint("");
        }
        
        return lhs.toPrint("") + "^";
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        if(lhs == null){
            // Simple ID
            return id.typeCheck();
        }

        // Dereference
        return ((PointerTypeNode) lhs.typeCheck()).getPointedType();
        /*
        ^int y = new int;
        y^ = 1;
        */

        /*
        ^^int y = new ^int;
        ^int x = new int
        ^y = x
        */

        /*
        int a = 3;
        int *pa = &a;
        int **ppa = &&a;
        int ***pppa = &a; //wrong type
        */
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    public String codeGenerationGetValue() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(id.codeGeneration());

        LhsNode current = lhs;
        while (current != null) {
            buffer.append("lw $a0 0($a0) ;").append(id.getId()).append("is a pointer, dereferencing it \n");
            current = current.lhs;
        }

        return buffer.toString();
    }

    public String codeGenerationGetAddress() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("mv $al $fp ; [get address of an LHS node pt1] i want the memory address of ").append(id.getId()).append("\n");
        for (int i = 0; i < (id.getCurrentNestingLevel() - id.getNestingLevel()); i++) {
            buffer.append("lw $al 0($al)\n");
        }

        buffer.append("addi $a0 $al ").append(-id.getOffset()).append(" ;[get address of an LHS node pt2] get the address and put in $a0\n");

        LhsNode current = lhs;
        while (current != null) {
            buffer.append("lw $a0 0($a0) ;[get address of an LHS node pt3] dereferncig the address the number of times required\n");
            current = current.lhs;
        }

        return buffer.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        if(lhs == null) {
            return id.checkSemantics(env);
        }

        return lhs.checkSemantics(env);
    }
}
