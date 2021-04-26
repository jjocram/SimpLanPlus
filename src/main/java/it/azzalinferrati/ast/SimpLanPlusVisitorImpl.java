package it.azzalinferrati.ast;

import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.parser.SimpLanPlusParser;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class SimpLanPlusVisitorImpl implements SimpLanPlusVisitor<Node> {

    @Override
    public Node visitBlock(SimpLanPlusParser.BlockContext ctx) {
        return null;
    }

    @Override
    public Node visitStatement(SimpLanPlusParser.StatementContext ctx) {
        return null;
    }

    @Override
    public Node visitDeclaration(SimpLanPlusParser.DeclarationContext ctx) {
        return null;
    }

    @Override
    public Node visitDecFun(SimpLanPlusParser.DecFunContext ctx) {
        return null;
    }

    @Override
    public Node visitDecVar(SimpLanPlusParser.DecVarContext ctx) {
        return null;
    }

    @Override
    public Node visitType(SimpLanPlusParser.TypeContext ctx) {
        return null;
    }

    @Override
    public Node visitArg(SimpLanPlusParser.ArgContext ctx) {
        return null;
    }

    @Override
    public Node visitAssignment(SimpLanPlusParser.AssignmentContext ctx) {
        return null;
    }

    @Override
    public Node visitLhs(SimpLanPlusParser.LhsContext ctx) {
        return null;
    }

    @Override
    public Node visitDeletion(SimpLanPlusParser.DeletionContext ctx) {
        return null;
    }

    @Override
    public Node visitPrint(SimpLanPlusParser.PrintContext ctx) {
        return null;
    }

    @Override
    public Node visitRet(SimpLanPlusParser.RetContext ctx) {
        return null;
    }

    @Override
    public Node visitIte(SimpLanPlusParser.IteContext ctx) {
        return null;
    }

    @Override
    public Node visitCall(SimpLanPlusParser.CallContext ctx) {
        return null;
    }

    @Override
    public Node visitBaseExp(SimpLanPlusParser.BaseExpContext ctx) {
        return null;
    }

    @Override
    public Node visitBinExp(SimpLanPlusParser.BinExpContext ctx) {
        return null;
    }

    @Override
    public Node visitDerExp(SimpLanPlusParser.DerExpContext ctx) {
        return null;
    }

    @Override
    public Node visitNewExp(SimpLanPlusParser.NewExpContext ctx) {
        return null;
    }

    @Override
    public Node visitValExp(SimpLanPlusParser.ValExpContext ctx) {
        return null;
    }

    @Override
    public Node visitNegExp(SimpLanPlusParser.NegExpContext ctx) {
        return null;
    }

    @Override
    public Node visitBoolExp(SimpLanPlusParser.BoolExpContext ctx) {
        return null;
    }

    @Override
    public Node visitCallExp(SimpLanPlusParser.CallExpContext ctx) {
        return null;
    }

    @Override
    public Node visitNotExp(SimpLanPlusParser.NotExpContext ctx) {
        return null;
    }

    @Override
    public Node visit(ParseTree parseTree) {
        return null;
    }

    @Override
    public Node visitChildren(RuleNode ruleNode) {
        return null;
    }

    @Override
    public Node visitTerminal(TerminalNode terminalNode) {
        return null;
    }

    @Override
    public Node visitErrorNode(ErrorNode errorNode) {
        return null;
    }
}
