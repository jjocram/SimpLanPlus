package it.azzalinferrati.lexer.node.statement;

import java.util.ArrayList;
import java.util.List;

import it.azzalinferrati.lexer.node.Node;
import it.azzalinferrati.lexer.node.declaration.DeclarationNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;

public class BlockNode implements Node {

    final private List<DeclarationNode> declarations;

    final private List<StatementNode> statements;

    public BlockNode(final List<DeclarationNode> declarations, final List<StatementNode> statements) {
        this.declarations = declarations;
        this.statements = statements;
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
