package it.azzalinferrati.ast.node.statement;

import it.azzalinferrati.ast.node.LhsNode;
import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.expression.ExpNode;
import it.azzalinferrati.ast.node.type.PointerTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;

public class AssignmentNode implements Node {
    final private LhsNode lhs;
    final private ExpNode exp;

    public AssignmentNode(LhsNode lhs, ExpNode exp) {
        this.lhs = lhs;
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        return indent + lhs.toPrint("") + " = " + exp.toPrint("");
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        // if ((exp.typeCheck() == null && !(lhs.typeCheck() instanceof PointerTypeNode))) {
        //     throw new TypeCheckingException("Expression new cannot be used with a non-pointer");
        // } else {
        //     // type must be <= to exp.typeCheck()
        //     if (exp.typeCheck() != null && !Node.isSubtype(lhs.typeCheck(), exp.typeCheck())) {
        //         throw new TypeCheckingException("Left hand side " + lhs.toPrint("") + " has different type compared to expression " + exp.toPrint(""));
        //     }
        // }
        // return null;        
        TypeNode lhsType = lhs.typeCheck();
        TypeNode expType = exp.typeCheck();

        boolean isNewExp = expType == null;

        if(isNewExp && lhsType instanceof PointerTypeNode) {
            // This represents the declaration and assignment of a pointer (e.g. "^int a; ^^^bool b; a = new; b = new;")
            return null;
        }

        // exp is not null (therefore exists)
        if(!isNewExp && Node.isSubtype(expType, lhsType)) {
            // The expression is not "new" but is an integer, a boolean or a pointer
            return null;
        }

        throw new TypeCheckingException("Expression: " + exp.toPrint("") + " cannot be assigned to " + lhs.getId().toPrint("") + " of type " + lhsType.toPrint(""));
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }
}
