package it.azzalinferrati.ast.node;

import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;

public interface Node {
    String toPrint(String indent);

    // fa il type checking e ritorna:
    //  per una espressione, il suo tipo (oggetto BoolTypeNode o IntTypeNode)
    //  per una dichiarazione, "null"
    Node typeCheck() throws TypeCheckingException;

    String codeGeneration();

    ArrayList<SemanticError> checkSemantics(Environment env);

    static boolean isSubtype(Node a, Node b) {
        return a.getClass().equals(b.getClass());
    }
}
