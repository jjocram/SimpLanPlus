package it.azzalinferrati.lexer.node.statement;

import it.azzalinferrati.lexer.node.Node;
import it.azzalinferrati.lexer.node.expression.ExpNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;

import java.util.ArrayList;

public class IteNode implements Node {
    final private ExpNode condition;
    final private StatementNode thenStatement;
    final private StatementNode elseStatement;

    public IteNode(ExpNode condition, StatementNode thenStatement, StatementNode elseStatement) {
        this.condition = condition;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public String toPrint(String indent) {
        return null;
    }

    @Override
    public Node typeCheck() {
        return null;
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }
}
