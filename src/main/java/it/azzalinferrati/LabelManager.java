package it.azzalinferrati;

public class LabelManager {
    private static LabelManager INSTANCE = new LabelManager();

    private static int labelCount = 0;

    public static LabelManager getInstance() {
        return INSTANCE;
    }

    public String freshLabel(String key) {
        labelCount += 1;
        return key + labelCount;
    }
}
