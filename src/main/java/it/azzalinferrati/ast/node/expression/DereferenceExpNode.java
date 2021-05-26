package it.azzalinferrati.ast.node.expression;

import it.azzalinferrati.ast.node.IdNode;
import it.azzalinferrati.ast.node.LhsNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;
import java.util.List;

public class DereferenceExpNode extends ExpNode{
    final private LhsNode lhs;

    public DereferenceExpNode(LhsNode lhs) {
        this.lhs = lhs;
    }

    @Override
    public String toPrint(String indent) {
        return indent + lhs.toPrint(indent);
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return lhs.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return lhs.checkSemantics(env);
    }

    @Override
    public List<IdNode> variables() {
        List<IdNode> variable = new ArrayList<>();

        variable.add(lhs.getId());

        return variable;
    }
}
