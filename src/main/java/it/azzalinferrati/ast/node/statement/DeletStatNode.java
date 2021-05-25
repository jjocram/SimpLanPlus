package it.azzalinferrati.ast.node.statement;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

public class DeletStatNode extends StatementNode {

    final private DeletionNode deletion;

    public DeletStatNode(final DeletionNode deletion) {
        this.deletion = deletion;
    }

    @Override
    public String toPrint(String indent) {
        return indent + deletion.toPrint("");
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return deletion.typeCheck();
    }

    @Override
    public String codeGeneration() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return deletion.checkSemantics(env);
    }
    
}
