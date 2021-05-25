package it.azzalinferrati.semanticanalysis;

public class Effect {
    // ⊥
    public static final int INITIALIZED = 0;
    // rw
    public static final int READ_WRITE = 1;
    // d
    public static final int DELETE = 2;
    // ⊤
    public static final int ERROR = 3;

    private int value;

    public Effect(final int value) {
        this.value = value;
    }

    public Effect() {
        this(INITIALIZED);
    }

    public static Effect max(final Effect e1, final Effect e2) {
        return new Effect(e1.value > e2.value ? e1.value : e2.value);
    }

    public static Effect seq(final Effect e1, final Effect e2) {
        return null;
    }

    public static Effect par(final Effect e1, final Effect e2) {
        return null;
    }
}
