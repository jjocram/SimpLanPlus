package it.azzalinferrati.ast.node.statement;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

/**
 * <p>Represents a wrapper for an if-then-else statement in the AST.</p>
 * 
 * <p><strong>Type checking</strong>: if there are both then and else statements then the the type of the first branch is returned (they are nonetheless equal), throws an error if the condition is not boolean or if the two branches have different types.</p>
 * <p><strong>Semantic analysis</strong>: it performs the semantic analysis on both the branches of the if-then-else statement, on the condition and returns the maximum effects applied to the variables.</p>
 * <p><strong>Code generation</strong>: Generates the code for the boolean condition, generates the two branches's code and sets the branching labels.</p>
 */
public class IteStatNode extends StatementNode {
    
    final private IteNode ite;

    public IteStatNode(final IteNode ite) {
        this.ite = ite;
    }

    @Override
    public String toPrint(String indent) {
        return ite.toPrint(indent);
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return ite.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return ite.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return ite.checkSemantics(env);
    }
    
}
