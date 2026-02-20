package snorax.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import snorax.exception.SnoraxException;
import snorax.storage.Storage;
import snorax.task.Deadline;
import snorax.task.Event;
import snorax.task.Todo;
import snorax.tasklist.TaskList;
import snorax.ui.Ui;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class AddCommandTest {

    private TaskList tasks;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    public void setUp(@TempDir Path tempDir) {
        tasks = new TaskList();
        ui = new Ui();
        storage = new Storage(tempDir.resolve("test.txt").toString());
    }

    // ======================== Todo ========================

    @Test
    public void testExecute_addTodo_success() throws SnoraxException {
        String result = new AddCommand(new Todo("Read book"))
                .execute(tasks, ui, storage);
        assertEquals(1, tasks.size());
        assertTrue(result.contains("Read book"));
    }

    @Test
    public void testExecute_addDuplicateTodo_throwsException() throws SnoraxException {
        new AddCommand(new Todo("Read book")).execute(tasks, ui, storage);
        assertThrows(SnoraxException.class, () -> new AddCommand(new Todo("Read book")).execute(tasks, ui, storage));
    }

    // ======================== Deadline ========================

    @Test
    public void testExecute_addDeadline_success() throws SnoraxException {
        String result = new AddCommand(new Deadline("Submit report", "2024-12-31 2359"))
                .execute(tasks, ui, storage);
        assertEquals(1, tasks.size());
        assertTrue(result.contains("Submit report"));
    }

    @Test
    public void testExecute_addDeadlineInvalidDate_throwsException() {
        assertThrows(SnoraxException.class, () -> new AddCommand(new Deadline("Task", "2024-02-30 1200"))
                .execute(tasks, ui, storage));
    }

    @Test
    public void testExecute_addDuplicateDeadline_throwsException() throws SnoraxException {
        new AddCommand(new Deadline("Submit", "2024-12-31 2359")).execute(tasks, ui, storage);
        assertThrows(SnoraxException.class, () -> new AddCommand(new Deadline("Submit", "2024-12-31 2359"))
                .execute(tasks, ui, storage));
    }

    // ======================== Event ========================

    @Test
    public void testExecute_addEvent_success() throws SnoraxException {
        String result = new AddCommand(
                new Event("Conference", "2024-12-25 0900", "2024-12-25 1700"))
                .execute(tasks, ui, storage);
        assertEquals(1, tasks.size());
        assertTrue(result.contains("Conference"));
    }

    @Test
    public void testExecute_addEventStartAfterEnd_throwsException() {
        assertThrows(SnoraxException.class,
                () -> new AddCommand(new Event("Meeting", "2024-12-25 1700", "2024-12-25 0900"))
                        .execute(tasks, ui, storage));
    }

    @Test
    public void testExecute_addEventStartEqualsEnd_throwsException() {
        assertThrows(SnoraxException.class,
                () -> new AddCommand(new Event("Meeting", "2024-12-25 1400", "2024-12-25 1400"))
                        .execute(tasks, ui, storage));
    }

    @Test
    public void testExecute_addDuplicateEvent_throwsException() throws SnoraxException {
        new AddCommand(new Event("Conference", "2024-12-25 0900", "2024-12-25 1700"))
                .execute(tasks, ui, storage);
        assertThrows(SnoraxException.class,
                () -> new AddCommand(new Event("Conference", "2024-12-25 0900", "2024-12-25 1700"))
                        .execute(tasks, ui, storage));
    }

    // ======================== Task Count ========================

    @Test
    public void testExecute_taskCountIncrements() throws SnoraxException {
        new AddCommand(new Todo("Task 1")).execute(tasks, ui, storage);
        new AddCommand(new Todo("Task 2")).execute(tasks, ui, storage);
        assertEquals(2, tasks.size());
    }

    @Test
    public void testExecute_responseContainsTaskCount() throws SnoraxException {
        String result = new AddCommand(new Todo("Task 1")).execute(tasks, ui, storage);
        assertTrue(result.contains("1"));
    }

    @Test
    public void testIsExit() {
        assertFalse(new AddCommand(new Todo("Task")).isExit());
    }
}
