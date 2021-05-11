package it.azzalinferrati.ast.node;

import it.azzalinferrati.ast.node.type.PointerTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;

public class LhsNode implements Node{
    final private IdNode id;
    // LhsNode is just a plain identifier only when lhs == null.
    final private LhsNode lhs;

    public LhsNode(IdNode id, LhsNode lhs) {
        this.id = id;
        this.lhs = lhs;
    }

    public IdNode getId() {
        return id;
    }

    @Override
    public String toPrint(String indent) {
        if(lhs == null) {
            return indent + id.toPrint("");
        }
        
        return lhs.toPrint("") + "^";
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        if(lhs == null){
            return id.typeCheck();
        }
        return new PointerTypeNode(lhs.typeCheck());
        /*
        int a = 3;
        int *pa = &a;
        int **ppa = &&a;
        int ***pppa = &a; //wrong type
        */
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }
}
