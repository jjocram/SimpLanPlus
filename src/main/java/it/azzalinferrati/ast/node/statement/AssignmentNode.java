package it.azzalinferrati.ast.node.statement;

import it.azzalinferrati.ast.node.LhsNode;
import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.expression.ExpNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.ast.node.type.VoidTypeNode;
import it.azzalinferrati.semanticanalysis.Effect;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;

/**
 * <p>Represents the wrapper for an assignment statement.</p>
 * 
 * <p><strong>Type checking</strong>: {@code void} if the assignment is correct, throws a type checking exception if the assignment is incorrect.</p>
 * <p><strong>Semantic analysis</strong>: it performs the semantic analysis on both the LHS and RHS.</p>
 * <p><strong>Code generation</strong>: Generates the expression and saves its value in the stack, loads in <strong>$a0</strong> the memory address in which to save the value and then stores it there.</p>
 */
public class AssignmentNode implements Node {
    final private LhsNode lhs;
    final private ExpNode exp;

    public AssignmentNode(LhsNode lhs, ExpNode exp) {
        this.lhs = lhs;
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Assignment:\t" + lhs + " = " + exp;
    }

    @Override
    public String toString() {
        return toPrint("");
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {    
        TypeNode lhsType = lhs.typeCheck();
        TypeNode expType = exp.typeCheck();

        if(Node.isSubtype(expType, lhsType)) {
            return new VoidTypeNode();
        }

        throw new TypeCheckingException("Expression: " + exp + " of type " + exp.typeCheck() + " cannot be assigned to " + lhs.getId() + " of type " + lhsType + ".");
    }

    @Override
    public String codeGeneration() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(exp.codeGeneration());
        buffer.append("push $a0 ;push the generated expression\n");
        buffer.append(lhs.codeGeneration());
        buffer.append("lw $t1 0($sp) ;load the expression from the stack\n");
        buffer.append("pop ;pop the expression from the stack\n");
        buffer.append("sw $t1 0($a0) ; store at $a0 dereferenced the value stored in $t1\n");

        return buffer.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(lhs.checkSemantics(env));
        errors.addAll(exp.checkSemantics(env));

        if (lhs.getId().getStatus(lhs.getDereferenceLevel()).equals(Effect.ERROR)) {
            errors.addAll(env.checkVariableStatus(lhs, Effect::seq, Effect.READ_WRITE));
        } else {
            lhs.getId().setStatus(new Effect(Effect.READ_WRITE), lhs.getDereferenceLevel());
        }

        return errors;
    }
}
