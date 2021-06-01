package it.azzalinferrati.svm;

import it.azzalinferrati.svm.instruction.SVMInstruction;

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
}

public class SVMInterpreter {
    private final int codeSize; //Max num of instructions
    private final int memorySize; //Max size of the memory (heap + stack)

    private List<SVMInstruction> code;
    private MemoryCell[] memory;

    private Map<String, Integer> registers;
    private int $ip; //Program Counter (not available in code generation)

    public SVMInterpreter(int codeSize, int memorySize, List<SVMInstruction> code) {
        this.codeSize = codeSize;
        this.memorySize = memorySize;
        this.code = code;

        memory = new MemoryCell[memorySize];
        for (int i = 0; i < memorySize; i++) {
            memory[i] = new MemoryCell(0, false);
        }

        $ip = 0;

        registers = new HashMap<>();
        registers.put("$sp", memorySize);
        registers.put("$hp", -1);
        registers.put("$fp", memorySize);
        registers.put("$ra", null);
        registers.put("$al", null);
        registers.put("$a0", null);
        registers.put("$t1", null);
    }

    private int sp() {
        return registers.get("$sp");
    }

    private int hp() {
        return registers.get("$hp");
//        var firstFreeMemoryCell = Arrays.stream(memory).filter(MemoryCell::isFree).findFirst();
//        if (firstFreeMemoryCell.isPresent()) {
//            for (int i = 0; i < memorySize; i++) {
//                if (memory[i] == firstFreeMemoryCell.get()) {
//                    return i;
//                }
//            }
//        }
//
//        return memorySize; // reach the end of memory nothing is free
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

    public void run() {
        while (true) {
            if (hp() >= sp()) {
                System.err.println("Error: out of memory");
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
                    memory[sp()].setData(registers.get(arg1));
                    registers.put("$sp", sp() - 1); // sp -= 1
                    break;
                case "pop":
                    registers.put("$sp", sp() + 1); // sp += 1
                    break;
                case "lw":
                    // "memory address -> memory index"
                    registers.put(arg1, memory[registers.get(arg2) + offset].getData());
                    break;
                case "sw":
                    // "memory address -> memory index"
                    memory[registers.get(arg2) + offset].setData(registers.get(arg1));
                    break;
                case "li":
                    registers.put(arg1, Integer.parseInt(arg2));
                    break;
                case "add":
                    registers.put(arg1, registers.get(arg2) + registers.get(arg2));
                    break;
                case "sub":
                    registers.put(arg1, registers.get(arg2) - registers.get(arg2));
                    break;
                case "mult":
                    registers.put(arg1, registers.get(arg2) * registers.get(arg2));
                    break;
                case "div":
                    registers.put(arg1, registers.get(arg2) / registers.get(arg2));
                    break;
                case "addi":
                    registers.put(arg1, registers.get(arg2) + Integer.parseInt(arg2));
                    break;
                case "subi":
                    registers.put(arg1, registers.get(arg2) - Integer.parseInt(arg2));
                    break;
                case "multi":
                    registers.put(arg1, registers.get(arg2) * Integer.parseInt(arg2));
                    break;
                case "divi":
                    registers.put(arg1, registers.get(arg2) / Integer.parseInt(arg2));
                    break;
                case "and": {
                    boolean input1 = registers.get(arg2) == 1;
                    boolean input2 = registers.get(arg3) == 1;
                    int result = input1 && input2 ? 1 : 0;
                    registers.put(arg1, result);
                    break;
                }
                case "or": {
                    boolean input1 = registers.get(arg2) == 1;
                    boolean input2 = registers.get(arg3) == 1;
                    int result = input1 || input2 ? 1 : 0;
                    registers.put(arg1, result);
                    break;
                }
                case "not": {
                    int result = registers.get(arg2) == 1 ? 0 : 1;
                    registers.put(arg1, result);
                    break;
                }
                case "andb": {
                    boolean input1 = registers.get(arg2) == 1;
                    boolean input2 = arg3.equals("1");
                    int result = input1 && input2 ? 1 : 0;
                    registers.put(arg1, result);
                    break;
                }
                case "orb": {
                    boolean input1 = registers.get(arg2) == 1;
                    boolean input2 = arg3.equals("1");
                    int result = input1 || input2 ? 1 : 0;
                    registers.put(arg1, result);
                    break;
                }
                case "notb":{
                    int result = arg2.equals("1") ? 0 : 1;
                    registers.put(arg1, result);
                    break;
                }
                case "mv":
                    registers.put(arg1, registers.get(arg2));
                    break;
                case "beq":
                    if (registers.get(arg1).equals(registers.get(arg2))){
                        $ip = Integer.parseInt(arg3);
                    }
                    break;
                case "bleq":
                    if (registers.get(arg1) <= registers.get(arg2)){
                        $ip = Integer.parseInt(arg3);
                    }
                    break;
                case "b":
                    $ip = Integer.parseInt(arg1);
                    break;
                case "jal":
                    registers.put("$ra", $ip);
                    $ip = Integer.parseInt(arg1);
                    break;
                case "jr":
                    $ip = registers.get(arg1);
                    break;
                case "del":
                    memory[offset + hp()].freeCell();
                    break;
                case "print":
                    System.out.println(registers.get(arg1));
                    break;
                case "halt":
                    return;
                default:
                    System.err.println("Error: unrecognized SVMInstruction");
                    return;
            }
        }
    }
}
