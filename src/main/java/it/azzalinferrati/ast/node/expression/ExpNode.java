package it.azzalinferrati.ast.node.expression;

import java.util.ArrayList;
import java.util.List;

import it.azzalinferrati.ast.node.IdNode;
import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Effect;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

public abstract class ExpNode implements Node {

    @Override
    public abstract TypeNode typeCheck() throws TypeCheckingException;

    public abstract List<IdNode> variables();

    /**
     * Checks, for each variable in the list of used variables by the expression, its status and updates it,
     * if the new status is Effect.ERROR then adds a new semantic error to the list that will be returned.
     * @return a list with errors found while checking the status of each variable used in the expression
     */
    protected ArrayList<SemanticError> checkVariablesStatus() {
        ArrayList<SemanticError> errors = new ArrayList<>();

        for (IdNode variable : variables()) {
            Effect status = Effect.seq(variable.getStatus(), Effect.READ_WRITE);
            variable.setStatus(status);
            if (status == Effect.ERROR) {
                errors.add(new SemanticError("Effect analysis error"));
            }
        }

        return errors;
    }
}
