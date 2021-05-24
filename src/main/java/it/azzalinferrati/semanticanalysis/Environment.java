package it.azzalinferrati.semanticanalysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.exception.MissingDeclarationException;
import it.azzalinferrati.semanticanalysis.exception.MultipleDeclarationException;

public class Environment {

    // Symbol Table implemented as a stack of hash tables.
    // Since a List is used, the last element of the list is regarded as the top of
    // the stack.
    final private List<Map<String, STEntry>> symbolTable;

    // Current scope level. At the beginning of the file it is -1.
    // It gets incremented for each scope entrance.
    // It gets decremented for each scope exit.
    private int nestingLevel;

    // Offset for code generation.
    private int offset;

    public Environment(List<Map<String, STEntry>> symTable, int nestingLevel, int offset) {
        this.symbolTable = symTable;
        this.nestingLevel = nestingLevel;
        this.offset = offset;
    }

    public Environment(List<Map<String, STEntry>> symTable) {
        this(symTable, -1, 0);
    }

    private Map<String, STEntry> currentScope() {
        return symbolTable.get(nestingLevel);
    }

    /**
     * Pushes new scope in the Symbol Table stack.
     * Increments the nesting level.
     */
    public void pushNewScope() {
        symbolTable.add(new HashMap<>());
        nestingLevel++;
    }

    /**
     * Adds a new variable named [id] of type [type] into the current scope.
     *  
     * @param id the identifer of the variable or function.
     * @param type the type of the variable or function.
     * @throws MultipleDeclarationException when [id] is already present in the head of the Symbol Table.
     */
    public STEntry addNewDeclaration(final String id, final TypeNode type) throws MultipleDeclarationException {
        STEntry stEntry = new STEntry(nestingLevel, type, offset--);
        STEntry declaration = currentScope().put(id, stEntry);
        if(declaration != null) {
            throw new MultipleDeclarationException("Multiple declaration for ID: " + id + ". It was previously defined of type: " + declaration.getType().toPrint("") + ".");
        }

        return stEntry;
    }

    /**
     * Searches [id] in the Symbol Table and returns its entry, if present.
     * 
     * @param id the identifer of the variable or function.
     * @return the entry in the symbol table of the variable or function with that identifier.
     * @throws MissingDeclarationException if [id] is not present.
     */
    public STEntry lookup(final String id) throws MissingDeclarationException {
        for(int i = nestingLevel; i >= 0; i--) {
            var ithScope = symbolTable.get(i);
            var stEntry = ithScope.get(id);
            if(stEntry != null) {
                return stEntry;
            }
        }
        
        throw new MissingDeclarationException("Missing declaration for ID: " + id);
    }

    /**
     * Pops the current active scope from the Symbol Table stack.
     * Decrements the nesting level.
     */
    public void popScope() {
        symbolTable.remove(nestingLevel);
        nestingLevel--;
    }
}
