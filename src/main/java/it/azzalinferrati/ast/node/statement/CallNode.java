package it.azzalinferrati.ast.node.statement;

import it.azzalinferrati.ast.node.IdNode;
import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.expression.ExpNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;
import java.util.List;

public class CallNode implements Node {
    final private IdNode id;
    final private List<ExpNode> params;

    public CallNode(IdNode id, List<ExpNode> params) {
        this.id = id;
        this.params = params;
    }

    @Override
    public String toPrint(String indent) {
        String parametersToPrint = params.stream().map((argNode) -> argNode.toPrint(indent)).reduce("(", (arg1, arg2) -> (arg1.equals("(") ? "(" : (arg1 + ",")) + arg2);
        parametersToPrint += ")";
        return indent + "call " + id.toPrint(indent) + parametersToPrint;
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
