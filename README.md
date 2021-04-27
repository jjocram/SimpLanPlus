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