package it.azzalinferrati.svm.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.azzalinferrati.svm.parser.SVMParser;

public class SVMVisitorImpl extends SVMBaseVisitor<Void> {
    private List<Integer> code;
    private Map<String, Integer> labelPositions;
    private Map<Integer, String> labelReferences;

    SVMVisitorImpl() {
        code = new ArrayList<>();
        labelPositions = new HashMap<>();
        labelReferences = new HashMap<>();
    }

    @Override
    public Void visitAssembly(SVMParser.AssemblyContext ctx) {
        visitChildren(ctx);

        for (var reference : labelReferences.entrySet()) {
            Integer ref = reference.getKey();
            code.set(ref, labelPositions.get(reference.getValue()));
        }

        return null;
    }

    @Override
    public Void visitPush(SVMParser.PushContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitPop(SVMParser.PopContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitLoadWord(SVMParser.LoadWordContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitStoreWord(SVMParser.StoreWordContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitLoadInteger(SVMParser.LoadIntegerContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitAdd(SVMParser.AddContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitSub(SVMParser.SubContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitMult(SVMParser.MultContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitDiv(SVMParser.DivContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitAddInt(SVMParser.AddIntContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitSubInt(SVMParser.SubIntContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitMultInt(SVMParser.MultIntContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitDivInt(SVMParser.DivIntContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitAnd(SVMParser.AndContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitOr(SVMParser.OrContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitNot(SVMParser.NotContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitAndBool(SVMParser.AndBoolContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitOrBool(SVMParser.OrBoolContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitNotBool(SVMParser.NotBoolContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitMove(SVMParser.MoveContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitBranchIfEqual(SVMParser.BranchIfEqualContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitBranchIfLessEqual(SVMParser.BranchIfLessEqualContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitBranch(SVMParser.BranchContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitLabel(SVMParser.LabelContext ctx) {
        labelPositions.put(ctx.LABEL().getText(), code.size() + 1);
        return null;
    }

    @Override
    public Void visitJumpToFunction(SVMParser.JumpToFunctionContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitJumpToRegister(SVMParser.JumpToRegisterContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitDelete(SVMParser.DeleteContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitPrint(SVMParser.PrintContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Void visitHalt(SVMParser.HaltContext ctx) {
        return visitChildren(ctx);
    }
}
