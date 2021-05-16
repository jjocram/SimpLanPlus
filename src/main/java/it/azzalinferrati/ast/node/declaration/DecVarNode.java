package it.azzalinferrati.ast.node.declaration;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.IdNode;
import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.expression.ExpNode;
import it.azzalinferrati.ast.node.type.PointerTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.MultipleDeclarationException;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

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
                + "Variable\t>> " + id.toPrint(" ") + " : " + type.toPrint("");
        /* A declaration could be
            - type ID;
            - type ID = exp
            Exp is optional
        */
        final String body = exp != null ? ("\t- initialized with: " + exp.toPrint("")) : "";

        return declaration + body;
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        if(exp == null) {
            // Only declaration of variable occurs (e.g. "int a;")
            return null;
        }
        
        TypeNode expType = exp.typeCheck();

        boolean isNewExp = expType == null;

        if(isNewExp && type instanceof PointerTypeNode) {
            // This represents the declaration and assignment of a pointer (e.g. "^int a = new; ^^^bool b = new;")
            return null;
        }

        // exp is not null (therefore exists)
        if(!isNewExp && Node.isSubtype(expType, type)) {
            // The expression is not "new" but is an integer, a boolean or a pointer
            return null;
        }

        throw new TypeCheckingException("Expression: " + exp.toPrint("") + " cannot be assigned to " + id.toPrint("") + " of type " + type.toPrint(""));
    }

    @Override
    public String codeGeneration() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(exp.checkSemantics(env));

        try {
            id.setEntry(env.addNewDeclaration(id.getId(), type));
        } catch (MultipleDeclarationException exception) {
            errors.add(new SemanticError(exception.getMessage()));
        }

        return errors;
    }
}
