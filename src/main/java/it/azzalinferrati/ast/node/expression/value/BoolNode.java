package it.azzalinferrati.ast.node.expression.value;

import it.azzalinferrati.ast.node.LhsNode;
import it.azzalinferrati.ast.node.expression.ExpNode;
import it.azzalinferrati.ast.node.type.BoolTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Represents a boolean constant expression in the AST.</p>
 *
 * <p><strong>Type checking</strong>: bool.</p>
 * <p><strong>Semantic analysis</strong>: empty</p>
 * <p><strong>Code generation</strong>: loads the value in <strong>$a0</strong>.</p>
 */
public class BoolNode extends ExpNode {
    final private boolean value;

    public BoolNode(boolean value) {
        this.value = value;
    }

    @Override
    public String toPrint(String indent) {
        return indent + value;
    }

    @Override
    public String toString() {
        return toPrint("");
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return new BoolTypeNode();
    }

    @Override
    public String codeGeneration() {
        int intValue = value ? 1 : 0;
        return "li $a0 " + intValue + " ; loads in $a0 the value: " + value + " (" + intValue + ")\n";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return null;
    }

    @Override
    public List<LhsNode> variables() {
        return new ArrayList<>();
    }
}
