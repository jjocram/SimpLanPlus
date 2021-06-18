package it.azzalinferrati.svm;

import it.azzalinferrati.svm.exception.CodeSizeTooSmallException;
import it.azzalinferrati.svm.exception.MemoryAccessException;
import it.azzalinferrati.svm.exception.UninitializedVariableException;
import it.azzalinferrati.svm.instruction.SVMInstruction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MemoryCell {
    private Integer data;
    private boolean isUsed;

    public MemoryCell(int data, boolean isUsed) {
        this.data = data;
        this.isUsed = isUsed;
    }

    public MemoryCell() {
        data = null;
        isUsed = false;
    }

    public int getData() throws UninitializedVariableException {
        if (data == null) {
            throw new UninitializedVariableException("Access to memory cell which contains uninitialized data");
        } else {
            return data;
        }
    }

    public void setData(Integer data) {
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
        return "| " + (data != null && data >= 0 ? " " : "") + data + "\t|";
    }
}

public class SVMInterpreter {
    private final int memorySize; // Max size of the memory (heap + stack)

    private List<SVMInstruction> code;
    private MemoryCell[] memory;

    private Map<String, Integer> registers;
    private int $ip; // Program Counter (not available in code generation)

    private String lastUpdatedRegister;
    private int lastUpdatedMemoryCell;

    private int globalCounter; // Tracks the total number of run instructions ($ip is not monotone, globalCounter is)

    public SVMInterpreter(int codeSize, int memorySize, List<SVMInstruction> code) throws CodeSizeTooSmallException {
        this.memorySize = memorySize;
        this.code = code;

        if (codeSize < code.size()) {
            throw new CodeSizeTooSmallException("Requested code area size: " + codeSize + ", number of instructions given: " + code.size() + ".");
        }

        memory = new MemoryCell[memorySize];
        for (int i = 0; i < memorySize; i++) {
            memory[i] = new MemoryCell();
        }

        $ip = 0;
        globalCounter = 0;

        registers = new HashMap<>();
        registers.put("$sp", memorySize);
        registers.put("$bsp", memorySize);
        //registers.put("$hp", -1);
        registers.put("$fp", memorySize - 1);
        registers.put("$ra", null);
        registers.put("$al", null);
        registers.put("$a0", null);
        registers.put("$t1", null);

        lastUpdatedRegister = "";
        lastUpdatedMemoryCell = memorySize;
    }

    private int sp() {
        return registers.get("$sp");
    }

    private int hp() {
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

    private int bsp() {
        return registers.get("$bsp");
    }

    private void updateRegister(String register, int value) {
        registers.put(register, value);
        lastUpdatedRegister = register;
        lastUpdatedMemoryCell = memorySize;
    }

    private int readRegister(String register) {
        return registers.get(register);
    }

    private void writeOnMemory(int address, int data) throws MemoryAccessException {
        try {
            memory[address].setData(data);
            lastUpdatedMemoryCell = address;
            lastUpdatedRegister = "";
        } catch (IndexOutOfBoundsException e) {
            throw new MemoryAccessException("Address " + address + " cannot be accessed");
        }
    }

    private void resetCell(int address) throws MemoryAccessException {
        try {
            memory[address].setData(null);
            lastUpdatedMemoryCell = address;
            lastUpdatedRegister = "";
        } catch (IndexOutOfBoundsException e) {
            throw new MemoryAccessException("Address " + address + " cannot be accessed");
        }
    }

    private int readFromMemory(int address) throws MemoryAccessException, UninitializedVariableException {
        try {
            return memory[address].getData();
        } catch (IndexOutOfBoundsException e) {
            throw new MemoryAccessException("Address " + address + " cannot be accessed");
        }
    }

    private void freeMemory(int address) throws MemoryAccessException {
        try {
            memory[address].freeCell();
        } catch (IndexOutOfBoundsException e) {
            throw new MemoryAccessException("Address " + address + " cannot be accessed");
        }
    }

    public void run(boolean activeDebug) throws MemoryAccessException, UninitializedVariableException {
        if (activeDebug) {
            System.out.println("Initial state of the SVM");
            debugCPU();
            System.out.println("---------------------------");
        }

        while (true) {
            if (hp() > sp()) {
                throw new MemoryAccessException("Reached max memory limit");
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
                    resetCell(sp()); // It is actually not needed, but it is easier for debugging purposes.
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
                        writeOnMemory((readRegister(arg2) + offset), readRegister(arg1));
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
                    System.out.println((activeDebug ? "[PROGRAM OUTPUT] " : "") + readRegister(arg1));
                    break;
                case "halt":
                    return;
                default:
                    System.err.println("ERROR: Unrecognized Assembly instruction: " + instruction + ".");
                    return;
            }

            if (activeDebug) {
                System.out.println("Instruction #" + globalCounter + " - " + instruction + "\n");
                debugCPU();
                System.out.println("---------------------------");
            }

            globalCounter += 1;
        }
    }

    private void debugCPU() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("| $ip: ").append($ip).append(" | ");
        registers.keySet().forEach(key -> {
            buffer.append(key).append(": ").append(registers.get(key)).append(lastUpdatedRegister.equals(key) ? " (*) | " : " | ");
        });

        buffer.append("\n\n");

        for (int i = 0; i < memorySize; i++) {
            buffer
                    .append(i).append("\t").append(memory[i])
                    .append(i == fp() ? " <- $fp" : "")
                    .append(i == sp() ? " <- $sp" : "")
                    .append(i == bsp() ? " <- $bps" : "")
                    .append(lastUpdatedMemoryCell == i ? " (*)\n" : "\n");
        }

        System.out.println(buffer.toString());
    }
}
