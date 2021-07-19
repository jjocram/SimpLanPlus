package it.azzalinferrati.ast.node.expression;

import it.azzalinferrati.ast.node.LhsNode;
import it.azzalinferrati.ast.node.statement.CallNode;
import it.azzalinferrati.ast.node.type.FunTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents an expression which value is the returned value from the function in the AST.
 */
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
        call.typeCheck();
        return ((FunTypeNode) call.getId().typeCheck()).getReturned();
    }

    @Override
    public String codeGeneration() {
        return call.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return call.checkSemantics(env);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(call.checkEffects(env));
        errors.addAll(checkVariablesStatus(env));

        return errors;
    }

    @Override
    public List<LhsNode> variables() {
        return call.variables();
    }
}
