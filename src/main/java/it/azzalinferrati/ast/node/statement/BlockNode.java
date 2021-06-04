package it.azzalinferrati.ast.node.statement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.declaration.DeclarateFunNode;
import it.azzalinferrati.ast.node.declaration.DeclarateVarNode;
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
    private boolean isMainBlock;

    public BlockNode(final List<DeclarationNode> declarations, final List<StatementNode> statements) {
        this.declarations = declarations;
        this.statements = statements;
        allowScopeCreation = true;
        isMainBlock = false;
    }

    public void allowScopeCreation() {
        allowScopeCreation = true;
    }

    public void disallowScopeCreation() {
        allowScopeCreation = false;
    }

    public boolean isMainBlock() {
        return isMainBlock;
    }

    public void setMainBlock(boolean mainBlock) {
        isMainBlock = mainBlock;
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

        if (statements.size() == 0) {
            //No statements
            return new VoidTypeNode();
        }

        if (statements.stream().noneMatch(stm -> stm instanceof RetStatNode)) {
            List<StatementNode> iteStatNodes = statements.stream().filter(stm -> stm instanceof IteStatNode).collect(Collectors.toList());
            for (int i = 0; i < iteStatNodes.size() - 1; i++) {
                // Multiple if-then-else must have the same returned type
                if (!Node.isSubtype(iteStatNodes.get(i).typeCheck(), iteStatNodes.get(i + 1).typeCheck())) {
                    throw new TypeCheckingException("Multiple return statements with different returned types");
                }
            }
            if (iteStatNodes.size() > 0) {
                // There are if-then-else
                return iteStatNodes.get(0).typeCheck();
            }

            //No return
            return new VoidTypeNode();
        }

        return statements.get(statements.size() - 1).typeCheck();
    }

    @Override
    public String codeGeneration() {
        StringBuffer buffer = new StringBuffer();

        /*
        declarations.stream()
                .filter(dec -> dec instanceof DeclarateVarNode)
                .collect(Collectors.toCollection(LinkedList::new))
                .descendingIterator()
                .forEachRemaining(dec -> buffer.append(dec.codeGeneration()));
*/
        declarations.stream()
                .filter(dec -> dec instanceof DeclarateVarNode)
                .forEach(varDec -> buffer.append(varDec.codeGeneration()));

        statements.forEach(stm -> buffer.append(stm.codeGeneration()));

        if (isMainBlock) {
            buffer.append("halt\n");
        }

        declarations.stream()
                .filter(dec -> dec instanceof DeclarateFunNode)
                .forEach(funDec -> buffer.append(funDec.codeGeneration()));

        return buffer.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        if (allowScopeCreation) {
            env.pushNewScope();
        }
        ArrayList<SemanticError> errors = new ArrayList<>();

        if (!declarations.isEmpty()) {
            for (DeclarationNode declaration : declarations) {
                errors.addAll(declaration.checkSemantics(env));
            }
        }

        for (StatementNode statement : statements) {
            errors.addAll(statement.checkSemantics(env));
        }

        if (statements.stream().anyMatch(stm -> stm instanceof RetStatNode)) {
            var firstReturnStm = statements.stream().filter(stm -> stm instanceof RetStatNode).findFirst().get();
            int returnIndex = statements.indexOf(firstReturnStm);
            if (returnIndex + 1 < statements.size()) {
                errors.add(new SemanticError("There is code after return statement"));
            }
        }

        if (allowScopeCreation) {
            env.popScope();
        }

        return errors;
    }

}
