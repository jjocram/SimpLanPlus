package it.azzalinferrati.ast.node.expression;

import it.azzalinferrati.ast.node.IdNode;
import it.azzalinferrati.ast.node.LhsNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Represents the dereferencing of a variable or pointer in the AST.</p>
 * 
 * <p><strong>Type checking</strong>: the type returned of the variable in the Symbol Table.</p>
 * <p><strong>Semantic analysis</strong>: list of errors from the wrapped expression.</p>
 * <p><strong>Code generation</strong>: The code generated by the wrapped expression.</p>
 */
public class DereferenceExpNode extends ExpNode {
    final private LhsNode lhs;

    public DereferenceExpNode(LhsNode lhs) {
        this.lhs = lhs;
    }

    @Override
    public String toPrint(String indent) {
        return indent + lhs.toPrint(indent);
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return lhs.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return lhs.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(lhs.checkSemantics(env));

        errors.addAll(checkVariablesStatus(env));

        return errors;
    }

    @Override
    public List<LhsNode> variables() {
        List<LhsNode> variable = new ArrayList<>();

        variable.add(lhs);

        return variable;
    }

    public boolean isPointerType() {
        return lhs.isPointer();
    }
}
