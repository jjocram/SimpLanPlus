package it.azzalinferrati;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

@RunWith(Parameterized.class)
public class AppTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        String[] examplesFile = (new File("examples/")).list();

        Collection<Object[]> params = new ArrayList<>(100);
        assert examplesFile != null;
        for(String fileName : examplesFile) {
            params.add(new Object[] { fileName });
        }
        return params;
    }

    String exampleFile;

    public AppTest(String exampleFile) {
        this.exampleFile = exampleFile;
    }

    @Test
    public void testExample() throws Exception {
        int systemExit;
        try {
            systemExit = catchSystemExit(() -> App.main(new String[]{"examples/" + exampleFile}));
        } catch (AssertionError error) {
            systemExit = 0;
        }

        assertEquals(0, systemExit);
    }
}
