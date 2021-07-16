package it.azzalinferrati.ast.node.statement;

import java.util.ArrayList;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

/**
 * <p>Represents the wrapper for an invocation of a function in the AST.</p>
 *
 * <p><strong>Type checking</strong>: the type returned by the function if the
 * function body returns a matching value, throws a type checking exception if
 * the body of the function does not match the returned type.</p>
 * <p><strong>Semantic analysis</strong>: it checks the existence of the function
 * in the Symbol Table, checks that all the actual arguments are correct and
 * finally performs the Effects Analysis.</p>
 * <p><strong>Code generation</strong>: Pushes the <strong>$fp</strong> onto the
 * stack, goes through the static chain and retrieves the correct<strong>$al</strong>
 * to be pushed onto the stack, generates the code for all the arguments (in order),
 * pushes their value onto the stack, moves the <strong>$fp</strong> back to the
 * previously pushed <strong>$al</strong> and finally jumps to the function definition
 * code.</p>
 */
public class CallStatNode extends StatementNode {

    private final CallNode call;

    public CallStatNode(final CallNode call) {
        this.call = call;
    }

    @Override
    public String toPrint(String indent) {
        return indent + call;
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        return call.typeCheck();
    }

    @Override
    public boolean hasReturnStatements() {
        return false;
    }

    @Override
    public String codeGeneration() {
        return call.codeGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return call.checkSemantics(env);
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return call.checkEffects(env);
    }

}
