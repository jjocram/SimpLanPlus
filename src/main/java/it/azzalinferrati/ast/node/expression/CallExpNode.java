package it.azzalinferrati.ast.node.expression;

import it.azzalinferrati.ast.node.IdNode;
import it.azzalinferrati.ast.node.statement.CallNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;
import java.util.List;

public class CallExpNode extends ExpNode {
    final private CallNode call;

    public CallExpNode(CallNode call) {
        this.call = call;
    }

    @Override
    public String toPrint(String indent) {
        return indent + call.toPrint(indent);
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return call.getId().typeCheck();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(call.checkSemantics(env));
        errors.addAll(checkVariablesStatus(env));

        return errors;
    }

    @Override
    public List<IdNode> variables() {
        return call.variables();
    }
}
