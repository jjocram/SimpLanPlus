package it.azzalinferrati.ast.node.expression;

import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.type.BoolTypeNode;
import it.azzalinferrati.ast.node.type.IntTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

import java.util.ArrayList;

public class BinaryExpNode extends ExpNode{
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
        return indent + leftExpression.toPrint("") + " " + operator + " " + rightExpression.toPrint(indent);
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        TypeNode leftType = leftExpression.typeCheck();
        TypeNode rightType = rightExpression.typeCheck();

        if (!Node.isSubtype(leftType, rightType)) {
            throw new TypeCheckingException("Left expression: " + leftExpression.toPrint("") + " has different type from right expression: " + rightExpression.toPrint(""));
        }

        //leftType and rightType have the same type

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
                if (!Node.isSubtype(leftType, new IntTypeNode()) || !Node.isSubtype(rightType, new IntTypeNode())) {
                    throw new TypeCheckingException("Expressions are not of type int");
                }
                return new IntTypeNode();
            case "<":
            case "<=":
            case ">":
            case ">=":
                if (!Node.isSubtype(leftType, new IntTypeNode()) || !Node.isSubtype(rightType, new IntTypeNode())) {
                    throw new TypeCheckingException("Expressions are not of type int");
                }
                return new BoolTypeNode();
            case "&&":
            case "||":
                // Both must be bool
                if (!Node.isSubtype(leftType, new BoolTypeNode()) || !Node.isSubtype(rightType, new BoolTypeNode())) {
                    throw new TypeCheckingException("Expressions are not of type bool");
                }
                return new BoolTypeNode();
        }

        return null; // Dead code because the switch case covers all possibilities
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
