package it.azzalinferrati.parser;

import org.antlr.v4.runtime.*;

import java.util.Collections;
import java.util.List;

public class VerboseListener extends BaseErrorListener {
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        final List<String> stack = ((Parser) recognizer).getRuleInvocationStack();
        Collections.reverse(stack);
        

        System.err.println("Rule stack: " + stack);
        System.err.println("Line " + line + ":" + charPositionInLine + " " + msg);
        underlineError(recognizer, (Token) offendingSymbol, line, charPositionInLine);
    }

    private void underlineError(Recognizer<?, ?> recognizer, Token offendingToken, int line, int charPositionInLine) {
        CommonTokenStream tokens = (CommonTokenStream) recognizer.getInputStream();
        String input = tokens.getTokenSource().getInputStream().toString();
        String[] lines = input.split("\n");
        String errorLine = lines[line - 1];

        System.err.println(errorLine);
        for (int i = 0; i < charPositionInLine; i++) {
            System.err.print(" ");
        }

        int start = offendingToken.getStartIndex();
        int stop = offendingToken.getStopIndex();
        if (start >= 0 && stop >= 0) {
            for (int i = start; i <= stop; i++) {
                System.err.print("^");
            }
        }

        System.err.println();
    }
}
