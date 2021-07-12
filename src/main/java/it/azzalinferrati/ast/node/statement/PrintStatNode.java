package it.azzalinferrati.ast.node.statement;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

/**
 * <p>Represents a wrapper for a print statement in the AST.</p>
 * 
 * <p><strong>Type checking</strong>: {@code void} if the expression is correctly typed, throws a type checking exception if it is not.</p>
 * <p><strong>Semantic analysis</strong>: it performs the semantic analysis on the expression and returns its results.</p>
 * <p><strong>Code generation</strong>: Generates the value of the expression in <strong>$a0</strong> and prints it.</p>
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
    public String codeGeneration() {
        return print.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return print.checkSemantics(env);
    }
    
}
