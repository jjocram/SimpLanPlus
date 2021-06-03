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

        //return null; // Nothing to return
        return new VoidTypeNode();
    }

    @Override
    public String codeGeneration() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(id.codeGeneration());
        buffer.append("del $a0\n");

        return buffer.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(id.checkSemantics(env));

        errors.addAll(env.checkVariableStatus(id, Effect::seq, Effect.DELETE));

        return errors;
    }
}
