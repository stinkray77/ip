package snorax.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import snorax.exception.SnoraxException;
import snorax.storage.Storage;
import snorax.task.Todo;
import snorax.tasklist.TaskList;
import snorax.ui.Ui;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class MarkUnmarkDeleteCommandTest {

    private TaskList tasks;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    public void setUp(@TempDir Path tempDir) {
        tasks = new TaskList();
        tasks.addTask(new Todo("Task 1"));
        tasks.addTask(new Todo("Task 2"));
        ui = new Ui();
        storage = new Storage(tempDir.resolve("test.txt").toString());
    }

    // ======================== Mark ========================

    @Test
    public void testMark_validIndex_success() throws SnoraxException {
        new MarkCommand(0).execute(tasks, ui, storage);
        assertTrue(tasks.getTask(0).isDone());
    }

    @Test
    public void testMark_outOfBoundsIndex_throwsException() {
        assertThrows(SnoraxException.class, () -> new MarkCommand(99).execute(tasks, ui, storage));
    }

    @Test
    public void testMark_emptyList_throwsException() {
        TaskList empty = new TaskList();
        assertThrows(SnoraxException.class, () -> new MarkCommand(0).execute(empty, ui, storage));
    }

    @Test
    public void testMark_alreadyDone_throwsException() throws SnoraxException {
        new MarkCommand(0).execute(tasks, ui, storage);
        assertThrows(SnoraxException.class, () -> new MarkCommand(0).execute(tasks, ui, storage));
    }

    @Test
    public void testMarkIsExit() {
        assertFalse(new MarkCommand(0).isExit());
    }

    // ======================== Unmark ========================

    @Test
    public void testUnmark_validIndex_success() throws SnoraxException {
        tasks.getTask(0).markAsDone();
        new UnmarkCommand(0).execute(tasks, ui, storage);
        assertFalse(tasks.getTask(0).isDone());
    }

    @Test
    public void testUnmark_outOfBoundsIndex_throwsException() {
        assertThrows(SnoraxException.class, () -> new UnmarkCommand(99).execute(tasks, ui, storage));
    }

    @Test
    public void testUnmark_emptyList_throwsException() {
        TaskList empty = new TaskList();
        assertThrows(SnoraxException.class, () -> new UnmarkCommand(0).execute(empty, ui, storage));
    }

    @Test
    public void testUnmark_alreadyNotDone_throwsException() {
        assertThrows(SnoraxException.class, () -> new UnmarkCommand(0).execute(tasks, ui, storage));
    }

    @Test
    public void testUnmarkIsExit() {
        assertFalse(new UnmarkCommand(0).isExit());
    }

    // ======================== Delete ========================

    @Test
    public void testDelete_validIndex_removesTask() throws SnoraxException {
        new DeleteCommand(0).execute(tasks, ui, storage);
        assertEquals(1, tasks.size());
    }

    @Test
    public void testDelete_outOfBoundsIndex_throwsException() {
        assertThrows(SnoraxException.class, () -> new DeleteCommand(99).execute(tasks, ui, storage));
    }

    @Test
    public void testDelete_emptyList_throwsException() {
        TaskList empty = new TaskList();
        assertThrows(SnoraxException.class, () -> new DeleteCommand(0).execute(empty, ui, storage));
    }

    @Test
    public void testDelete_correctTaskRemoved() throws SnoraxException {
        String result = new DeleteCommand(0).execute(tasks, ui, storage);
        assertTrue(result.contains("Task 1"));
        assertEquals("Task 2", tasks.getTask(0).getDescription());
    }

    @Test
    public void testDeleteIsExit() {
        assertFalse(new DeleteCommand(0).isExit());
    }
}
