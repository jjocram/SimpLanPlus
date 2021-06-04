package it.azzalinferrati.svm;

import it.azzalinferrati.svm.exception.CodeSizeTooSmallException;
import it.azzalinferrati.svm.instruction.SVMInstruction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MemoryCell {
    private int data;
    private boolean isUsed;

    public MemoryCell(int data, boolean isUsed) {
        this.data = data;
        this.isUsed = isUsed;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
        this.isUsed = true;
    }

    public void freeCell() {
        isUsed = false;
    }

    public boolean isFree() {
        return !isUsed;
    }

    @Override
    public String toString() {
        return "|" + data + "|";
    }
}

public class SVMInterpreter {
    private final int memorySize; //Max size of the memory (heap + stack)

    private List<SVMInstruction> code;
    private MemoryCell[] memory;

    private Map<String, Integer> registers;
    private int $ip; //Program Counter (not available in code generation)

    public SVMInterpreter(int codeSize, int memorySize, List<SVMInstruction> code) throws CodeSizeTooSmallException {
        this.memorySize = memorySize;
        this.code = code;

        if(codeSize < code.size()) {
            throw new CodeSizeTooSmallException("Requested code area size: " + codeSize + ", number of instructions given: " + code.size() + ".");
        }

        memory = new MemoryCell[memorySize];
        for (int i = 0; i < memorySize; i++) {
            memory[i] = new MemoryCell(-1, false);
        }

        $ip = 0;

        registers = new HashMap<>();
        registers.put("$sp", memorySize);
        //registers.put("$hp", -1);
        registers.put("$fp", memorySize - 1);
        registers.put("$ra", null);
        registers.put("$al", null);
        registers.put("$a0", null);
        registers.put("$t1", null);
    }

    private int sp() {
        return registers.get("$sp");
    }

    private int hp() {
        //return registers.get("$hp");
        var firstFreeMemoryCell = Arrays.stream(memory).filter(MemoryCell::isFree).findFirst();
        if (firstFreeMemoryCell.isPresent()) {
            for (int i = 0; i < memorySize; i++) {
                if (memory[i] == firstFreeMemoryCell.get()) {
                    return i;
                }
            }
        }

        return memorySize; // reach the end of memory nothing is free
    }

    private int fp() {
        return registers.get("$fp");
    }

    private int ra() {
        return registers.get("$ra");
    }

    private int al() {
        return registers.get("$al");
    }

    private int a0() {
        return registers.get("$a0");
    }

    private int t1() {
        return registers.get("$t1");
    }

    private void updateRegister(String register, int value) {
        registers.put(register, value);
    }

    private int readRegister(String register) {
        return registers.get(register);
    }

    private void writeOnMemory(int address, int data) {
        memory[address].setData(data);
    }

    private int readFromMemory(int address) {
        return memory[address].getData();
    }

    private void freeMemory(int address) {
        memory[address].freeCell();
    }

    public void run(boolean activeDebug) {

        if(activeDebug) {
            debugCPU();
            System.out.println("---------------------------");
        }

        while (true) {
            if (hp() >= sp()) {
                System.err.println("ERROR: Out of memory.");
                return;
            }

            SVMInstruction instruction = code.get($ip);
            $ip += 1;
            String arg1 = instruction.getArg1();
            String arg2 = instruction.getArg2();
            String arg3 = instruction.getArg3();
            int offset = instruction.getOffset();

            switch (instruction.getInstruction()) {
                case "push":
                    updateRegister("$sp", sp() - 1); // sp -= 1
                    writeOnMemory(sp(), readRegister(arg1));
                    break;
                case "pop":
                    updateRegister("$sp", sp() + 1); // sp += 1
                    break;
                case "lw":
                    // "memory address -> memory ind
                    updateRegister(arg1, readFromMemory(readRegister(arg2) + offset));
                    break;
                case "sw":
                    // "memory address -> memory index"
                    if (arg2.equals("$hp")) {
                        int heapAddress = hp();
                        writeOnMemory(heapAddress, readRegister(arg1));
                        updateRegister("$a0", heapAddress);
                    } else {
                        writeOnMemory(readRegister(arg2), readRegister(arg1));
                    }
                    break;
                case "li":
                    updateRegister(arg1, Integer.parseInt(arg2));
                    break;
                case "add":
                    updateRegister(arg1, readRegister(arg2) + readRegister(arg3));
                    break;
                case "sub":
                    updateRegister(arg1, readRegister(arg2) - readRegister(arg3));
                    break;
                case "mult":
                    updateRegister(arg1, readRegister(arg2) * readRegister(arg3));
                    break;
                case "div":
                    updateRegister(arg1, readRegister(arg2) / readRegister(arg3));
                    break;
                case "addi":
                    updateRegister(arg1, readRegister(arg2) + Integer.parseInt(arg3));
                    break;
                case "subi":
                    updateRegister(arg1, readRegister(arg2) - Integer.parseInt(arg3));
                    break;
                case "multi":
                    updateRegister(arg1, readRegister(arg2) * Integer.parseInt(arg3));
                    break;
                case "divi":
                    updateRegister(arg1, readRegister(arg2) / Integer.parseInt(arg3));
                    break;
                case "and": {
                    boolean input1 = readRegister(arg2) == 1;
                    boolean input2 = readRegister(arg3) == 1;
                    int result = input1 && input2 ? 1 : 0;
                    updateRegister(arg1, result);
                    break;
                }
                case "or": {
                    boolean input1 = readRegister(arg2) == 1;
                    boolean input2 = readRegister(arg3) == 1;
                    int result = input1 || input2 ? 1 : 0;
                    updateRegister(arg1, result);
                    break;
                }
                case "not": {
                    int result = readRegister(arg2) == 1 ? 0 : 1;
                    updateRegister(arg1, result);
                    break;
                }
                case "andb": {
                    boolean input1 = readRegister(arg2) == 1;
                    boolean input2 = arg3.equals("1");
                    int result = input1 && input2 ? 1 : 0;
                    updateRegister(arg1, result);
                    break;
                }
                case "orb": {
                    boolean input1 = readRegister(arg2) == 1;
                    boolean input2 = arg3.equals("1");
                    int result = input1 || input2 ? 1 : 0;
                    updateRegister(arg1, result);
                    break;
                }
                case "notb": {
                    int result = arg2.equals("1") ? 0 : 1;
                    updateRegister(arg1, result);
                    break;
                }
                case "mv":
                    updateRegister(arg1, readRegister(arg2));
                    break;
                case "beq":
                    if (readRegister(arg1) == readRegister(arg2)) {
                        $ip = Integer.parseInt(arg3);
                    }
                    break;
                case "bleq":
                    if (readRegister(arg1) <= readRegister(arg2)) {
                        $ip = Integer.parseInt(arg3);
                    }
                    break;
                case "b":
                    $ip = Integer.parseInt(arg1);
                    break;
                case "jal":
                    updateRegister("$ra", $ip);
                    $ip = Integer.parseInt(arg1);
                    break;
                case "jr":
                    $ip = readRegister(arg1);
                    break;
                case "del":
                    freeMemory(readRegister(arg1));
                    break;
                case "print":
                    System.out.println(readRegister(arg1));
                    break;
                case "halt":
                    return;
                default:
                    System.err.println("ERROR: Unrecognized Assembly instruction.");
                    return;
            }

            if(activeDebug) {
                System.out.println(instruction);
                debugCPU();
                System.out.println("---------------------------");
            }

        }
    }

    private void debugCPU() {
        registers.keySet().forEach(key -> {
            System.out.println(key + ": " + registers.get(key));
        });

        for (int i = 0; i < memorySize; i++) {
            System.out.println(i + " " + memory[i]);
        }
    }
}
