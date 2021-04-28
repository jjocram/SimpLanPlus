package it.azzalinferrati.ast;

import it.azzalinferrati.ast.node.*;
import it.azzalinferrati.ast.node.expression.BaseExpNode;
import it.azzalinferrati.ast.node.type.BoolTypeNode;
import it.azzalinferrati.ast.node.type.IntTypeNode;
import it.azzalinferrati.ast.node.type.PointerTypeNode;
import it.azzalinferrati.ast.node.declaration.DecFunNode;
import it.azzalinferrati.ast.node.declaration.DecVarNode;

import java.util.ArrayList;
import java.util.List;

import it.azzalinferrati.parser.SimpLanPlusParser;
import it.azzalinferrati.parser.SimpLanPlusParser.ArgContext;
import it.azzalinferrati.parser.SimpLanPlusParser.DeclarationContext;
import it.azzalinferrati.parser.SimpLanPlusParser.StatementContext;
import it.azzalinferrati.parser.SimpLanPlusParser.ExpContext;
import org.antlr.v4.runtime.tree.ParseTree;

public class SimpLanPlusVisitorImpl extends SimpLanPlusBaseVisitor<Node> {

    @Override
    public Node visitBlock(SimpLanPlusParser.BlockContext ctx) {
        List<Node> declarations = new ArrayList<>();
        List<Node> statements = new ArrayList<>();
        
        for(DeclarationContext declarationContext: ctx.declaration()) {
            declarations.add(visit(declarationContext));
        }
        
        for(StatementContext statementContext: ctx.statement()) {
            statements.add(visit(statementContext));
        }

        return new BlockNode(declarations, statements);
    }

    @Override
    public Node visitAssigtStat(SimpLanPlusParser.AssigtStatContext ctx) {
        return null;
    }

    @Override
    public Node visitDeletStat(SimpLanPlusParser.DeletStatContext ctx) {
        return null;
    }

    @Override
    public Node visitPrintStat(SimpLanPlusParser.PrintStatContext ctx) {
        return null;
    }

    @Override
    public Node visitRetStat(SimpLanPlusParser.RetStatContext ctx) {
        return null;
    }

    @Override
    public Node visitIteStat(SimpLanPlusParser.IteStatContext ctx) {
        return null;
    }

    @Override
    public Node visitCallStat(SimpLanPlusParser.CallStatContext ctx) {
        return null;
    }

    @Override
    public Node visitBlockStat(SimpLanPlusParser.BlockStatContext ctx) {
        return null;
    }

    @Override
    public Node visitDeclaration(SimpLanPlusParser.DeclarationContext ctx) {
        return null;
    }

    @Override
    public Node visitDecFun(SimpLanPlusParser.DecFunContext ctx) {
        Node type = visit(ctx.type());
        String id = ctx.ID().getText();
        List<Node> args = new ArrayList<>();
        for(ArgContext argContext: ctx.arg()) {
            args.add(visit(argContext));
        }
        Node block = visit(ctx.block());

        return new DecFunNode(type, id, args, block);
    }

    @Override
    public Node visitDecVar(SimpLanPlusParser.DecVarContext ctx) {
        Node type = visit(ctx.type());
        String id = ctx.ID().getText();
        Node exp = visit(ctx.exp());

        return new DecVarNode(type, id, exp);
    }

    @Override
    public Node visitType(SimpLanPlusParser.TypeContext ctx) {
        final String text = ctx.getText();
        if (text.equals("int")) {
            return new IntTypeNode();
        } else if(text.equals("bool")) {
            return new BoolTypeNode();
        } 

        return new PointerTypeNode(visit(ctx.type()));
    }

    @Override
    public Node visitArg(SimpLanPlusParser.ArgContext ctx) {
        Node type = visit(ctx.type());
        String id = ctx.ID().getText();

        return new ArgNode(type, id);
    }

    @Override
    public Node visitAssignment(SimpLanPlusParser.AssignmentContext ctx) {
        Node lhs = visit(ctx.lhs());
        Node exp = visit(ctx.exp());

        return new AssigNode(lhs, exp);
    }

    @Override
    public Node visitLhs(SimpLanPlusParser.LhsContext ctx) {
        String id = ctx.ID().getText();
        Node lhs = visit(ctx.lhs());

        return new LhsNode(id, lhs);
    }

    @Override
    public Node visitDeletion(SimpLanPlusParser.DeletionContext ctx) {
        String id = ctx.ID().getText();

        return new DeletionNode(id);
    }

    @Override
    public Node visitPrint(SimpLanPlusParser.PrintContext ctx) {
        Node exp = visit(ctx.exp());

        return new PrintNode(exp);
    }

    @Override
    public Node visitRet(SimpLanPlusParser.RetContext ctx) {
        Node exp = visit(ctx.exp());

        return new ReturnNode(exp);
    }

    @Override
    public Node visitIte(SimpLanPlusParser.IteContext ctx) {
        return null;
    }

    @Override
    public Node visitCall(SimpLanPlusParser.CallContext ctx) {
        String id = ctx.ID().getText();
        List<Node> args = new ArrayList<>();

        for (ExpContext expContext : ctx.exp()) {
            args.add(visit(expContext));
        }

        return new CallNode(id, args);
    }

    @Override
    public Node visitBaseExp(SimpLanPlusParser.BaseExpContext ctx) {
        Node exp = visit(ctx.exp());

        return new BaseExpNode(exp);
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
    public Node visit(ParseTree tree) {
        return tree!=null ? super.visit(tree) : null;
    }
}
