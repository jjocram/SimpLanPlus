package it.azzalinferrati.ast.node;

import it.azzalinferrati.ast.node.type.PointerTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;

/**
 * <p>Represents a Left-hand-side expression and a node for dereferencing variables and pointers in the AST.</p>
 *
 * <p><strong>Type checking</strong>: type available in the Symbol Table if there are no {@code ^} with the identifier {@code id}, otherwise the type pointed by {@code lhs}.</p>
 * <p><strong>Semantic analysis</strong>: if the node is just a plain identifier, then its error are retrieved, otherwise errors are retrieved from the underlying {@code lhs}.</p>
 * <p><strong>Code generation</strong>: Two types:
 * <ul>
 * <li>Used in a <em>RHS</em>: the code for the identifier is generated and its value can be retrieved from <strong>$a0</strong>, then we dereference once for each symbol {@code ^} present together with the identifier.</li>
 * <li>Used in a <em>LHS</em>: the static chain is gone through until the scope in which the identifier was defined is found, then the variable value is retrieved and finally dereferenced if needed.</li>
 * </ul>
 * </p>
 */
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
    public String toString() {
        return toPrint("");
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        if(lhs == null){
            // Simple ID
            return id.typeCheck();
        }

        // Dereference
        return ((PointerTypeNode) lhs.typeCheck()).getPointedType();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    public String codeGenerationGetValue() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(id.codeGeneration());

        LhsNode current = lhs;
        while (current != null) {
            buffer.append("lw $a0 0($a0) ;").append(id.getId()).append(" is a pointer, dereferencing it \n");
            current = current.lhs;
        }

        return buffer.toString();
    }

    public String codeGenerationGetAddress() {
        StringBuilder buffer = new StringBuilder();

        if (id.getNestingLevel() == id.getCurrentNestingLevel()) {
            buffer.append("mv $al $fp ;the variable is declared in the same scope where it is used\n");
        }
        else {
            buffer.append("lw $al 0($fp) ; [get address of an LHS node pt1] i want the memory address of ").append(id.getId()).append("\n");
            for (int i = 0; i < (id.getCurrentNestingLevel() - id.getNestingLevel()) - 1; i++) {
                buffer.append("lw $al 0($al)\n");
            }
        }

        int offsetWithAL = -(id.getOffset() + 1);
        buffer.append("addi $a0 $al ").append(offsetWithAL).append(" ;[get address of an LHS node pt2] get the address and put in $a0\n");

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

    public boolean isPointer() {
        return id.getSTEntry().getType() instanceof PointerTypeNode;
    }
}
