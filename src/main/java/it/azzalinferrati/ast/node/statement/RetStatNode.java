package it.azzalinferrati.ast.node.statement;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

public class RetStatNode extends StatementNode {

    final private RetNode ret;

    public RetStatNode(final RetNode ret) {
        this.ret = ret;
    }

    @Override
    public String toPrint(String indent) {
        return indent + ret.toPrint("");
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return ret.typeCheck();
    }

    @Override
    public String codeGeneration() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return ret.checkSemantics(env);
    }
    
}
