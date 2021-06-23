package it.azzalinferrati.ast.node.statement;

import it.azzalinferrati.ast.node.IdNode;
import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.type.PointerTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.ast.node.type.VoidTypeNode;
import it.azzalinferrati.semanticanalysis.Effect;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;

/**
 * <p>Represents a deletion statement in the AST.</p>
 * 
 * <p><strong>Type checking</strong>: {@code void} if the passed identifier is of type {@code PointerTypNode}, throws a type checking exception if it is not.</p>
 * <p><strong>Semantic analysis</strong>: it verifies the identifier actually exists and that was not previously deleted.</p>
 * <p><strong>Code generation</strong>: Retrieves the identifier address and marks it for deletion.</p>
 */
public class DeletionNode implements Node {
    final private IdNode id;

    public DeletionNode(IdNode id) {
        this.id = id;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "delete: " + id.toPrint(indent);
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        if(!(id.typeCheck() instanceof PointerTypeNode)){
            throw new TypeCheckingException("Variable " + id.toPrint("") + " is not a pointer");
        }

        return new VoidTypeNode();
    }

    @Override
    public String codeGeneration() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(id.codeGeneration());
        buffer.append("del $a0 ;mark as deleted the address in $a0\n");

        return buffer.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(id.checkSemantics(env));

        if (id.getStatus().equals(Effect.DELETE) || id.getStatus().equals(Effect.ERROR)) {
            errors.add(new SemanticError("Variable " + id.getId() + " was already deleted"));
        } else {
            errors.addAll(env.checkVariableStatus(id, Effect::seq, Effect.DELETE));
        }
        return errors;
    }
}
