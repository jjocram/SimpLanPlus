package it.azzalinferrati.ast.node.expression;

import it.azzalinferrati.LabelManager;
import it.azzalinferrati.ast.node.LhsNode;
import it.azzalinferrati.ast.node.type.BoolTypeNode;
import it.azzalinferrati.ast.node.type.IntTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an expression which value is the returned value of the operation applied to two operands (which are expressions themselves), in the AST.
 */
public class BinaryExpNode extends ExpNode {
    final private ExpNode leftExpression;
    final private String operator;
    final private ExpNode rightExpression;

    public BinaryExpNode(ExpNode leftExpression, String operator, ExpNode rightExpression) {
        this.leftExpression = leftExpression;
        this.operator = operator;
        this.rightExpression = rightExpression;
    }

    @Override
    public String toPrint(String indent) {
        return indent + leftExpression + " " + operator + " " + rightExpression.toPrint(indent);
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        TypeNode leftType = leftExpression.typeCheck();
        TypeNode rightType = rightExpression.typeCheck();

        if (!(leftType.equals(rightType))) {
            throw new TypeCheckingException("Left expression: " + leftExpression + " has different type from right expression: " + rightExpression + ".");
        }

        // leftType and rightType have the same type.

        switch (operator) {
            case "==":
            case "!=":
                // Only same type
                return new BoolTypeNode();
            case "*":
            case "/":
            case "+":
            case "-":
                // Both must be integer type
                if (!(leftType instanceof IntTypeNode) || !(rightType instanceof IntTypeNode)) {
                    throw new TypeCheckingException("Expressions are not of type int.");
                }
                return new IntTypeNode();
            case "<":
            case "<=":
            case ">":
            case ">=":
                if (!(leftType instanceof IntTypeNode) || !(rightType instanceof IntTypeNode)) {
                    throw new TypeCheckingException("Expressions are not of type int.");
                }
                return new BoolTypeNode();
            case "&&":
            case "||":
                // Both must be bool
                if (!(leftType instanceof BoolTypeNode) || !(rightType instanceof BoolTypeNode)) {
                    throw new TypeCheckingException("Expressions are not of type bool.");
                }
                return new BoolTypeNode();
        }

        return null; // Dead code because the switch case covers all possibilities
    }

    @Override
    public String codeGeneration() {
        StringBuilder buffer = new StringBuilder();
        LabelManager labelManager = LabelManager.getInstance();

        buffer.append("; BEGIN ").append(this).append("\n");

        buffer.append(leftExpression.codeGeneration());
        buffer.append("push $a0 ; push on the stack e1\n");
        buffer.append(rightExpression.codeGeneration());
        buffer.append("lw $t1 0($sp) ;$t1 = e1, $a0 = e2\n");
        buffer.append("pop ;pop e1 from the stack\n");


        /*
            e1 operator e1
            $t1 = e1
            $a0 = e2
            and the stack is as before
         */
        switch (operator) {
            case "==": {
                String trueBranchLabel = labelManager.freshLabel("equalTrueBranch");
                String endCheckLabel = "end" + trueBranchLabel;

                buffer.append("beq $t1 $a0 ").append(trueBranchLabel).append("\n");
                //False branch
                buffer.append("li $a0 0 ;e1 != e2\n");
                buffer.append("b ").append(endCheckLabel).append("\n");
                buffer.append(trueBranchLabel).append(":\n");
                buffer.append("li $a0 1 ;e1 == e2\n");
                buffer.append(endCheckLabel).append(":\n");
                break;
            }
            case "!=": {
                String trueBranchLabel = labelManager.freshLabel("unequalTrueBranch");
                String endCheckLabel = "end" + trueBranchLabel;

                buffer.append("beq $t1 $a0 ").append(trueBranchLabel).append("\n");
                //False branch
                buffer.append("li $a0 1 ;e1 != e2\n");
                buffer.append("b ").append(endCheckLabel).append("\n");
                buffer.append(trueBranchLabel).append(":\n");
                buffer.append("li $a0 0 ;e1 == e2\n");
                buffer.append(endCheckLabel).append(":\n");
                break;
            }
            case "*": {
                buffer.append("mult $a0 $t1 $a0\n");
                break;
            }
            case "/": {
                buffer.append("div $a0 $t1 $a0\n");
                break;
            }
            case "+": {
                buffer.append("add $a0 $t1 $a0\n");
                break;
            }
            case "-": {
                buffer.append("sub $a0 $t1 $a0\n");
                break;
            }
            case "<": {
                String equalTrueBranch = labelManager.freshLabel("equalTrueBranch");
                String endEqualCheck = "end" + equalTrueBranch;
                String lesseqTrueBranch = labelManager.freshLabel("lesseqTrueBranch");
                String endLesseqCheck = "end" + lesseqTrueBranch;

                buffer.append("beq $t1 $a0 ").append(equalTrueBranch).append("\n");
                //False branch => e1 != e2
                buffer.append("bleq $t1 $a0 ").append(lesseqTrueBranch).append("\n");
                //InnerFalse branch => e1 > e2
                buffer.append("li $a0 0\n");
                buffer.append("b ").append(endLesseqCheck).append("\n");
                buffer.append(lesseqTrueBranch).append(":\n");
                buffer.append("li $a0 1\n"); // e1 < e2
                buffer.append(endLesseqCheck).append(":\n");
                buffer.append("b ").append(endEqualCheck).append("\n");
                buffer.append(equalTrueBranch).append(":\n");
                buffer.append("li $a0 0\n");
                buffer.append(endEqualCheck).append(":\n");

                break;
            }
            case "<=": {
                String trueBranchLabel = labelManager.freshLabel("lesseqTrueBranch");
                String endCheckLabel = "end" + trueBranchLabel;

                buffer.append("bleq $t1 $a0").append(trueBranchLabel).append("\n");
                //False branch
                buffer.append("li $a0 0\n");
                buffer.append("b ").append(endCheckLabel).append("\n");
                buffer.append(trueBranchLabel).append(":\n");
                buffer.append("li $a0 1\n");
                buffer.append(endCheckLabel).append(":\n");
                break;
            }
            case ">": {
                String trueBranchLabel = labelManager.freshLabel("greaterTrueBranch");
                String endCheckLabel = "end" + trueBranchLabel;

                buffer.append("bleq $t1 $a0").append(trueBranchLabel).append("\n");
                //False branch
                buffer.append("li $a0 1\n");
                buffer.append("b ").append(endCheckLabel).append("\n");
                buffer.append(trueBranchLabel).append(":\n");
                buffer.append("li $a0 0\n");
                buffer.append(endCheckLabel).append(":\n");
                break;
            }
            case ">=": {
                String equalTrueBranch = labelManager.freshLabel("equalTrueBranch");
                String endEqualCheck = "end" + equalTrueBranch;
                String lesseqTrueBranch = labelManager.freshLabel("lesseqTrueBranch");
                String endLesseqCheck = "end" + lesseqTrueBranch;

                buffer.append("beq $t1 $a0 ").append(equalTrueBranch).append("\n");
                //False branch => e1 != e2
                buffer.append("bleq $t1 $a0 ").append(lesseqTrueBranch).append("\n");
                //InnerFalse branch => e1 > e2
                buffer.append("li $a0 1\n");
                buffer.append("b ").append(endLesseqCheck).append("\n");
                buffer.append(lesseqTrueBranch).append(":\n");
                buffer.append("li $a0 0\n"); // e1 < e2
                buffer.append(endLesseqCheck).append(":\n");
                buffer.append("b ").append(endEqualCheck).append("\n");
                buffer.append(equalTrueBranch).append(":\n");
                buffer.append("li $a0 1\n"); // e1 == e2
                buffer.append(endEqualCheck).append(":\n");

                break;
            }
            case "&&": {
                buffer.append("and $a0 $t1 $a0\n");
                break;
            }
            case "||": {
                buffer.append("or $a0 $t1 $a0\n");
                break;
            }
        }

        buffer.append("; END ").append(this).append("\n");

        return buffer.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(leftExpression.checkSemantics(env));
        errors.addAll(rightExpression.checkSemantics(env));

        return errors;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        errors.addAll(leftExpression.checkEffects(env));
        errors.addAll(rightExpression.checkEffects(env));

        errors.addAll(checkVariablesStatus(env));

        return errors;
    }

    @Override
    public List<LhsNode> variables() {
        List<LhsNode> variables = new ArrayList<>();

        variables.addAll(leftExpression.variables());
        variables.addAll(rightExpression.variables());

        return variables;
    }
}
