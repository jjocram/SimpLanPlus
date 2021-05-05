package it.azzalinferrati.ast.node.statement;

import java.util.ArrayList;
import java.util.List;

import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.ast.node.declaration.DeclarationNode;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;

public class BlockNode implements Node {

    final private List<DeclarationNode> declarations;

    final private List<StatementNode> statements;

    public BlockNode(final List<DeclarationNode> declarations, final List<StatementNode> statements) {
        this.declarations = declarations;
        this.statements = statements;
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
    public Node typeCheck() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String codeGeneration() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        // TODO Auto-generated method stub
        return null;
    }

}
