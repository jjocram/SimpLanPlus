package it.azzalinferrati.ast.node.statement;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

public class PrintStatNode extends StatementNode {

    final private PrintNode print;

    public PrintStatNode(final PrintNode print) {
        this.print = print;
    }

    @Override
    public String toPrint(String indent) {
        return indent + print.toPrint("");
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return print.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return print.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return print.checkSemantics(env);
    }
    
}
