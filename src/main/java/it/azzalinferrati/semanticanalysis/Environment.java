package it.azzalinferrati.semanticanalysis;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import it.azzalinferrati.ast.node.LhsNode;
import it.azzalinferrati.ast.node.type.FunTypeNode;
import it.azzalinferrati.ast.node.type.TypeNode;
import it.azzalinferrati.semanticanalysis.exception.MissingDeclarationException;
import it.azzalinferrati.semanticanalysis.exception.MultipleDeclarationException;

/**
 * Symbol Table implementation.
 * <p>
 * It is implemented as a list of hashmaps and relies on {@code STEntry}.
 */
public class Environment {

    // Symbol Table implemented as a stack of hash tables.
    // Since a List is used, the last element of the list is regarded as the top of
    // the stack.
    final private List<Map<String, STEntry>> symbolTable;

    // Current scope level. At the beginning of the program it is -1.
    // Therefore the main block has nesting level 0.
    // It gets incremented for each scope entrance.
    // It gets decremented for each scope exit.
    private int nestingLevel;

    // Offset for variables in the code generation.
    private int offset;

    /**
     * Constructor for {@code Environment}
     *
     * @param symTable     a list of hashmaps (that can be empty)
     * @param nestingLevel a positive integer generally, a part from -1 that is the
     *                     minimum value that it can assume
     * @param offset       a positive integer, the minimum value that it can assume
     *                     is 0
     */
    public Environment(List<Map<String, STEntry>> symTable, int nestingLevel, int offset) {
        this.symbolTable = symTable;
        this.nestingLevel = nestingLevel;
        this.offset = offset;
    }

    /**
     * Creates an empty environment with no scopes.
     */
    public Environment() {
        this(new ArrayList<>(), -1, 0);
    }

    /**
     * Copy constructor of {@code Environment}
     *
     * @param e environment
     */
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

    /**
     * @return the current nesting level.
     */
    public int getNestingLevel() {
        return nestingLevel;
    }

    /**
     * @return the current active scope.
     */
    private Map<String, STEntry> currentScope() {
        return symbolTable.get(nestingLevel);
    }

    /**
     * Pushes new scope in the Symbol Table stack. Increments the nesting level.
     * Sets the offset to 0.
     */
    public void pushNewScope() {
        symbolTable.add(new HashMap<>());
        nestingLevel += 1;
        offset = 0;
    }

    /**
     * Adds a new scope to the environment.
     *
     * @param scope the scope to add to the Symbol Table stack
     */
    private void pushNewScope(Map<String, STEntry> scope) {
        symbolTable.add(scope);
        nestingLevel += 1;
        offset = 0;
    }

    /**
     * Adds a new variable named [id] of type [type] into the current scope. The
     * offset of functions will be set to -1, the offset of variables will be set to
     * the current offset and later incremented.
     *
     * @param id   the identifer of the variable or function.
     * @param type the type of the variable or function.
     * @throws MultipleDeclarationException when [id] is already present in the head
     *                                      of the Symbol Table.
     */
    public STEntry addNewDeclaration(final String id, final TypeNode type) throws MultipleDeclarationException {
        STEntry stEntry;
        if (type instanceof FunTypeNode) {
            stEntry = new STEntry(nestingLevel, type, -1); // -1 is correct since after pop() is called the offset for
            // the following variable will be set to 0.
        } else {
            stEntry = new STEntry(nestingLevel, type, offset);
            offset += 1; // 1 = 4 Byte, for integers, boolean (1/0), pointers to boolean/integers.
        }
        STEntry declaration = currentScope().put(id, stEntry);
        if (declaration != null) {
            throw new MultipleDeclarationException("Multiple declaration for ID: " + id
                    + ". It was previously defined of type: " + declaration.getType() + ".");
        }

        return stEntry;
    }

    /**
     * Adds a new variable named [id] of type [type] into the current scope. The
     * caller is sure that [id] does not exist in the current scope: if it does,
     * then unexpected behavior could occur. The offset of functions will be set to
     * -1, the offset of variables will be set to the current offset and later
     * incremented.
     *
     * @param id   the identifer of the variable or function.
     * @param type the type of the variable or function.
     */
    public STEntry addUniqueNewDeclaration(final String id, final TypeNode type) {
        STEntry stEntry;
        if (type instanceof FunTypeNode) {
            stEntry = new STEntry(nestingLevel, type, -1); // -1 is correct since after pop() is called the offset for
            // the following variable will be set to 0.
        } else {
            stEntry = new STEntry(nestingLevel, type, offset);
            offset += 1; // 1 = 4 Byte, for integers, boolean (1/0), pointers to boolean/integers.
        }
        STEntry declaration = currentScope().put(id, stEntry);
        if (declaration != null) {
            System.err.println("Unexpected multiple assignment for ID: " + id + ". It was previously defined of type: "
                    + declaration.getType() + ".");
        }

        return stEntry;
    }

    /**
     * Searches [id] in the Symbol Table and returns its entry, if present.
     *
     * @param id the identifer of the variable or function.
     * @return the entry in the symbol table of the variable or function with that
     * identifier.
     * @throws MissingDeclarationException if [id] is not present.
     */
    public STEntry lookup(final String id) throws MissingDeclarationException {
        for (int i = nestingLevel; i >= 0; i--) {
            var ithScope = symbolTable.get(i);
            var stEntry = ithScope.get(id);
            if (stEntry != null) {
                return stEntry;
            }
        }

        throw new MissingDeclarationException("Missing declaration for ID: " + id);
    }

    /**
     * Searches [id] in the Symbol Table and returns its entry. It must be present!.
     *
     * @param id the identifer of the variable or function.
     * @return the entry in the symbol table of the variable or function with that
     * identifier.
     */
    public STEntry safeLookup(final String id) {
        for (int i = nestingLevel; i >= 0; i--) {
            var ithScope = symbolTable.get(i);
            var stEntry = ithScope.get(id);
            if (stEntry != null) {
                return stEntry;
            }
        }

        System.err.println("Unexpected absence of ID " + id + " in the Symbol Table.");

        return null; // Does not happen if preconditions are met.
    }

    /**
     * Pops the current active scope from the Symbol Table stack. Decrements the
     * nesting level. Sets the offset to the maximum offset in the previous scope +
     * 1.
     */
    public void popScope() {
        symbolTable.remove(nestingLevel);
        nestingLevel--;
        if (nestingLevel >= 0) {
            var stEntry = symbolTable.get(nestingLevel).values().stream().max(Comparator.comparing(STEntry::getOffset));
            offset = stEntry.map(entry -> entry.getOffset() + 1).orElse(0);
        }
    }

    /**
     * Returning a new environment which has, for each identifier, the maximum
     * effect set in the two environments. Assumes dom(env2) is a subset of
     * dom(env1).
     *
     * @param env1 first environment
     * @param env2 second environment
     * @return the maximum environment of the two
     */
    public static Environment max(final Environment env1, final Environment env2) {
        return operateOnEnvironments(env1, env2, Effect::max);
    }

    /**
     * Returning a new environment which has, for each identifier, the sequence
     * effect set in the two environments. Assumes dom(env2) is a subset of
     * dom(env1).
     *
     * @param env1 first environment
     * @param env2 second environment
     * @return the sequence environment of the two
     */
    public static Environment seq(final Environment env1, final Environment env2) {
        return operateOnEnvironments(env1, env2, Effect::seq);
    }

    /**
     * Returning a new environment which has, for each identifier in {@code env2},
     * the operation applied effect set in the two environments. Assumes dom(env2)
     * is a subset of dom(env1).
     *
     * @param env1 first environment
     * @param env2 second environment
     * @return the operation applied environment of the two
     */
    private static Environment operateOnEnvironments(final Environment env1, final Environment env2,
                                                     final BiFunction<Effect, Effect, Effect> operation) {
        var resultEnv = new Environment(new ArrayList<>(), env1.nestingLevel, env1.offset);
        for (int i = 0, size = env1.symbolTable.size(); i < size; i++) { // for each scope in the Symbol Table
            var ithScope1 = env1.symbolTable.get(i);
            var ithScope2 = env2.symbolTable.get(i);
            final HashMap<String, STEntry> resultEnvScope = new HashMap<>();
            for (var id : ithScope1.keySet()) {
                var entryInScopeEnv1 = ithScope1.get(id);
                var entryInScopeEnv2 = ithScope2.get(id);

                if (entryInScopeEnv2 != null) { // ==> id \in dom(env2)
                    var opEntry = new STEntry(entryInScopeEnv1.getNestingLevel(), entryInScopeEnv1.getType(),
                            entryInScopeEnv1.getOffset());
                    //opEntry.setVariableStatus(operation.apply(entryInScopeEnv1.getVariableStatus(), entryInScopeEnv2.getVariableStatus()));
                    for (int j = 0; j < entryInScopeEnv1.getMaxDereferenceLevel(); j++) {
                        opEntry.setVariableStatus(operation.apply(entryInScopeEnv1.getVariableStatus(j), entryInScopeEnv2.getVariableStatus(j)), j);
                    }

                    resultEnvScope.put(id, opEntry);
                } else {
                    resultEnvScope.put(id, entryInScopeEnv1);
                }
            }
            resultEnv.symbolTable.add(resultEnvScope);
        }
        return resultEnv;
    }

    /**
     * Returns the par environment applied to the head of {@code env1} and
     * {@code env2}.
     *
     * @param env1 environment which has at least one scope
     * @param env2 environment which has at least one scope
     * @return the par environment
     */
    public static Environment par(final Environment env1, final Environment env2) {
        Environment resultingEnvironment = new Environment();
        resultingEnvironment.pushNewScope();

        Map<String, STEntry> scope1 = env1.symbolTable.get(env1.symbolTable.size() - 1);
        Map<String, STEntry> scope2 = env2.symbolTable.get(env2.symbolTable.size() - 1);

        for (var xInE1 : scope1.entrySet()) {
            if (!scope2.containsKey(xInE1.getKey())) {
                STEntry entry = resultingEnvironment.addUniqueNewDeclaration(xInE1.getKey(),
                        xInE1.getValue().getType());
                //entry.setVariableStatus(xInE1.getValue().getVariableStatus());
                for (int j = 0; j < xInE1.getValue().getMaxDereferenceLevel(); j++) {
                    entry.setVariableStatus(xInE1.getValue().getVariableStatus(j), j);
                }
            }
        }

        for (var xInE2 : scope2.entrySet()) {
            if (!scope1.containsKey(xInE2.getKey())) {
                STEntry entry = resultingEnvironment.addUniqueNewDeclaration(xInE2.getKey(),
                        xInE2.getValue().getType());
                //entry.setVariableStatus(xInE2.getValue().getVariableStatus());
                for (int j = 0; j < xInE2.getValue().getMaxDereferenceLevel(); j++) {
                    entry.setVariableStatus(xInE2.getValue().getVariableStatus(j), j);
                }
            }
        }

        for (var xInE1 : scope1.entrySet()) {
            for (var xInE2 : scope2.entrySet()) {
                if (xInE1.getKey().equals(xInE2.getKey())) {
                    STEntry entry = resultingEnvironment.addUniqueNewDeclaration(xInE1.getKey(),
                            xInE1.getValue().getType());
                    //Effect parResult = Effect.par(xInE1.getValue().getVariableStatus(), xInE2.getValue().getVariableStatus());
                    //entry.setVariableStatus(parResult);
                    for (int j = 0; j < xInE2.getValue().getMaxDereferenceLevel(); j++) {
                        entry.setVariableStatus(Effect.par(xInE1.getValue().getVariableStatus(j), xInE2.getValue().getVariableStatus(j)), j);
                    }
                }
            }
        }

        return resultingEnvironment;
    }

    /**
     * Creates a new environment updating {@code env1} with the effects applied in
     * {@code env2}. <strong>Attention</strong>: consider invoking this function
     * with clones of the environments since this function performs side-effects on
     * the arguments.
     *
     * @param env1 environment to update (multi-scope)
     * @param env2 environment with updates (single-scope)
     * @return the updated environment
     */
    public static Environment update(Environment env1, Environment env2) {
        Environment returnedEnvironment;

        if (env2.symbolTable.size() == 0 || env1.symbolTable.size() == 0) {
            return new Environment(env1);
        }

        Map<String, STEntry> headScope1 = env1.symbolTable.get(env1.symbolTable.size() - 1);
        Map<String, STEntry> headScope2 = env2.symbolTable.get(env2.symbolTable.size() - 1);

        if (headScope2.keySet().isEmpty()) {
            // \sigma' = \emptySet
            return new Environment(env1);
        }

        var u = headScope2.entrySet().stream().findFirst().get();
        env2.removeFirstIdentifier(u.getKey());

        if (headScope1.containsKey(u.getKey())) {
            headScope1.put(u.getKey(), u.getValue());

            returnedEnvironment = update(env1, env2);
        } else {
            Environment envWithOnlyU = new Environment();
            envWithOnlyU.pushNewScope();
            STEntry tmpEntry = envWithOnlyU.addUniqueNewDeclaration(u.getKey(), u.getValue().getType());
            //tmpEntry.setVariableStatus(u.getValue().getVariableStatus());
            for (int j = 0; j < u.getValue().getMaxDereferenceLevel(); j++) {
                tmpEntry.setVariableStatus(u.getValue().getVariableStatus(j), j);
            }

            env1.popScope();
            Environment tmpEnv = update(env1, envWithOnlyU);
            tmpEnv.pushNewScope(headScope1);

            returnedEnvironment = update(tmpEnv, env2);
        }

        return returnedEnvironment;
    }

    private void removeFirstIdentifier(String id) {
        for (int i = symbolTable.size() - 1; i >= 0; i--) {
            if (symbolTable.get(i).containsKey(id)) {
                symbolTable.get(i).remove(id);
                return;
            }
        }
    }

    /**
     * Checks the status of the variable and its status and updates it following the
     * given rule, if the new status is Effect.ERROR then returns a SemanticError
     * wrapped in a ArrayList. The ArrayList is returned just for simplicity.
     *
     * @return a list with errors found while checking the status of each variable
     * used in the expression
     */
    public ArrayList<SemanticError> checkVariableStatus(final LhsNode variable,
                                                        final BiFunction<Effect, Effect, Effect> rule, final Effect effectToApply) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        try {
            var stEntry = lookup(variable.getId().getId());
            Effect status = rule.apply(stEntry.getVariableStatus(variable.getDereferenceLevel()), effectToApply);

            stEntry.setVariableStatus(status, variable.getDereferenceLevel());

            if (status.equals(Effect.ERROR)) {
                errors.add(new SemanticError("Variable " + variable.getId() + " is used after deletion"));
            }
        } catch (MissingDeclarationException exception) {
            errors.add(new SemanticError(
                    "Cannot perform effect analysis on " + variable.getId() + " since it is not declared."));
        }

        return errors;
    }

    public ArrayList<SemanticError> getEffectErrors() {
        ArrayList<SemanticError> errors = new ArrayList<>();
        for (var scope : symbolTable) {
            for (var entry : scope.entrySet()) {
                for (int i = 0; i < entry.getValue().getMaxDereferenceLevel(); i++) {
                    if (entry.getValue().getVariableStatus(i).equals(Effect.ERROR)) {
                        errors.add(new SemanticError("Dereference pointer " + entry.getKey() + " at level " + i + " is used after deletion"));
                    }
                }
            }
        }

        return errors;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Environment {\n");

        for (int i = 0; i < symbolTable.size(); i++) {
            buffer.append("\tScope ").append(i).append(" {\n");

            for (var entry : symbolTable.get(i).entrySet()) {
                buffer.append("\t\t").append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
            }

            buffer.append("\t}\n");
        }

        buffer.append("}\n");

        return buffer.toString();
    }

    /**
     * Replaces the whole content of the current instance of {@code Environment}
     * with that passed as argument.
     *
     * @param environment environment to copy
     */
    public void replace(final Environment environment) {
        symbolTable.clear();
        nestingLevel = environment.nestingLevel;
        offset = environment.offset;

        for (var scope : environment.symbolTable) {
            final Map<String, STEntry> copiedScope = new HashMap<>();
            for (var id : scope.keySet()) {
                copiedScope.put(id, new STEntry(scope.get(id)));
            }
            symbolTable.add(copiedScope);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Environment env = (Environment) obj;

        if (nestingLevel != env.nestingLevel) {
            return false;
        }

        if (offset != env.offset) {
            return false;
        }

        if (symbolTable.size() != env.symbolTable.size()) {
            return false;
        }

        for (int i = 0; i < symbolTable.size(); i++) {
            var ithScope = symbolTable.get(i);
            var ithScopeEnv = env.symbolTable.get(i);

            if (!ithScopeEnv.keySet().equals(ithScope.keySet())){
                return false;
            }

            for (var entry: ithScopeEnv.entrySet()) {
                var entryEnv = ithScope.get(entry.getKey());
                if (!entry.getValue().equals(entryEnv)){
                    return false;
                }
            }
        }

        return true;
    }
}
