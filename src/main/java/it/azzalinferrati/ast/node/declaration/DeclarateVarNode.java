package it.azzalinferrati.ast.node.declaration;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

/**
 * <p>Represents a node in the AST which is wrapper for {@code DecVarNode}.</p>
 */
public class DeclarateVarNode extends DeclarationNode {

    private final DecVarNode decVar;

    public DeclarateVarNode(final DecVarNode decVar) {
        this.decVar = decVar;
    }

    @Override
    public String toPrint(String indent) {
        return decVar.toPrint(indent);
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return decVar.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return decVar.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return decVar.checkSemantics(env);
    }
    
}
