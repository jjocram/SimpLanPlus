package it.azzalinferrati.ast.node.expression;

import it.azzalinferrati.ast.node.IdNode;
import it.azzalinferrati.ast.node.LhsNode;
import it.azzalinferrati.ast.node.type.BoolTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Represents an expression which value is the returned value of the wrapped expression, with the Not Boolean operator applied, in the AST.</p>
 * 
 * <p><strong>Type checking</strong>: the type returned by the expression.</p>
 * <p><strong>Semantic analysis</strong>: list of errors from the wrapped expression.</p>
 * <p><strong>Code generation</strong>: The code generated by the wrapped expression, which value is saved in <strong>$a0</strong>, is then inverted and saved again in <strong>$a0</strong>.</p>
 */
public class NotExpNode extends ExpNode{
    private final ExpNode exp;

    public NotExpNode(ExpNode exp) {
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        return "!" + exp.toPrint(indent);
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        if(!(exp.typeCheck() instanceof BoolTypeNode)){
            throw new TypeCheckingException("Expression: " + exp.toPrint("") + " must be of type bool");
        }
        
        return new BoolTypeNode();
    }

    @Override
    public String codeGeneration() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(exp.codeGeneration());
        buffer.append("not $a0 $a0\n");
        return buffer.toString();
    }
    
    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(exp.checkSemantics(env));

        errors.addAll(checkVariablesStatus(env));

        return errors;
    }

    @Override
    public List<LhsNode> variables() {
        return exp.variables();
    }
}
