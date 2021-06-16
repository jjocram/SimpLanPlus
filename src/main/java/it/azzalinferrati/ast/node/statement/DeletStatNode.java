package it.azzalinferrati.ast.node.statement;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

/**
 * <p>Represents a wrapper for a deletion statement in the AST.</p>
 * 
 * <p><strong>Type checking</strong>: {@code void} if the passed identifier is of type {@code PointerTypNode}, throws a type checking exception if it is not.</p>
 * <p><strong>Semantic analysis</strong>: it verifies the identifier actually exists and that was not previously deleted.</p>
 * <p><strong>Code generation</strong>: Retrieves the identifier address and marks it for deletion.</p>
 */
public class DeletStatNode extends StatementNode {

    final private DeletionNode deletion;

    public DeletStatNode(final DeletionNode deletion) {
        this.deletion = deletion;
    }

    @Override
    public String toPrint(String indent) {
        return indent + deletion.toPrint("");
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return deletion.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return deletion.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return deletion.checkSemantics(env);
    }
    
}
