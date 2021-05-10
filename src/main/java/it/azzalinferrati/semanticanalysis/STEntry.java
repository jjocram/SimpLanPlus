package it.azzalinferrati.semanticanalysis;

import it.azzalinferrati.ast.node.type.TypeNode;

public class STEntry {
    private int nl;
    private TypeNode type;
    private int offset;

    public STEntry(int n, int os) {
        nl = n;
        offset = os;
    }

    public STEntry(int n, TypeNode t, int os) {
        nl = n;
        type = t;
        offset = os;
    }

    public void addType(TypeNode t) {
        type = t;
    }

    public TypeNode getType() {
        return type;
    }

    public int getOffset() {
        return offset;
    }

    public int getNestinglevel() {
        return nl;
    }

    public String toPrint(String s) { //
        return s + "STentry: nestlev " + Integer.toString(nl) + "\n" +
                s + "STentry: type\n" +
                type.toPrint(s + "  ") +
                s + "STentry: offset " + Integer.toString(offset) + "\n";
    }
}
