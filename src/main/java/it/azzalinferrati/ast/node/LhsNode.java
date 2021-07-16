package it.azzalinferrati.ast.node;

import it.azzalinferrati.ast.node.type.PointerTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Effect;
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
    private boolean isAssignment;

    public LhsNode(IdNode id, LhsNode lhs) {
        this.id = id;
        this.lhs = lhs;
        this.isAssignment = false;
    }

    public void setAssignment(boolean assignment) {
        isAssignment = assignment;
    }

    public IdNode getId() {
        return id;
    }

    @Override
    public String toPrint(String indent) {
        if (lhs == null) {
            return indent + id;
        }

        return lhs + "^";
    }

    @Override
    public String toString() {
        return toPrint("");
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        if (lhs == null) {
            // Simple ID
            return id.typeCheck();
        }

        // Dereference
        return ((PointerTypeNode) lhs.typeCheck()).getPointedType();
    }

    @Override
    public String codeGeneration() {
        StringBuilder buffer = new StringBuilder();

        buffer.append("; BEGIN loading lhs " + this + "\n");

        if (isAssignment) {
            buffer.append("mv $al $fp ;[get address of an LHS node pt1] the variable is declared in the same scope where it is used\n");
            for (int i = 0; i < (id.getCurrentNestingLevel() - id.getNestingLevel()); i++) {
                buffer.append("lw $al 0($al)\n");
            }

            int offsetWithAL = -(id.getOffset() + 1);
            buffer.append("addi $a0 $al ").append(offsetWithAL).append(" ;[get address of an LHS node pt2] get the address and put in $a0\n");
        } else {
            buffer.append(id.codeGeneration());
        }

        LhsNode current = lhs;
        while (current != null) {
            buffer.append("lw $a0 0($a0) ;").append(id.getIdentifier()).append(" is a pointer, dereferencing it \n");
            current = current.lhs;
        }

        buffer.append("; END loading lhs " + this + "\n");

        return buffer.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        if (lhs == null) {
            return id.checkSemantics(env);
        }
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(lhs.checkSemantics(env));

        if (!id.getSTEntry().getVariableStatus(getDereferenceLevel() - 1).equals(Effect.READ_WRITE)) {
            errors.add(new SemanticError("Cannot use " + this + " since " + this.toString().substring(0, this.toString().length() - 1) + " has not status READ_WRITE."));
        }

        return errors;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return null;
    }

    public boolean isPointer() {
        return id.getSTEntry().getType() instanceof PointerTypeNode;
    }

    /**
     * Return the number of '^' in the LHSNode
     */
    public int getDereferenceLevel() {
        if (lhs == null) {
            return 0;
        }

        return 1 + lhs.getDereferenceLevel();
    }
}
