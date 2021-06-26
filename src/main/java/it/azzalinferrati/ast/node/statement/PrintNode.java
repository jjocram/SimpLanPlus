package it.azzalinferrati.ast.node.statement;

import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.expression.ExpNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.ast.node.type.VoidTypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;

/**
 * <p>Represents a print statement in the AST.</p>
 * 
 * <p><strong>Type checking</strong>: {@code void} if the expression is correctly typed, throws a type checking exception if it is not.</p>
 * <p><strong>Semantic analysis</strong>: it performs the semantic analysis on the expression and returns its results.</p>
 * <p><strong>Code generation</strong>: Generates the value of the expression in <strong>$a0</strong> and prints it.</p>
 */
public class PrintNode implements Node {
    final private ExpNode exp;

    public PrintNode(ExpNode exp) {
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "print: " + exp.toPrint(indent);
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        TypeNode expType = exp.typeCheck(); // Executes the type checking on the expression (throwing exceptions if needed)

        if(expType instanceof VoidTypeNode) {
            throw new TypeCheckingException("Print statements cannot output void-returning functions");
        }

        return new VoidTypeNode();
    }

    @Override
    public String codeGeneration() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(exp.codeGeneration());
        buffer.append("print $a0\n");
        return buffer.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return exp.checkSemantics(env);
    }
}
