package it.azzalinferrati.ast.node.statement;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

public class AssigtStatNode extends StatementNode {

    final private AssignmentNode assignment;

    public AssigtStatNode(final AssignmentNode assignment) {
        this.assignment = assignment;
    }

    @Override
    public String toPrint(String indent) {
        return indent + assignment.toPrint("");
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return assignment.typeCheck();
    }

    @Override
    public String codeGeneration() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return assignment.checkSemantics(env);
    }
    
}
