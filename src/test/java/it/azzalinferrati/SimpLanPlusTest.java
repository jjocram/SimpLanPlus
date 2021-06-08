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

        Collection<Object[]> params = new ArrayList<>(9);
        assert examplesFile != null;
        for (String fileName : examplesFile) {
            params.add(new Object[]{fileName, filesWithErrors.contains(fileName) ? 1 : 0});
        }
        return params;
    }

    String exampleFile;
    int expectedStatusCode;

    public SimpLanPlusTest(String exampleFile, int statusCode) {
        this.exampleFile = exampleFile;
        this.expectedStatusCode = statusCode;
    }

    @Test
    public void testExample() throws Exception {
        int systemExit;
        try {
            systemExit = catchSystemExit(() -> SimpLanPlus.main(new String[]{"examples/" + exampleFile, "-memsize=20"}));
        } catch (AssertionError error) {
            systemExit = 0;
        }

        assertEquals(expectedStatusCode, systemExit);
    }
}
