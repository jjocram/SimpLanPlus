package it.azzalinferrati.svm.instruction;

public class SVMInstruction {
    private final String instruction;
    private final String arg1;
    private final int offset;
    private final String arg2;
    private final String arg3;

    public SVMInstruction(String instruction, String arg1, int offset, String arg2, String arg3) {
        this.instruction = instruction;
        this.arg1 = arg1;
        this.offset = offset;
        this.arg2 = arg2;
        this.arg3 = arg3;
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
