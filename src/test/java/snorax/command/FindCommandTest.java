package snorax.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import snorax.storage.Storage;
import snorax.task.Todo;
import snorax.tasklist.TaskList;
import snorax.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FindCommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp(@TempDir Path tempDir) {
        tasks = new TaskList();
        tasks.addTask(new Todo("read book"));
        tasks.addTask(new Todo("return book"));
        tasks.addTask(new Todo("buy groceries"));

        ui = new Ui();
        storage = new Storage(tempDir.resolve("test.txt").toString());
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testExecute_findMatchingTasks() {
        FindCommand command = new FindCommand("book");
        assertDoesNotThrow(() -> command.execute(tasks, ui, storage));

        String output = outContent.toString();
        assertTrue(output.contains("read book"));
        assertTrue(output.contains("return book"));
        assertFalse(output.contains("buy groceries"));
    }

    @Test
    public void testExecute_noMatchingTasks() {
        String result = new FindCommand("homework").execute(tasks, ui, storage);
        assertTrue(result.contains("No matching tasks found bro"));
    }

    @Test
    public void testExecute_caseInsensitiveSearch() {
        FindCommand command = new FindCommand("BOOK");
        assertDoesNotThrow(() -> command.execute(tasks, ui, storage));

        String output = outContent.toString();
        assertTrue(output.contains("read book"));
        assertTrue(output.contains("return book"));
    }

    @Test
    public void testIsExit() {
        FindCommand command = new FindCommand("test");
        assertFalse(command.isExit());
    }

    @org.junit.jupiter.api.AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}
