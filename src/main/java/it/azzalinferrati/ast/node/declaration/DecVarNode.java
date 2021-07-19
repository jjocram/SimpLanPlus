package it.azzalinferrati.ast.node.declaration;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.IdNode;
import it.azzalinferrati.ast.node.expression.ExpNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Effect;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.MultipleDeclarationException;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

/**
 * Represents a variable declaration in the AST.
 */
public class DecVarNode extends DeclarationNode {

    final private TypeNode type;
    final private IdNode id;
    final private ExpNode exp;

    public DecVarNode(final TypeNode type, final IdNode id, final ExpNode exp) {
        this.type = type;
        this.id = id;
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        final String declaration = indent
                + "Var. dec:\t" + id + " : " + type;
        /* A declaration could be
            - type ID;
            - type ID = exp
            Exp is optional
        */
        final String offset = "\t- in the AR will be at offset: " + id.getOffset();
        final String body = exp != null ? ("\t- initialized with: " + exp) : "";


        return declaration + offset + body;
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        if (exp == null) {
            // Only declaration of variable occurs (e.g. "int a;")
            return null;
        }

        TypeNode expType = exp.typeCheck();

        if (expType.equals(type)) {
            return null;
        }

        throw new TypeCheckingException("Expression: " + exp + " cannot be assigned to " + id + " of type " + type + ".");
    }

    @Override
    public String codeGeneration() {
        String begin = "; BEGIN " + this + "\n";
        String end = "; END " + this + "\n";
        if (exp == null) {
            return begin + "addi $sp $sp -1\n" + end;
        }

        // Declaration with initialization

        return begin + exp.codeGeneration() + "push $a0\n" + end;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        if (exp != null) {
            errors.addAll(exp.checkSemantics(env));
        }

        try {
            id.setEntry(env.addNewDeclaration(id.getIdentifier(), type));
            if (exp != null) {
                id.setStatus(Effect.READ_WRITE, 0);
            }
        } catch (MultipleDeclarationException exception) {
            errors.add(new SemanticError(exception.getMessage()));
        }

        return errors;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        if (exp != null) {
            errors.addAll(exp.checkEffects(env));
        }
        
        env.addEntry(id.getIdentifier(), id.getSTEntry());

        return errors;
    }
}
