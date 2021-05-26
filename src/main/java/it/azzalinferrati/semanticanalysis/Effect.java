package it.azzalinferrati.semanticanalysis;

public class Effect {
    // ⊥
    private static final int _INITIALIZED = 0;
    public static final Effect INITIALIZED = new Effect(_INITIALIZED);
    // rw
    private static final int _READ_WRITE = 1;
    public static final Effect READ_WRITE = new Effect(_READ_WRITE);
    // d
    private static final int _DELETE = 2;
    public static final Effect DELETE = new Effect(_DELETE);
    // ⊤
    private static final int _ERROR = 3;
    public static final Effect ERROR = new Effect(_ERROR);

    private final int value;

    public Effect(final int value) {
        this.value = value;
    }

    public Effect() {
        this(_INITIALIZED);
    }

    public static Effect max(final Effect e1, final Effect e2) {
        return new Effect(Math.max(e1.value, e2.value));
    }

    public static Effect seq(final Effect e1, final Effect e2) {
        if (max(e1, e2).value <= _READ_WRITE) {
            return Effect.max(e1, e2);
        }

        if ((e1.value <= _READ_WRITE && e2.value == _DELETE) || (e1.value == _DELETE && e2.value == _INITIALIZED)) {
            return new Effect(_DELETE);
        }

        return new Effect(_ERROR);
    }

    public static Effect par(final Effect e1, final Effect e2) {
        return max(seq(e1, e2), seq(e2, e1));
    }
}
