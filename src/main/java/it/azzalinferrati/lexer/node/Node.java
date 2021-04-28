package it.azzalinferrati.lexer.node;

import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;

import java.util.ArrayList;

public interface Node {
    String toPrint(String indent);

    // fa il type checking e ritorna:
    //  per una espressione, il suo tipo (oggetto BoolTypeNode o IntTypeNode)
    //  per una dichiarazione, "null"
    Node typeCheck();

    String codeGeneration();

    ArrayList<SemanticError> checkSemantics(Environment env);
}
