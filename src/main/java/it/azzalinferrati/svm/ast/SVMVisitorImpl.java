package it.azzalinferrati.svm.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.azzalinferrati.svm.instruction.SVMInstruction;
import it.azzalinferrati.svm.instruction.SVMInstructionBuilder;
import it.azzalinferrati.svm.parser.SVMParser;


public class SVMVisitorImpl extends SVMBaseVisitor<Void> {
    private List<SVMInstruction> code;
    private Map<String, Integer> labelPositions;
    private Map<Integer, String> labelReferences;

    public SVMVisitorImpl() {
        code = new ArrayList<>();
        labelPositions = new HashMap<>();
        labelReferences = new HashMap<>();
    }

    public List<SVMInstruction> getCode() {
        return code;
    }

    @Override
    public Void visitAssembly(SVMParser.AssemblyContext ctx) {
        visitChildren(ctx);

        for (var labelToJump : labelReferences.entrySet()) {
            Integer codeLine = labelToJump.getKey();
            String label = labelToJump.getValue();
            Integer lineToJump = labelPositions.get(label);

            SVMInstruction instructionToModify = code.get(codeLine);
            if (instructionToModify.getInstruction().equals("beq") || instructionToModify.getInstruction().equals("bleq")) {
                code.set(codeLine, new SVMInstructionBuilder()
                        .instruction(instructionToModify.getInstruction())
                        .arg1(instructionToModify.getArg1())
                        .arg2(instructionToModify.getArg2())
                        .arg3(lineToJump.toString())
                        .build());
            } else {
                // b and jal instructions
                code.set(codeLine, new SVMInstructionBuilder()
                        .instruction(instructionToModify.getInstruction())
                        .arg1(lineToJump.toString())
                        .build());
            }
        }

        return null;
    }

    @Override
    public Void visitPush(SVMParser.PushContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("push")
                .arg1(ctx.REGISTER().getText())
                .build());
        return null;
    }

    @Override
    public Void visitPop(SVMParser.PopContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("pop")
                .build());
        return null;
    }

    @Override
    public Void visitLoadWord(SVMParser.LoadWordContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("lw")
                .arg1(ctx.output.getText())
                .offset(Integer.parseInt(ctx.NUMBER().getText()))
                .arg2(ctx.input.getText())
                .build());
        return null;
    }

    @Override
    public Void visitStoreWord(SVMParser.StoreWordContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("sw")
                .arg1(ctx.output.getText())
                .offset(Integer.parseInt(ctx.NUMBER().getText()))
                .arg2(ctx.input.getText())
                .build());
        return null;
    }

    @Override
    public Void visitLoadInteger(SVMParser.LoadIntegerContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("li")
                .arg1(ctx.REGISTER().getText())
                .arg2(ctx.NUMBER().getText()) // Interpreter will need to cast to integer
                .build());
        return null;
    }

    @Override
    public Void visitAdd(SVMParser.AddContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("add")
                .arg1(ctx.output.getText())
                .arg2(ctx.input1.getText())
                .arg3(ctx.input2.getText())
                .build());
        return null;
    }

    @Override
    public Void visitSub(SVMParser.SubContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("sub")
                .arg1(ctx.output.getText())
                .arg2(ctx.input1.getText())
                .arg3(ctx.input2.getText())
                .build());
        return null;
    }

    @Override
    public Void visitMult(SVMParser.MultContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("mult")
                .arg1(ctx.output.getText())
                .arg2(ctx.input1.getText())
                .arg3(ctx.input2.getText())
                .build());
        return null;
    }

    @Override
    public Void visitDiv(SVMParser.DivContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("div")
                .arg1(ctx.output.getText())
                .arg2(ctx.input1.getText())
                .arg3(ctx.input2.getText())
                .build());
        return null;
    }

    @Override
    public Void visitAddInt(SVMParser.AddIntContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("addi")
                .arg1(ctx.output.getText())
                .arg2(ctx.input.getText())
                .arg3(ctx.NUMBER().getText()) // Interpreter will need to cast to integer
                .build());
        return null;
    }

    @Override
    public Void visitSubInt(SVMParser.SubIntContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("subi")
                .arg1(ctx.output.getText())
                .arg2(ctx.input.getText())
                .arg3(ctx.NUMBER().getText()) // Interpreter will need to cast to integer
                .build());
        return null;
    }

    @Override
    public Void visitMultInt(SVMParser.MultIntContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("multi")
                .arg1(ctx.output.getText())
                .arg2(ctx.input.getText())
                .arg3(ctx.NUMBER().getText()) // Interpreter will need to cast to integer
                .build());
        return null;
    }

    @Override
    public Void visitDivInt(SVMParser.DivIntContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("divi")
                .arg1(ctx.output.getText())
                .arg2(ctx.input.getText())
                .arg3(ctx.NUMBER().getText()) // Interpreter will need to cast to integer
                .build());
        return null;
    }

    @Override
    public Void visitAnd(SVMParser.AndContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("and")
                .arg1(ctx.output.getText())
                .arg2(ctx.input1.getText())
                .arg3(ctx.input2.getText())
                .build());
        return null;
    }

    @Override
    public Void visitOr(SVMParser.OrContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("or")
                .arg1(ctx.output.getText())
                .arg2(ctx.input1.getText())
                .arg3(ctx.input2.getText())
                .build());
        return null;
    }

    @Override
    public Void visitNot(SVMParser.NotContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("not")
                .arg1(ctx.output.getText())
                .arg2(ctx.input.getText())
                .build());
        return null;
    }

    @Override
    public Void visitAndBool(SVMParser.AndBoolContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("andb")
                .arg1(ctx.output.getText())
                .arg2(ctx.input.getText())
                .arg3(ctx.BOOL().getText())
                .build());
        return null;
    }

    @Override
    public Void visitOrBool(SVMParser.OrBoolContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("orb")
                .arg1(ctx.output.getText())
                .arg2(ctx.input.getText())
                .arg3(ctx.BOOL().getText())
                .build());
        return null;
    }

    @Override
    public Void visitNotBool(SVMParser.NotBoolContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("notb")
                .arg1(ctx.output.getText())
                .arg3(ctx.BOOL().getText())
                .build());
        return null;
    }

    @Override
    public Void visitMove(SVMParser.MoveContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("mv")
                .arg1(ctx.output.getText())
                .arg2(ctx.input.getText())
                .build());
        return null;
    }

    @Override
    public Void visitBranchIfEqual(SVMParser.BranchIfEqualContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("beq")
                .arg1(ctx.input1.getText())
                .arg2(ctx.input2.getText())
                .arg3(ctx.LABEL().getText())
                .build());
        labelReferences.put(code.size()-1, ctx.LABEL().getText());
        return null;
    }

    @Override
    public Void visitBranchIfLessEqual(SVMParser.BranchIfLessEqualContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("bleq")
                .arg1(ctx.input1.getText())
                .arg2(ctx.input2.getText())
                .arg3(ctx.LABEL().getText())
                .build());
        labelReferences.put(code.size()-1, ctx.LABEL().getText());
        return null;
    }

    @Override
    public Void visitBranch(SVMParser.BranchContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("b")
                .arg1(ctx.LABEL().getText())
                .build());
        labelReferences.put(code.size()-1, ctx.LABEL().getText());
        return null;
    }

    @Override
    public Void visitLabel(SVMParser.LabelContext ctx) {
        // TODO: check if code.size() is correct or missing +1
        labelPositions.put(ctx.LABEL().getText(), code.size());
        return null;
    }

    @Override
    public Void visitJumpToFunction(SVMParser.JumpToFunctionContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("jal")
                .arg1(ctx.LABEL().getText())
                .build());
        labelReferences.put(code.size(), ctx.LABEL().getText());
        return null;
    }

    @Override
    public Void visitJumpToRegister(SVMParser.JumpToRegisterContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("jr")
                .arg1(ctx.REGISTER().getText())
                .build());
        return null;
    }

    @Override
    public Void visitDelete(SVMParser.DeleteContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("del")
                .arg1(ctx.REGISTER().getText())
                .build());
        return null;
    }

    @Override
    public Void visitPrint(SVMParser.PrintContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("print")
                .arg1(ctx.REGISTER().getText())
                .build());
        return null;
    }

    @Override
    public Void visitHalt(SVMParser.HaltContext ctx) {
        code.add(new SVMInstructionBuilder()
                .instruction("halt")
                .build());
        return null;
    }
}
