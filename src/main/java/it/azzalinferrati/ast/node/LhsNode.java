package it.azzalinferrati.ast.node;

import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;

import java.util.ArrayList;

public class LhsNode implements Node{
    final private IdNode id;
    // LhsNode is just a plain identifier only when lhs == null.
    final private LhsNode lhs;

    public LhsNode(IdNode id, LhsNode lhs) {
        this.id = id;
        this.lhs = lhs;
    }

    public IdNode getId() {
        return id;
    }

    @Override
    public String toPrint(String indent) {
        if(lhs == null) {
            return indent + id.toPrint("");
        }
        
        return lhs.toPrint("") + "^";
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
