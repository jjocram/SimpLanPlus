package it.azzalinferrati.ast.node.expression;

import it.azzalinferrati.ast.node.IdNode;
import it.azzalinferrati.ast.node.type.IntTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;
import java.util.List;

public class NegativeExpNode extends ExpNode {
    final private ExpNode exp;

    public NegativeExpNode(ExpNode exp) {
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        return "-" + exp.toPrint(indent);
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        if(!(exp.typeCheck() instanceof IntTypeNode)){
            throw new TypeCheckingException("Expression: " + exp.toPrint("") + " must be of type int");
        }
        
        return new IntTypeNode();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(exp.checkSemantics(env));

        errors.addAll(checkVariablesStatus());

        return errors;
    }

    @Override
    public List<IdNode> variables() {
        return exp.variables();
    }
}
