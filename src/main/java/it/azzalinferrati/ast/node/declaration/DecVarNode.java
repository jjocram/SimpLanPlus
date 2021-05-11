package it.azzalinferrati.ast.node.declaration;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.IdNode;
import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.expression.ExpNode;
import it.azzalinferrati.ast.node.type.PointerTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
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
        if (exp != null) {
            System.out.println("EXP: " + exp.typeCheck() + " TYPE: " + type.getClass());
            // exp.typeCheck() == null => exp è new
            if ((exp.typeCheck() == null && !(type instanceof PointerTypeNode))) {
                throw new TypeCheckingException("Expression new cannot be used with a non-pointer");
            } else {
                // type must be <= to exp.typeCheck()
                if (exp.typeCheck() != null && !Node.isSubtype(type, exp)) {
                    throw new TypeCheckingException("Expression: " + exp.toPrint("") + " cannot be assigned to " + id.toPrint("") + " of type " + type.toPrint(""));
                }
            }
        }
        return null; // Nothing to return
    }

    @Override
    public String codeGeneration() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        // TODO Auto-generated method stub
        return null;
    }

}
