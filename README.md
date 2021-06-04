# SimpLanPlus

Repository for the course "Compilatori e interpreti" (Compilers and Interpreters) at the University of Bologna, A.Y. 2020/21.

## Instructions for compiling
```bash
mvn clean compile assembly:single
```

## Instructions for running (after compiling)
```bash
java -jar target/SimpLanPlusCompiler.jar fileName.ext
```
with ```fileName.ext``` the name of a source code file written in **SimpLanPlus**.

## Registers

Available registers:

- **$sp**: Stack Pointer points to the top of the stack
- **$hp**: Heap Pointer points to the top of the heap
- **$fp**: Frame Pointer points to the current Access Link relative to the active frame
- **$al**: Access Link is used to go through the static chain (i.e. scopes)
- **$ra**: Return address stores the return address
- **$a0**: Accumulator is used to store the computed value of expressions
- **$t1**: General purpose register is used to store temporary values

## Activation Record structure
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
| Dec M                       | $fp
| Previous FP (Frame Pointer) |
