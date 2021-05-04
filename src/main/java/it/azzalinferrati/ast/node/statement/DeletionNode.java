package it.azzalinferrati.ast.node.statement;

import it.azzalinferrati.ast.node.IdNode;
import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;

import java.util.ArrayList;

public class DeletionNode implements Node {
    final private IdNode id;

    public DeletionNode(IdNode id) {
        this.id = id;
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
