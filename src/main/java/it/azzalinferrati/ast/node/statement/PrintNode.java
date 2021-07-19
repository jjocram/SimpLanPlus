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
 * Represents a print statement in the AST.
 */
public class PrintNode implements Node {
    final private ExpNode exp;

    public PrintNode(ExpNode exp) {
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Print:\t" + exp.toPrint(indent);
    }

    @Override
    public String toString() {
        return toPrint("");
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        TypeNode expType = exp.typeCheck(); // Executes the type checking on the expression (throwing exceptions if needed).

        if (expType instanceof VoidTypeNode) {
            throw new TypeCheckingException("Print statements cannot output void-returning functions.");
        }

        return new VoidTypeNode();
    }

    @Override
    public String codeGeneration() {
        return exp.codeGeneration() +
                "print $a0\n";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return exp.checkSemantics(env);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return exp.checkEffects(env);
    }
}
