package it.azzalinferrati.svm.instruction;

import it.azzalinferrati.svm.SVMInterpreter;

public class SVMInstructionBuilder {
    private String instruction = null;
    private String arg1 = null;
    private int offset = 0;
    private String arg2 = null;
    private String arg3 = null;
    private FunctionToExecute<SVMInterpreter, String, Integer, String, String> functionToExecute = null;

    public SVMInstructionBuilder instruction(String instruction) {
        this.instruction = instruction;
        return this;
    }

    public SVMInstructionBuilder arg1(String arg1) {
        this.arg1 = arg1;
        return this;
    }

    public SVMInstructionBuilder offset(int offset) {
        this.offset = offset;
        return this;
    }

    public SVMInstructionBuilder arg2(String arg2) {
        this.arg2 = arg2;
        return this;
    }

    public SVMInstructionBuilder arg3(String arg3) {
        this.arg3 = arg3;
        return this;
    }

    public SVMInstructionBuilder functionToExecute(FunctionToExecute<SVMInterpreter, String, Integer, String, String> functionToExecute) {
        this.functionToExecute = functionToExecute;
        return this;
    }

    public SVMInstruction build() {
        return new SVMInstruction(
                instruction,
                arg1,
                offset,
                arg2,
                arg3,
                functionToExecute
        );
    }

}
