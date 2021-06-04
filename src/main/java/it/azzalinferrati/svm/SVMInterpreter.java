package it.azzalinferrati.svm;

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

    public int sp() {
        return registers.get("$sp");
    }

    public int hp() {
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

    public void setIP(int address) {
        $ip = address;
    }

    public int getIP() {
        return $ip;
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

    public void updateRegister(String register, int value) {
        registers.put(register, value);
    }

    public int readRegister(String register) {
        return registers.get(register);
    }

    public void writeOnMemory(int address, int data) {
        memory[address].setData(data);
    }

    public int readFromMemory(int address) {
        return memory[address].getData();
    }

    public void freeMemory(int address) {
        memory[address].freeCell();
    }

    public void run() {

        //debugCPU();
        //System.out.println("---------------------------");

        while (true) {
            if (hp() >= sp()) {
                System.err.println("Error: out of memory");
                return;
            }

            SVMInstruction instruction = code.get($ip);
            $ip += 1;

            System.out.println(instruction);
            instruction.execute(this);
            //debugCPU();
            System.out.println("---------------------------");

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
