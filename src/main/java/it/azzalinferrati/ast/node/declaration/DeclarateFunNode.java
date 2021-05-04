package it.azzalinferrati.ast.node.declaration;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;

public class DeclarateFunNode extends DeclarationNode {

    private final DecFunNode decFun;

    public DeclarateFunNode(final DecFunNode decFun) {
        this.decFun = decFun;
    }

    @Override
    public String toPrint(String indent) {
        return indent + decFun.toPrint(" ");
    }

    @Override
    public Node typeCheck() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String codeGeneration() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        // TODO Auto-generated method stub
        return null;
    }

}
