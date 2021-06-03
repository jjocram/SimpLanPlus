package it.azzalinferrati.semanticanalysis;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import it.azzalinferrati.ast.node.IdNode;
import it.azzalinferrati.ast.node.type.FunTypeNode;
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

    public Environment(Environment e) {
        this(new ArrayList<>(), e.nestingLevel, e.offset);

        for (var scope : e.symbolTable) {
            final Map<String, STEntry> copiedScope = new HashMap<>();
            for (var id : scope.keySet()) {
                copiedScope.put(id, new STEntry(scope.get(id)));
            }
            this.symbolTable.add(copiedScope);
        }

    }

    public int getNestingLevel() {
        return nestingLevel;
    }

    private Map<String, STEntry> currentScope() {
        return symbolTable.get(nestingLevel);
    }

    /**
     * Pushes new scope in the Symbol Table stack.
     * Increments the nesting level.
     * Sets the offset to 0.
     */
    public void pushNewScope() {
        symbolTable.add(new HashMap<>());
        nestingLevel++;
        offset = 0;
    }

    /**
     * Adds a new variable named [id] of type [type] into the current scope.
     * The offset of functions will be set to -1, the offset of variables will be set to the current offset and later incremented.
     *  
     * @param id the identifer of the variable or function.
     * @param type the type of the variable or function.
     * @throws MultipleDeclarationException when [id] is already present in the head of the Symbol Table.
     */
    public STEntry addNewDeclaration(final String id, final TypeNode type) throws MultipleDeclarationException {
        STEntry stEntry;
        if(type instanceof FunTypeNode) {
            stEntry = new STEntry(nestingLevel, type, -1); // -1 is correct since after pop() is called the offset for the following variable will be set to 0.
        } else {
            stEntry = new STEntry(nestingLevel, type, offset);
            offset += 1; // 1 = 4 Byte, for integers, boolean (1/0), pointers to boolean/integers.
        }
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
     * Sets the offset to the maximum offset in the previous scope + 1.
     */
    public void popScope() {
        symbolTable.remove(nestingLevel);
        nestingLevel--;
        if(nestingLevel >= 0) {
            var stEntry = symbolTable.get(nestingLevel).values().stream().max(Comparator.comparing(STEntry::getOffset));
            offset = stEntry.map(entry -> entry.getOffset() + 1).orElse(0);
        }
    }

    /**
     * Returning a new environment which has, for each identifie, the maximum effect
     * set in the two environments. Assumes env1 and env2 are identical, except for
     * the identifiers' statuses.
     * 
     * @param env1 first environment
     * @param env2 second environment
     * @return the maximum environment of the two
     */
    public static Environment max(final Environment env1, final Environment env2) {
        var maxEnv = new Environment(new ArrayList<>(), env1.nestingLevel, env1.offset);
        for (int i = 0, size = env1.symbolTable.size(); i < size; i++) {
            var ithScope1 = env1.symbolTable.get(i);
            var ithScope2 = env2.symbolTable.get(i);
            final HashMap<String, STEntry> maxHashMap = new HashMap<>();
            for (var id : ithScope1.keySet()) {
                var entry1 = ithScope1.get(id);
                var entry2 = ithScope2.get(id);

                var maxEntry = new STEntry(entry1.getNestingLevel(), entry1.getType(), entry1.getOffset());
                maxEntry.setStatus(Effect.max(entry1.getStatus(), entry2.getStatus()));

                maxHashMap.put(id, maxEntry);
            }
            maxEnv.symbolTable.add(maxHashMap);
        }
        return maxEnv;
    }

    /**
     * Checks the status of the variable and its status and updates it following the
     * given rule, if the new status is Effect.ERROR then returns a SemanticError
     * wrapped in a ArrayList. The ArrayList is returned just for simplicity.
     * 
     * @return a list with errors found while checking the status of each variable
     *         used in the expression
     */
    public ArrayList<SemanticError> checkVariableStatus(final IdNode variable, final BiFunction<Effect, Effect, Effect> rule, final Effect effectToApply) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        try {
            var stEntry = lookup(variable.getId());
            Effect status = rule.apply(stEntry.getStatus(), effectToApply);

            stEntry.setStatus(status);
            variable.setStatus(status);

            if (status.equals(Effect.ERROR)) {
                errors.add(new SemanticError(variable.getId() +  " is used after deletion"));
            }
        } catch (MissingDeclarationException exception) {
            errors.add(new SemanticError("Cannot perform effect analysis on " + variable.getId() + " since it is not declared."));
        }

        return errors;
    }
}
