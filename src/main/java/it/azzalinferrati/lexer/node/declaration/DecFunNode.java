package it.azzalinferrati.lexer.node.declaration;

import java.util.ArrayList;
import java.util.List;

import it.azzalinferrati.lexer.node.ArgNode;
import it.azzalinferrati.lexer.node.Node;
import it.azzalinferrati.lexer.node.statement.BlockNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;

public class DecFunNode implements Node {
    final private Node type;
    final private String id;
    final private List<ArgNode> args;
    final private BlockNode block;

    public DecFunNode(final Node type, final String id, final List<ArgNode> args, final BlockNode block) {
        this.type = type;
        this.id = id;
        this.args = args;
        this.block = block;
    }

    @Override
    public String toPrint(String indent) {
        // TODO Auto-generated method stub
        return null;
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
