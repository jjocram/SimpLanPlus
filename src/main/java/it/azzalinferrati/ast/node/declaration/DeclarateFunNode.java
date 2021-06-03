package it.azzalinferrati.ast.node.declaration;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

public class DeclarateFunNode extends DeclarationNode {

    private final DecFunNode decFun;

    public DeclarateFunNode(final DecFunNode decFun) {
        this.decFun = decFun;
    }

    @Override
    public String toPrint(String indent) {
        return decFun.toPrint(indent);
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return decFun.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return decFun.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return decFun.checkSemantics(env);
    }

}
