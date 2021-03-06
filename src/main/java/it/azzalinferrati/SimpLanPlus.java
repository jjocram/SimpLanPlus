package it.azzalinferrati;

import it.azzalinferrati.ast.SimpLanPlusVisitorImpl;
import it.azzalinferrati.ast.node.statement.BlockNode;
import it.azzalinferrati.lexer.SimpLanPlusLexer;
import it.azzalinferrati.parser.SimpLanPlusParser;
import it.azzalinferrati.parser.VerboseListener;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;
import it.azzalinferrati.svm.SVMInterpreter;
import it.azzalinferrati.svm.ast.SVMVisitorImpl;
import it.azzalinferrati.svm.exception.CodeSizeTooSmallException;
import it.azzalinferrati.svm.exception.MemoryAccessException;
import it.azzalinferrati.svm.exception.UninitializedVariableException;
import it.azzalinferrati.svm.lexer.SVMLexer;
import it.azzalinferrati.svm.parser.SVMParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Main class for the SimpLanPlus compiler and interpreter
 */
public class SimpLanPlus {
    public static void main(String[] args) {
        try {
            /* HEADER */
            System.out.println("[-- SimpLanPlus --]");

            /* FLAGS */
            Flags flags = new Flags(args);
            String filename = args[0];

            if(!Paths.get(filename).toFile().exists()) {
                throw new NonExistentInputFileException("Input file " + filename + " does not exist.");
            }

            String fileContent = Files.readString(Paths.get(filename));

            System.out.println("Input file:\t" + filename);
            System.out.println("Flags:\t\t" + flags);

            /* COMPILER */
            if (flags.mode().equals("compile")) {
                var assembly = compile(flags, fileContent);

                // Generating the Assembly file name.
                int fileExtBeginIndex = filename.lastIndexOf(".");
                if (fileExtBeginIndex > 0) { // Has extension after at least one character.
                    filename = filename.substring(0, fileExtBeginIndex);
                } // else: Does not have any extension

                Files.write(Paths.get(filename + ".asm"), assembly.getBytes());
            }

            /* INTERPRETER */
            if (flags.mode().equals("run")) {
                run(flags, fileContent);
            }

            /* ALL: COMPILER & INTERPRETER */
            if (flags.mode().equals("all")) {
                run(flags, compile(flags, fileContent));
            }
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
            System.exit(2);
        }

    }

    /**
     * Handles the compilation phase.
     *
     * @param flags           for the compilation phase
     * @param simpLanPlusCode the SimpLanPlusCode
     * @return the generated Assembly code for the SimpLanPlus Virtual Machine (SVM)
     */
    private static String compile(final Flags flags, final String simpLanPlusCode) {
        /* SIMPLANPLUS LEXER */
        SimpLanPlusLexer slpLexer = new SimpLanPlusLexer(CharStreams.fromString(simpLanPlusCode));
        CommonTokenStream slpLexerTokens = new CommonTokenStream(slpLexer);

        slpLexer.removeErrorListeners();
        slpLexer.addErrorListener(new VerboseListener());
        // Checking for lexical errors.
        if (slpLexer.errorCount() > 0) {
            System.err.println("Lexical analysis:");
            System.err.println("There are lexical errors in the file. It cannot compile.");
            System.exit(1);
        }

        /* SIMPLANPLUS PARSER */
        SimpLanPlusParser slpParser = new SimpLanPlusParser(slpLexerTokens);
        slpParser.removeErrorListeners();
        slpParser.addErrorListener(new VerboseListener());

        // Creating the tree visitor.
        SimpLanPlusVisitorImpl slpVisitor = new SimpLanPlusVisitorImpl();

        // Visiting the tree and generating the AST.
        BlockNode AST = slpVisitor.visitBlock(slpParser.block());
        AST.setMainBlock(); // The main block is special therefore just here a flag is set to signal this
        // (it's important for the code generation).

        // Checking for syntactical errors.
        if (slpParser.getNumberOfSyntaxErrors() > 0) {
            System.err.println("Syntactic analysis:");
            System.err.println("There are syntactical errors in the file, look above.");
            System.exit(1);
        }

        // Creating the environment for performing the semantic analysis and the
        // following code generation.
        Environment env = new Environment();

        // Checking for semantic errors.
        ArrayList<SemanticError> semanticErrors = AST.checkSemantics(env);
        if (!semanticErrors.isEmpty()) {
            System.err.println("Semantic analysis:");
            semanticErrors.stream().filter(DistinctByKey.distinctByKey(SemanticError::toString)).forEach(System.err::println);
            System.exit(1);
        }

        // Checking for type errors.
        try {
            AST.typeCheck();
        } catch (TypeCheckingException typeCheckingException) {
            System.out.println("Type checking:");
            System.err.println(typeCheckingException.getMessage());
            System.exit(1);
        }

        // Checking for effect analysis errors.
        ArrayList<SemanticError> effectsAnalysisErrors = AST.checkEffects(env);
        if (!effectsAnalysisErrors.isEmpty()) {
            System.err.println("Effects analysis:");
            effectsAnalysisErrors.stream().filter(DistinctByKey.distinctByKey(SemanticError::toString)).forEach(System.err::println);
            System.exit(1);
        }

        // Printing (if requested) the AST.
        if (flags.ast()) {
            System.out.println("Information from the AST:\n" + AST);
        }

        return AST.codeGeneration();
    }

    /**
     * Handles the interpretation phase.
     *
     * @param flags        for the interpretation phase
     * @param assemblyCode the SVM Assembly code
     */
    private static void run(final Flags flags, final String assemblyCode) {
        /* SVM LEXER */
        SVMLexer svmLexer = new SVMLexer(CharStreams.fromString(assemblyCode));
        CommonTokenStream svmLexerTokens = new CommonTokenStream(svmLexer);

        svmLexer.removeErrorListeners();
        svmLexer.addErrorListener(new VerboseListener());

        // Checking for lexical errors.
        if (svmLexer.errorCount() > 0) {
            System.err.println("There are lexical errors in the generated Assembly code. It cannot compile.");
            System.exit(1);
        }

        /* SVM PARSER */
        SVMParser svmParser = new SVMParser(svmLexerTokens);
        svmParser.removeErrorListeners();
        svmParser.addErrorListener(new VerboseListener());

        // Visiting the tree and generating the AST.
        SVMVisitorImpl svmVisitor = new SVMVisitorImpl();
        svmVisitor.visit(svmParser.assembly());

        /* SVM INTERPRETER */
        try {
            SVMInterpreter svmInterpreter = new SVMInterpreter(flags.codesize(), flags.memsize(), svmVisitor.getCode());
            System.out.println("Program output (can be empty):");
            svmInterpreter.run(flags.debugcpu());
        } catch (MemoryAccessException | CodeSizeTooSmallException | UninitializedVariableException exc) {
            System.err.println("Error: " + exc.getMessage());
            System.exit(1);
        }
    }
}

/**
 * Flags for the program execution.
 */
final class Flags {
    private final boolean ast;

    private final String mode;

    private final boolean debugcpu;

    private int codesize;

    private int memsize;

    public Flags(final String[] args) throws WrongArgumentsException {
        if (args.length < 1 || Arrays.stream(args).allMatch(arg -> arg.startsWith("--"))) {
            System.err.print("Usage: ");
            System.err.println("java -jar /path/to/SimpLanPlus.jar <sourcefile> [--ast] [--mode=(compile|run|all)] [--debugcpu] [--codesize=<positive_integer>] [--memsize=<positive_integer>]\n");
            throw new WrongArgumentsException("ERROR: You must specify a file to compile or run!");
        }

        List<String> arguments = Arrays.asList(args);

        ast = arguments.contains("--ast");

        debugcpu = arguments.contains("--debugcpu");

        if (arguments.contains("--mode=compile")) {
            mode = "compile";
        } else if (arguments.contains("--mode=run")) {
            mode = "run";
        } else if (arguments.contains("--mode=all")) {
            mode = "all";
        } else if (arguments.contains("--mode=")) { // Argument starts with "-mode=" but the remaining part is not
            // expected.
            throw new WrongArgumentsException("Unrecognized mode. These are the available modes: compile, run, all.");
        } else { // There is no "mode" argument.
            mode = "all";
        }

        Optional<String> optCodeSize = arguments.stream().filter(a -> a.startsWith("--codesize=")).findFirst();
        if (optCodeSize.isPresent()) {
            try {
                codesize = Integer.parseInt(optCodeSize.get().split("=")[1]);
                if (codesize <= 0) {
                    codesize = 1000;
                }
            } catch (NumberFormatException exc) {
                codesize = 1000;
            } catch (ArrayIndexOutOfBoundsException exc) {
                throw new WrongArgumentsException("Argument codesize requires a positive integer to be specified.");
            }
        } else {
            codesize = 1000;
        }

        Optional<String> optMemSize = arguments.stream().filter(a -> a.startsWith("--memsize=")).findFirst();
        if (optMemSize.isPresent()) {
            try {
                memsize = Integer.parseInt(optMemSize.get().split("=")[1]);
                if (memsize <= 0) {
                    memsize = 1000;
                }
            } catch (NumberFormatException exc) {
                memsize = 1000;
            } catch (ArrayIndexOutOfBoundsException exc) {
                throw new WrongArgumentsException("Argument memsize requires a positive integer to be specified.");
            }
        } else {
            memsize = 1000;
        }
    }

    public int codesize() {
        return codesize;
    }

    public int memsize() {
        return memsize;
    }

    public String mode() {
        return mode;
    }

    public boolean ast() {
        return ast;
    }

    public boolean debugcpu() {
        return debugcpu;
    }

    @Override
    public String toString() {
        return (mode.equals("all") ? "Compile and run" : mode) + (ast ? ", print AST" : "")
                + (debugcpu ? ", debug CPU" : "") + (", max " + codesize + " Assembly instructions")
                + (", max " + memsize + " virtual memory.");
    }
}

final class WrongArgumentsException extends Exception {
    private static final long serialVersionUID = 8745799711138778409L;

    public WrongArgumentsException(String message) {
        super(message);
    }
}

final class NonExistentInputFileException extends Exception {
    private static final long serialVersionUID = 8745799711138778410L;

    
    public NonExistentInputFileException(String message) {
        super(message);
    }
}