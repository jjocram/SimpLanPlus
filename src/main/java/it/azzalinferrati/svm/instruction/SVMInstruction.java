package it.azzalinferrati.svm.instruction;

import it.azzalinferrati.svm.SVMInterpreter;

public class SVMInstruction {
    private final String instruction;
    private final String arg1;
    private final int offset;
    private final String arg2;
    private final String arg3;
    private final FunctionToExecute<SVMInterpreter, String, Integer, String, String> functionToExecute;

    public SVMInstruction(String instruction, String arg1, int offset, String arg2, String arg3, FunctionToExecute<SVMInterpreter, String, Integer, String, String> functionToExecute) {
        this.instruction = instruction;
        this.arg1 = arg1;
        this.offset = offset;
        this.arg2 = arg2;
        this.arg3 = arg3;
        this.functionToExecute = functionToExecute;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getArg1() {
        return arg1;
    }

    public int getOffset() {
        return offset;
    }

    public String getArg2() {
        return arg2;
    }

    public String getArg3() {
        return arg3;
    }

    public FunctionToExecute<SVMInterpreter, String, Integer, String, String> getFunctionToExecute() {
        return functionToExecute;
    }

    public void execute(SVMInterpreter cpu) {
        functionToExecute.apply(cpu, arg1, offset, arg2, arg3);
    }

    @Override
    public String toString() {
        return "SVMInstruction{" +
                "instruction='" + instruction + '\'' +
                ", arg1='" + arg1 + '\'' +
                ", offset=" + offset +
                ", arg2='" + arg2 + '\'' +
                ", arg3='" + arg3 + '\'' +
                '}';
    }
}
