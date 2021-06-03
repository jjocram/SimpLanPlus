package it.azzalinferrati.ast.node.statement;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

public class BlockStatNode extends StatementNode {

    final private BlockNode block;

    public BlockStatNode(final BlockNode block) {
        this.block = block;
    }

    @Override
    public String toPrint(String indent) {
        return block.toPrint(indent);
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return block.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return block.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return block.checkSemantics(env);
    }
    
}
