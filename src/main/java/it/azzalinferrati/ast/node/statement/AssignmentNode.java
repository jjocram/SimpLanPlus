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
        TypeNode lhsType = lhs.typeCheck();
        TypeNode expType = exp.typeCheck();

        if(Node.isSubtype(expType, lhsType)) {
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
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(lhs.checkSemantics(env));
        errors.addAll(exp.checkSemantics(env));
        
        return errors;
    }
}
