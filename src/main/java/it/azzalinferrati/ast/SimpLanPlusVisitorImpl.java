package it.azzalinferrati.ast;

import it.azzalinferrati.ast.node.*;
import it.azzalinferrati.ast.node.expression.BaseExpNode;
import it.azzalinferrati.ast.node.expression.ExpNode;
import it.azzalinferrati.ast.node.statement.AssigtStatNode;
import it.azzalinferrati.ast.node.statement.BlockStatNode;
import it.azzalinferrati.ast.node.statement.CallStatNode;
import it.azzalinferrati.ast.node.statement.DeletStatNode;
import it.azzalinferrati.ast.node.statement.IteStatNode;
import it.azzalinferrati.ast.node.statement.PrintStatNode;
import it.azzalinferrati.ast.node.statement.RetStatNode;
import it.azzalinferrati.ast.node.type.BoolTypeNode;
import it.azzalinferrati.ast.node.type.IntTypeNode;
import it.azzalinferrati.ast.node.type.PointerTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
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
    public BlockNode visitBlock(SimpLanPlusParser.BlockContext ctx) {
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
    public AssigtStatNode visitAssigtStat(SimpLanPlusParser.AssigtStatContext ctx) {
        return new AssigtStatNode(visitAssignment(ctx.assignment()));
    }

    @Override
    public DeletStatNode visitDeletStat(SimpLanPlusParser.DeletStatContext ctx) {
        return new DeletStatNode(visitDeletion(ctx.deletion()));
    }

    @Override
    public PrintStatNode visitPrintStat(SimpLanPlusParser.PrintStatContext ctx) {
        return new PrintStatNode(visitPrint(ctx.print()));
    }

    @Override
    public RetStatNode visitRetStat(SimpLanPlusParser.RetStatContext ctx) {
        return new RetStatNode(visitRet(ctx.ret()));
    }

    @Override
    public IteStatNode visitIteStat(SimpLanPlusParser.IteStatContext ctx) {
        return new IteStatNode(visitIte(ctx.ite()));
    }

    @Override
    public CallStatNode visitCallStat(SimpLanPlusParser.CallStatContext ctx) {
        return new CallStatNode(visitCall(ctx.call()));
    }

    @Override
    public BlockStatNode visitBlockStat(SimpLanPlusParser.BlockStatContext ctx) {
        return new BlockStatNode(visitBlock(ctx.block()));
    }

    @Override
    public Node visitDeclaration(SimpLanPlusParser.DeclarationContext ctx) {
        return null;
    }

    @Override
    public DecFunNode visitDecFun(SimpLanPlusParser.DecFunContext ctx) {
        Node type = visit(ctx.type());
        String id = ctx.ID().getText();
        List<ArgNode> args = new ArrayList<>();
        for(ArgContext argContext: ctx.arg()) {
            args.add(visitArg(argContext));
        }
        BlockNode block = visitBlock(ctx.block());

        return new DecFunNode(type, id, args, block);
    }

    @Override
    public DecVarNode visitDecVar(SimpLanPlusParser.DecVarContext ctx) {
        Node type = visit(ctx.type());
        String id = ctx.ID().getText();
        Node exp = visit(ctx.exp());

        return new DecVarNode(type, id, exp);
    }

    @Override
    public TypeNode visitType(SimpLanPlusParser.TypeContext ctx) {
        final String text = ctx.getText();
        if (text.equals("int")) {
            return new IntTypeNode();
        } else if(text.equals("bool")) {
            return new BoolTypeNode();
        } 

        return new PointerTypeNode(visitType(ctx.type()));
    }

    @Override
    public ArgNode visitArg(SimpLanPlusParser.ArgContext ctx) {
        TypeNode type = visitType(ctx.type());
        String id = ctx.ID().getText();

        return new ArgNode(type, id);
    }

    @Override
    public AssignmentNode visitAssignment(SimpLanPlusParser.AssignmentContext ctx) {
        LhsNode lhs = visitLhs(ctx.lhs());
        Node exp = visit(ctx.exp());

        return new AssignmentNode(lhs, exp);
    }

    @Override
    public LhsNode visitLhs(SimpLanPlusParser.LhsContext ctx) {
        String id = ctx.ID().getText();
        LhsNode lhs = visitLhs(ctx.lhs());

        return new LhsNode(id, lhs);
    }

    @Override
    public DeletionNode visitDeletion(SimpLanPlusParser.DeletionContext ctx) {
        String id = ctx.ID().getText();

        return new DeletionNode(id);
    }

    @Override
    public PrintNode visitPrint(SimpLanPlusParser.PrintContext ctx) {
        Node exp = visit(ctx.exp());

        return new PrintNode(exp);
    }

    @Override
    public ReturnNode visitRet(SimpLanPlusParser.RetContext ctx) {
        Node exp = visit(ctx.exp());

        return new ReturnNode(exp);
    }

    @Override
    public Node visitIte(SimpLanPlusParser.IteContext ctx) {
        return null;
    }

    @Override
    public CallNode visitCall(SimpLanPlusParser.CallContext ctx) {
        String id = ctx.ID().getText();
        List<ExpNode> parameters = new ArrayList<>();

        for (ExpContext expContext : ctx.exp()) {
            parameters.add(visit(expContext));
        }

        return new CallNode(id, parameters);
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
