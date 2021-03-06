package it.azzalinferrati;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class SimpLanPlusTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        String[] examplesFile = (new File("examples/")).list();

        List<String> filesWithErrors = new ArrayList<>();
        filesWithErrors.add("example2.simplan");
        filesWithErrors.add("example3.simplan");
        filesWithErrors.add("example5.simplan");
        filesWithErrors.add("example12.simplan");
        filesWithErrors.add("example13.simplan");
        filesWithErrors.add("example14.simplan");
        filesWithErrors.add("example15.simplan");
        filesWithErrors.add("example16.simplan");
        filesWithErrors.add("example17.simplan");
        filesWithErrors.add("example18.simplan");
        filesWithErrors.add("example19.simplan");
        filesWithErrors.add("example24.simplan");
        filesWithErrors.add("example27.simplan");
        filesWithErrors.add("example28.simplan");
        filesWithErrors.add("example31.simplan");
        filesWithErrors.add("example32.simplan");

        Collection<Object[]> params = new ArrayList<>(9);
        assert examplesFile != null;
        for (String fileName : examplesFile) {
            params.add(new Object[]{fileName, filesWithErrors.contains(fileName) ? 1 : 0});
        }
        return params;
    }

    final String exampleFile;
    final int expectedStatusCode;

    public SimpLanPlusTest(String exampleFile, int statusCode) {
        this.exampleFile = exampleFile;
        this.expectedStatusCode = statusCode;
    }

    @Test
    public void testExample() throws Exception {
        int systemExit;
        try {
            systemExit = catchSystemExit(() -> SimpLanPlus.main(new String[]{"examples/" + exampleFile}));
        } catch (AssertionError error) {
            systemExit = 0;
        }

        assertEquals(exampleFile, expectedStatusCode, systemExit);
    }
}
