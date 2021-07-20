# SimpLanPlus

Repository for the course "Compilatori e interpreti" (Compilers and Interpreters) at the University of Bologna, A.Y. 2020/21.

## Instructions for compiling
```bash
mvn clean compile assembly:single
```

## Instructions for running (after compiling)
```bash
java -jar target/SimpLanPlus.jar fileName.ext [additional args]
```
with ```fileName.ext``` the name of a source code file written in **SimpLanPlus** language or in the **SVM Assembly** language (read the next section for more information).
The file name cannot start with a dash (`-`).

### Additional arguments
One might add different arguments to print out additional information or change the behaviour of the program while compiling or interpreting:

- `--ast` prints out the information available in the nodes of the **Abstract Syntax Tree (AST)** after the semantic and type checks. This will be skipped if argument `-mode=run` is present;
- `--mode=compile` only compiles the code and saves it in a file named ```fileName.asm```;
- `--mode=run` only runs the Assembly code for the **SimpLanPlus Virtual Machine (SVM)** which is present in the file given as parameter;
- `--mode=all` compiles the code and runs it;
- `--debugcpu` shows the content of the memory and registers of the SVM at each instruction available in the compiled (or read) Assembly code. This will be skipped if argument `-mode=compile` is present;
- `--codesize=c` requires the memory size for hosting the code to be of size *c* (i.e. *c* Assembly instructions can be processed). *c* must be a positive integer otherwise 1000 is set. This will be skipped if argument `-mode=compile` is present;
- `--memsize=m` requires the memory size for executing the program to be of size *m* (both stack and heap). *m* must be a positive integer otherwise 1000 is set. This will be skipped if argument `-mode=compile` is present;

By default:

- the AST is not printed in the console;
- the file given as a parameter contains SimpLanPlus code to be both compiled and interpreted by the SVM;
- the CPU debug is disabled;
- both `codesize` and `memsize` are set to 1000.

## Registers in the SVM

Available registers in the SVM:

- **$sp**: Stack Pointer points to the top of the stack
- **$bsp**: Base Stack Pointer points to the address where is stored the stack pointer prior a function call
- **$hp**: Heap Pointer points to the top of the heap
- **$fp**: Frame Pointer points to the current Access Link relative to the active frame
- **$al**: Access Link is used to go through the static chain (i.e. scopes)
- **$ra**: Return address stores the return address
- **$a0**: Accumulator is used to store the computed value of expressions
- **$t1**: General purpose register is used to store temporary values

## Activation Record structure in the SVM
| Activation Record           |
|-----------------------------|
| RA (Return Address)         | $sp
| Par 1                       |
| ...                         |
| Par N-1                     |
| Par N                       |
| Dec 1                       |
| ...                         |
| Dec M-1                     |
| Dec M                       | 
| AL (Access Link)            | $fp
| Previous FP (Frame Pointer) |
