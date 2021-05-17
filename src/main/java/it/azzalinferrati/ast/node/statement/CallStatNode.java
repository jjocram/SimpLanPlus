package it.azzalinferrati.ast.node.statement;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

public class CallStatNode extends StatementNode {

    private final CallNode call;

    public CallStatNode(final CallNode call) {
        this.call = call;
    }

    @Override
    public String toPrint(String indent) {
        return indent + call.toPrint("");
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return call.typeCheck();
    }

    @Override
    public String codeGeneration() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return call.checkSemantics(env);
    }
    
}
