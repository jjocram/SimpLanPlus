package it.azzalinferrati.ast.node.statement;

import it.azzalinferrati.ast.node.LhsNode;
import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.expression.DereferenceExpNode;
import it.azzalinferrati.ast.node.expression.ExpNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.ast.node.type.VoidTypeNode;
import it.azzalinferrati.semanticanalysis.Effect;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;

/**
 * Represents the wrapper for an assignment statement.
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

        if (lhsType.equals(expType)) {
            return new VoidTypeNode();
        }

        throw new TypeCheckingException("Expression: " + exp + " of type " + exp.typeCheck() + " cannot be assigned to " + lhs.getId() + " of type " + lhsType + ".");
    }

    @Override
    public String codeGeneration() {
        String begin = "; BEGIN " + this + "\n";
        String end = "; END " + this + "\n";
        return begin + exp.codeGeneration() +
                "push $a0 ;push the generated expression\n" +
                lhs.codeGeneration() +
                "lw $t1 0($sp) ;load the expression from the stack\n" +
                "pop ;pop the expression from the stack\n" +
                "sw $t1 0($a0) ; store at $a0 dereferenced the value stored in $t1\n" + end;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(lhs.checkSemantics(env));
        errors.addAll(exp.checkSemantics(env));

        return errors;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(lhs.checkEffects(env));
        errors.addAll(exp.checkEffects(env));

        if (lhs.getId().getStatus(lhs.getDereferenceLevel()).equals(Effect.ERROR)) {
            errors.addAll(env.checkVariableStatus(lhs, Effect::seq, Effect.READ_WRITE));
        } else if (exp instanceof DereferenceExpNode) {
            // Since a copy of a pointer/variable is required therefore a copy of the applied effects is also performed.
            // It is okay to copy effects even for plain variables since the only possible status they would have is Effect.READ_WRITE.
            LhsNode rhsPointer = exp.variables().get(0);
            int lhsDerefLvl = lhs.getDereferenceLevel();
            int expDerefLvl = rhsPointer.getDereferenceLevel();
            int lhsMaxDerefLvl = lhs.getId().getSTEntry().getMaxDereferenceLevel();

            for (int i = lhsDerefLvl, j = expDerefLvl; i < lhsMaxDerefLvl; i++, j++) {
                var rhsStatus = rhsPointer.getId().getStatus(j);
                lhs.getId().setStatus(rhsStatus, i);
            }
        } else { // lhs is not in error status and exp is not a pointer.
            lhs.getId().setStatus(new Effect(Effect.READ_WRITE), lhs.getDereferenceLevel());
        }

        return errors;
    }
}
