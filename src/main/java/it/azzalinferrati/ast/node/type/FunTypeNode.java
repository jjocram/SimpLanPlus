package it.azzalinferrati.ast.node.type;

import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;
import java.util.List;

public class FunTypeNode extends TypeNode {

    final private List<TypeNode> params;
    final private TypeNode returned;

    public FunTypeNode(List<TypeNode> params, TypeNode returned) {
        this.params = params;
        this.returned = returned;
    }

    @Override
    public String toPrint(String indent) {
        final String declaration = indent +
                params.stream().map((arg) ->  arg.toPrint("")).reduce("",
                (arg1, arg2) -> (arg1.isEmpty() ? "" : (arg1 + " X ")) + arg2)
                + " -> " + returned.toPrint("");

        return declaration;
    }

    @Override
    public Node typeCheck() throws TypeCheckingException {
        return null; // Nothing to return
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
