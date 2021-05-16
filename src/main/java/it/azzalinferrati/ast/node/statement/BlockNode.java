package it.azzalinferrati.ast.node.statement;

import java.util.ArrayList;
import java.util.List;

import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.declaration.DeclarationNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.ast.node.type.VoidTypeNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;

public class BlockNode implements Node {

    final private List<DeclarationNode> declarations;

    final private List<StatementNode> statements;

    private boolean allowScopeCreation;

    public BlockNode(final List<DeclarationNode> declarations, final List<StatementNode> statements) {
        this.declarations = declarations;
        this.statements = statements;
        allowScopeCreation = true;
    }

    public void allowScopeCreation() {
        allowScopeCreation = true;
    }

    public void disallowScopeCreation() {
        allowScopeCreation = false;
    }

    @Override
    public String toPrint(String indent) {
        String declarationsToPrint = declarations.stream().map((dec1) -> dec1.toPrint(indent + "  ")).reduce("",
                (dec1, dec2) -> (dec1.isEmpty() ? dec1 : (dec1 + "\n")) + dec2);
        String statementsToPrint = statements.stream().map((stm1) -> stm1.toPrint(indent + "  ")).reduce("",
                (stm1, stm2) -> (stm1.isEmpty() ? stm1 : (stm1 + "\n")) + stm2);

        return indent + "{\n" + (declarationsToPrint.isEmpty() ? "" : (declarationsToPrint + "\n"))
                + (statementsToPrint.isEmpty() ? "" : (statementsToPrint + "\n")) + indent + "}";
    }

    @Override
    public TypeNode typeCheck() throws TypeCheckingException {
        for (DeclarationNode dec : declarations) {
            dec.typeCheck(); // The important is to perform type check, not to get the returned value
        }

        for (StatementNode stm : statements) {
            stm.typeCheck(); // The important is to perform type check, not to get the returned value
        }

        if (statements.size() > 0){
            StatementNode lastStatement = statements.get(statements.size()-1);
            if (lastStatement instanceof RetStatNode) {
                return lastStatement.typeCheck();
            }
        }

        return new VoidTypeNode();
    }

    @Override
    public String codeGeneration() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        if (allowScopeCreation) {
            env.pushNewScope();
        }
        ArrayList<SemanticError> errors = new ArrayList<>();

        if (!declarations.isEmpty()) {
            //TODO: env.offset = -2
            for (DeclarationNode declaration : declarations) {
                errors.addAll(declaration.checkSemantics(env));
            }
        }

        for (StatementNode statement : statements) {
            errors.addAll(statement.checkSemantics(env));
        }

        if(allowScopeCreation) {
            env.popScope();
        }

        return errors;
    }

}
