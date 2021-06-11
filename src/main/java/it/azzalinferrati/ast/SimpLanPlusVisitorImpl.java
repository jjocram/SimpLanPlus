package it.azzalinferrati.ast;

import it.azzalinferrati.ast.node.*;
import it.azzalinferrati.ast.node.expression.*;
import it.azzalinferrati.ast.node.statement.*;
import it.azzalinferrati.ast.node.type.*;
import it.azzalinferrati.ast.node.declaration.DecFunNode;
import it.azzalinferrati.ast.node.declaration.DecVarNode;
import it.azzalinferrati.ast.node.declaration.DeclarateFunNode;
import it.azzalinferrati.ast.node.declaration.DeclarateVarNode;
import it.azzalinferrati.ast.node.declaration.DeclarationNode;

import java.util.ArrayList;
import java.util.List;

import it.azzalinferrati.ast.node.value.BoolNode;
import it.azzalinferrati.ast.node.value.NumberNode;
import it.azzalinferrati.parser.SimpLanPlusParser;
import it.azzalinferrati.parser.SimpLanPlusParser.ArgContext;
import it.azzalinferrati.parser.SimpLanPlusParser.DeclarationContext;
import it.azzalinferrati.parser.SimpLanPlusParser.StatementContext;
import it.azzalinferrati.parser.SimpLanPlusParser.ExpContext;
import org.antlr.v4.runtime.tree.ParseTree;

public class SimpLanPlusVisitorImpl extends SimpLanPlusBaseVisitor<Node> {
    
    @Override
    public BlockNode visitBlock(SimpLanPlusParser.BlockContext ctx) {
        List<DeclarationNode> declarations = new ArrayList<>();
        List<StatementNode> statements = new ArrayList<>();
        
        for(DeclarationContext declarationContext: ctx.declaration()) {
            declarations.add((DeclarationNode) visit(declarationContext));
        }
        
        for(StatementContext statementContext: ctx.statement()) {
            statements.add((StatementNode) visit(statementContext));
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
    public DeclarateFunNode visitDeclarateFun(SimpLanPlusParser.DeclarateFunContext ctx) {
        return new DeclarateFunNode(visitDecFun(ctx.decFun()));
    }

    @Override
    public DeclarateVarNode visitDeclarateVar(SimpLanPlusParser.DeclarateVarContext ctx) {
        return new DeclarateVarNode(visitDecVar(ctx.decVar()));
    }

    @Override
    public DecFunNode visitDecFun(SimpLanPlusParser.DecFunContext ctx) {
        TypeNode type = visitFunType(ctx.funType());
        IdNode id = new IdNode(ctx.ID().getText());
        List<ArgNode> args = new ArrayList<>();
        for(ArgContext argContext: ctx.arg()) {
            args.add(visitArg(argContext));
        }
        BlockNode block = visitBlock(ctx.block());

        return new DecFunNode(type, id, args, block);
    }

    @Override
    public DecVarNode visitDecVar(SimpLanPlusParser.DecVarContext ctx) {
        TypeNode type = visitType(ctx.type());
        IdNode id = new IdNode(ctx.ID().getText());
        ExpNode exp = (ExpNode) visit(ctx.exp());

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
    public TypeNode visitFunType(SimpLanPlusParser.FunTypeContext ctx) {
        if (ctx.type() != null) {
            return visitType(ctx.type());
        } else {
            return new VoidTypeNode();
        }
    }

    @Override
    public ArgNode visitArg(SimpLanPlusParser.ArgContext ctx) {
        TypeNode type = visitType(ctx.type());
        IdNode id = new IdNode(ctx.ID().getText());

        return new ArgNode(type, id);
    }

    @Override
    public AssignmentNode visitAssignment(SimpLanPlusParser.AssignmentContext ctx) {
        LhsNode lhs = visitLhs(ctx.lhs());
        ExpNode exp = (ExpNode) visit(ctx.exp());

        return new AssignmentNode(lhs, exp);
    }

    @Override
    public LhsNode visitLhs(SimpLanPlusParser.LhsContext ctx) {
        // TODO: Recursive function, check with Gilberto Filè teaching
        if (ctx.lhs() == null) {
            // lhs: ID
            IdNode id = new IdNode(ctx.ID().getText());
            return new LhsNode(id, null);
        } else{
            // lhs: lhs '^'
            LhsNode lhs = visitLhs(ctx.lhs());
            return new LhsNode(lhs.getId(), lhs);
        }
    }

    @Override
    public DeletionNode visitDeletion(SimpLanPlusParser.DeletionContext ctx) {
        IdNode id = new IdNode(ctx.ID().getText());

        return new DeletionNode(id);
    }

    @Override
    public PrintNode visitPrint(SimpLanPlusParser.PrintContext ctx) {
        ExpNode exp = (ExpNode) visit(ctx.exp());

        return new PrintNode(exp);
    }

    @Override
    public RetNode visitRet(SimpLanPlusParser.RetContext ctx) {
        ExpNode exp = (ExpNode) visit(ctx.exp());

        return new RetNode(exp);
    }

    @Override
    public IteNode visitIte(SimpLanPlusParser.IteContext ctx) {
        ExpNode condition = (ExpNode) visit(ctx.condition);
        StatementNode thenStatement = (StatementNode) visit(ctx.thenBranch);
        StatementNode elseStatement = (StatementNode) visit(ctx.elseBranch);

        return new IteNode(condition, thenStatement, elseStatement);
    }

    @Override
    public CallNode visitCall(SimpLanPlusParser.CallContext ctx) {
        IdNode id = new IdNode(ctx.ID().getText());
        List<ExpNode> parameters = new ArrayList<>();

        for (ExpContext expContext : ctx.exp()) {
            parameters.add((ExpNode) visit(expContext));
        }

        return new CallNode(id, parameters);
    }

    @Override
    public BaseExpNode visitBaseExp(SimpLanPlusParser.BaseExpContext ctx) {
        ExpNode exp = (ExpNode) visit(ctx.exp());

        return new BaseExpNode(exp);
    }

    @Override
    public NegativeExpNode visitNegExp(SimpLanPlusParser.NegExpContext ctx) {
        ExpNode exp = (ExpNode) visit(ctx.exp());

        return new NegativeExpNode(exp);
    }

    @Override
    public Node visitNotExp(SimpLanPlusParser.NotExpContext ctx) {
        ExpNode exp = (ExpNode) visit(ctx.exp());

        return new NotExpNode(exp);
    }

    @Override
    public BinaryExpNode visitBinExp(SimpLanPlusParser.BinExpContext ctx) {
        ExpNode leftExpression = (ExpNode) visit(ctx.left);
        String operator = ctx.op.getText();
        ExpNode rightExpression = (ExpNode) visit(ctx.right);

        return new BinaryExpNode(leftExpression, operator, rightExpression);
    }

    @Override
    public DereferenceExpNode visitDerExp(SimpLanPlusParser.DerExpContext ctx) {
        LhsNode lhs = visitLhs(ctx.lhs());

        return new DereferenceExpNode(lhs);
    }

    @Override
    public NewExpNode visitNewExp(SimpLanPlusParser.NewExpContext ctx) {
        TypeNode type = visitType(ctx.type());
        return new NewExpNode(type);
    }

    @Override
    public CallExpNode visitCallExp(SimpLanPlusParser.CallExpContext ctx) {
        CallNode callNode = visitCall(ctx.call());

        return new CallExpNode(callNode);
    }

    @Override
    public BoolNode visitBoolExp(SimpLanPlusParser.BoolExpContext ctx) {
        return new BoolNode(Boolean.parseBoolean(ctx.getText()));
    }

    @Override
    public NumberNode visitValExp(SimpLanPlusParser.ValExpContext ctx) {
        return new NumberNode(Integer.parseInt(ctx.getText()));
    }

    @Override
    public Node visit(ParseTree tree) {
        return tree!=null ? super.visit(tree) : null;
    }
}
