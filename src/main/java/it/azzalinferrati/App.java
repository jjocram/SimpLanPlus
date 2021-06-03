package it.azzalinferrati;

import it.azzalinferrati.ast.SimpLanPlusVisitor;
import it.azzalinferrati.ast.SimpLanPlusVisitorImpl;
import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.lexer.SimpLanPlusLexer;
import it.azzalinferrati.parser.SimpLanPlusParser;
import it.azzalinferrati.parser.VerboseListener;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;
import it.azzalinferrati.svm.SVMInterpreter;
import it.azzalinferrati.svm.ast.SVMVisitorImpl;
import it.azzalinferrati.svm.lexer.SVMLexer;
import it.azzalinferrati.svm.parser.SVMParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        if(args.length != 1) {
            System.err.println("SimpLanPlus compiler: you must specify a file name!");
            System.exit(1);
        }
        // File to read
        String filename = args[0];
        CharStream charStream = CharStreams.fromFileName(filename);

        // SimpLan lexer
        SimpLanPlusLexer lexer = new SimpLanPlusLexer(charStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        
        lexer.removeErrorListeners();
        lexer.addErrorListener(new VerboseListener());

        // Check for lexical errors
        if (lexer.errorCount() > 0) {
            System.err.println("The program was not in the right format. The program cannot compile.");
            System.exit(1);
        }

        // SimpLan parser and visitor
        SimpLanPlusParser parser = new SimpLanPlusParser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(new VerboseListener());
        SimpLanPlusVisitor<Node> visitor = new SimpLanPlusVisitorImpl();

        Node AST = visitor.visit(parser.block());

        if (parser.getNumberOfSyntaxErrors() > 0){
            System.err.println("There was syntax errors in the file, look above.");
            System.exit(1);
        }

        Environment environment = new Environment(new ArrayList<>());
        
        ArrayList<SemanticError> semanticErrors = AST.checkSemantics(environment);
        
        if(!semanticErrors.isEmpty()) {
            semanticErrors.forEach(System.err::println);
            System.exit(1);
        }
        
        try {
            AST.typeCheck();
        } catch (TypeCheckingException typeCheckingException) {
            System.err.println(typeCheckingException.getMessage());
            System.exit(1);
        }

        String generatedCode = AST.codeGeneration() + "halt";

        //System.out.println(generatedCode);


        CharStream SVMcharStream = CharStreams.fromString(generatedCode);

        SVMLexer SVMlexer = new SVMLexer(SVMcharStream);
        CommonTokenStream SVMtokenStream = new CommonTokenStream(SVMlexer);

        SVMlexer.removeErrorListeners();
        SVMlexer.addErrorListener(new VerboseListener());

        if (SVMlexer.errorCount() > 0) {
            System.err.println("Assembly code was not in the right format.");
            System.exit(1);
        }

        SVMParser SVMparser = new SVMParser(SVMtokenStream);
        SVMparser.removeErrorListeners();
        SVMparser.addErrorListener(new VerboseListener());
        SVMVisitorImpl SVMvisitor = new SVMVisitorImpl();
        SVMvisitor.visit(SVMparser.assembly());

        SVMInterpreter interpreter = new SVMInterpreter(1000, 11, SVMvisitor.getCode());
        interpreter.run();

    }
}
