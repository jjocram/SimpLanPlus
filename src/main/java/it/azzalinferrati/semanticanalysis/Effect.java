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

    /**
     * Constructor of Effect.
     * @param value can be _INITIALIZED, _READ_WRITE, _DELETE, _ERROR.
     */
    private Effect(final int value) {
        this.value = value;
    }

    /**
     * Construct of Effect. Defaults to Effect.INITIALIZED.
     */
    public Effect() {
        this(_INITIALIZED);
    }

    /**
     * Copy constructor of Effect.
     * @param e effect
     */
    public Effect(final Effect e) {
        this(e.value);
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

    @Override
    public String toString() {
        switch(value) {
            case _INITIALIZED:
                return "INITIALIZED";
            case _READ_WRITE:
                return "READ_WRITE";
            case _DELETE:
                return "DELETE";
            case _ERROR:
                return "ERROR";
            default:
                return "";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Effect e = (Effect) obj;
        return value == e.value;
    }
}
