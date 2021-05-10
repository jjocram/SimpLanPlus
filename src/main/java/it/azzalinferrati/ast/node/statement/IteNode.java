package it.azzalinferrati.ast.node.statement;

import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.expression.ExpNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;

public class IteNode implements Node {
    final private ExpNode condition;
    final private StatementNode thenBranch;
    final private StatementNode elseBranch;

    public IteNode(ExpNode condition, StatementNode thenBranch, StatementNode elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "If cond\t>> " + condition.toPrint("") + "\n" + indent + "Then stmt\t>>\n" +
                thenBranch.toPrint(indent) + "\n" + indent + "Else stmt\t>>\n" +
                (elseBranch != null ? elseBranch.toPrint(indent) : "");
    }

    @Override
    public Node typeCheck() throws TypeCheckingException {
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
