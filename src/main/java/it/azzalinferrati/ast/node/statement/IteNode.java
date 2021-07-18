package it.azzalinferrati.ast.node.statement;

import it.azzalinferrati.LabelManager;
import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.expression.ExpNode;
import it.azzalinferrati.ast.node.type.BoolTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;

/**
 * <p>Represents a if-then-else statement in the AST.</p>
 *
 * <p><strong>Type checking</strong>: if there are both then and else statements then the the type of the first branch is returned (they are nonetheless equal), throws an error if the condition is not boolean or if the two branches have different types.</p>
 * <p><strong>Semantic analysis</strong>: it performs the semantic analysis on both the branches of the if-then-else statement, on the condition and returns the maximum effects applied to the variables.</p>
 * <p><strong>Code generation</strong>: Generates the code for the boolean condition, generates the two branches's code and sets the branching labels.</p>
 */
public class IteNode implements Node {
    final private ExpNode condition;
    final private StatementNode thenBranch;
    final private StatementNode elseBranch;

    public IteNode(ExpNode condition, StatementNode thenBranch, StatementNode elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    public void setEndFunctionLabel(String endFunctionLabel) {
        thenBranch.setEndFunctionLabel(endFunctionLabel);
        if (elseBranch != null) {
            elseBranch.setEndFunctionLabel(endFunctionLabel);
        }
    }

    @Override
    public String toPrint(String indent) {
        return indent + "If cond.:\t" + condition + "\n" + indent + "Then branch:\n"
                + thenBranch.toPrint(indent) + "\n" + indent
                + (elseBranch != null ? "Else branch:\n" + elseBranch.toPrint(indent) : "");
    }

    @Override
    public String toString() {
        return toPrint("");
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        if (!(condition.typeCheck() instanceof BoolTypeNode)) {
            throw new TypeCheckingException("Condition " + condition + " is not of type bool.");
        }

        if (elseBranch != null && !Node.isSubtype(thenBranch.typeCheck(), elseBranch.typeCheck())) {
            throw new TypeCheckingException("\"Then\" branch and \"Else\" branch have different return types.");
        }

        return thenBranch.typeCheck(); // thenBranch has the same type of elseBranch
    }

    @Override
    public String codeGeneration() {
        StringBuilder buffer = new StringBuilder();

        String thenBranchLabel = LabelManager.getInstance().freshLabel("thenBranch");
        String endIfLabel = "endIf" + thenBranchLabel;

        buffer.append("; BEGIN if with condition: " + condition).append("\n");

        buffer.append(condition.codeGeneration());
        buffer.append("li $t1 1 ; load in $t1 the value TRUE\n");
        buffer.append("beq $a0 $t1 ").append(thenBranchLabel).append(" ; compare what is in $a0 (the expression) with TRUE ($t1)\n");
        if (elseBranch != null) {
            buffer.append(elseBranch.codeGeneration());
        }
        buffer.append("b ").append(endIfLabel).append(" ;if the condition was false skip to the end of if-then-else\n");
        buffer.append(thenBranchLabel).append(":\n");
        buffer.append(thenBranch.codeGeneration());
        buffer.append(endIfLabel).append(":\n");

        buffer.append("; END if with condition: " + condition).append("\n");

        return buffer.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        /*
         * Inference rule for If-Then-Else statement, following the lesson on Semantic
         * Analysis, Effects Analysis.
         *
         * env |- condition : env0    env0 |- thenBranch : env1    env0 |- elseBranch : env2
         * ---------------------------------------------------------------------------------[If-e]
         *   env |- 'if' '(' condition ')' thenBranch 'else' elseBranch : max(env1, env2)
         */

        errors.addAll(condition.checkSemantics(env)); // env |- condition : env0

        if (elseBranch == null) {
            errors.addAll(thenBranch.checkSemantics(env)); // env0 |- thenBranch : env1 (no else branch)
        } else {
            var thenBranchEnv = new Environment(env);
            errors.addAll(thenBranch.checkSemantics(thenBranchEnv)); // env0 |- thenBranch : env1

            var elseBranchEnv = new Environment(env);
            errors.addAll(elseBranch.checkSemantics(elseBranchEnv)); // env0 |- elseBranch : env2
        }

        return errors;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(condition.checkEffects(env));

        if (elseBranch == null) {
            errors.addAll(thenBranch.checkEffects(env)); // env0 |- thenBranch : env1 (no else branch)
        } else {
            var thenBranchEnv = new Environment(env);
            errors.addAll(thenBranch.checkEffects(thenBranchEnv)); // env0 |- thenBranch : env1

            var elseBranchEnv = new Environment(env);
            errors.addAll(elseBranch.checkEffects(elseBranchEnv)); // env0 |- elseBranch : env2

            env.replace(Environment.max(thenBranchEnv, elseBranchEnv)); // In here dom(thenBranchEnv) == dom(elseBranchEnv)
        }
        return null;
    }

    public boolean hasReturnStatements() {
        return thenBranch.hasReturnStatements() || (elseBranch != null && elseBranch.hasReturnStatements());
    }

    public boolean hasElseBranch() {
        return elseBranch != null;
    }
}
