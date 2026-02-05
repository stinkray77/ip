package snorax;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class SnoraxTest {
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final ByteArrayInputStream originalIn = System.in;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void testConstructor_withValidFilePath(@TempDir Path tempDir) {
        String filePath = tempDir.resolve("snorax.txt").toString();
        assertDoesNotThrow(() -> new Snorax(filePath));
    }

    @Test
    public void testConstructor_withInvalidFilePath() {
        String invalidPath = "/invalid/path/that/does/not/exist/snorax.txt";
        assertDoesNotThrow(() -> new Snorax(invalidPath));
    }

    @Test
    public void testRun_exitCommand(@TempDir Path tempDir) {
        String input = "bye\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inContent);

        String filePath = tempDir.resolve("snorax.txt").toString();
        Snorax snorax = new Snorax(filePath);
        
        assertDoesNotThrow(() -> snorax.run());
        
        System.setIn(originalIn);
    }
}