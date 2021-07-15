package it.azzalinferrati;

/**
 * Label Manager for Assembly generated code. Stores a global counter for labels
 * (in order for them to be always unique).
 */
public class LabelManager {
    // Singleton
    private static final LabelManager INSTANCE = new LabelManager();

    private static int labelCount = 0;

    /**
     * @return the singleton instance of {@code LabelManager}
     */
    public static LabelManager getInstance() {
        return INSTANCE;
    }

    /**
     * Generates a new label, unique.
     *
     * @param key to prepend the unique code
     * @return a fresh label
     */
    public String freshLabel(String key) {
        labelCount += 1;
        return key + labelCount;
    }
}
