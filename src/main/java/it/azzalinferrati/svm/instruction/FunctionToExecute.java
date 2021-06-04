package it.azzalinferrati.svm.instruction;

@FunctionalInterface
public interface FunctionToExecute<Cpu, Arg1, Offset, Arg2, Arg3> {
    public void apply(Cpu cpu, Arg1 arg1, Offset offset, Arg2 arg2, Arg3 arg3);
}
