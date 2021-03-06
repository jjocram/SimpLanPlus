package it.azzalinferrati.ast.node.statement;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

/**
 * Represents a wrapper for a print statement in the AST.
 */
public class PrintStatNode extends StatementNode {

    final private PrintNode print;

    public PrintStatNode(final PrintNode print) {
        this.print = print;
    }

    @Override
    public String toPrint(String indent) {
        return indent + print;
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return print.typeCheck();
    }

    @Override
    public boolean hasReturnStatements() {
        return false;
    }

    @Override
    public String codeGeneration() {
        return print.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return print.checkSemantics(env);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return print.checkEffects(env);
    }

}
