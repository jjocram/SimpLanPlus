package it.azzalinferrati;

import it.azzalinferrati.ast.SimpLanPlusVisitor;
import it.azzalinferrati.ast.SimpLanPlusVisitorImpl;
import it.azzalinferrati.ast.node.Node;
import it.azzalinferrati.lexer.SimpLanPlusLexer;
//import org.antlr.v4.runtime.ANTLRInputStream;
import it.azzalinferrati.parser.SimpLanPlusParser;
import it.azzalinferrati.parser.VerboseListener;
import it.azzalinferrati.semanticanalysis.Environment;
import it.azzalinferrati.semanticanalysis.SemanticError;
import it.azzalinferrati.semanticanalysis.exception.TypeCheckingException;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.ArrayList;

//import java.io.FileInputStream;

public class App {
    public static void main(String[] args) throws Exception {
        if(args.length != 1) {
            System.err.println("SimpLanPlus compiler: you must specify a file name!");
            System.exit(1);
        }
        // File to read
        String filename = args[0];
        //FileInputStream fileInputStream = new FileInputStream(filename);
        //ANTLRInputStream inputStream = new ANTLRInputStream(fileInputStream);
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
            semanticErrors.stream().forEach(System.err::println);
            System.exit(1);
        }

        try {
            AST.typeCheck();
        } catch (TypeCheckingException typeCheckingException) {
            System.err.println(typeCheckingException.getMessage());
            System.exit(1);
        }

    }
}
