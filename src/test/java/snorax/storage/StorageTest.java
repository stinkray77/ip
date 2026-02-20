package snorax.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import snorax.exception.SnoraxException;
import snorax.task.Deadline;
import snorax.task.Event;
import snorax.task.Task;
import snorax.task.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {

    // ======================== Load ========================

    @Test
    public void testLoad_nonExistentFile_returnsEmptyList(@TempDir Path tempDir)
            throws SnoraxException {
        Storage storage = new Storage(tempDir.resolve("nonexistent.txt").toString());
        ArrayList<Task> tasks = storage.load();
        assertNotNull(tasks);
        assertEquals(0, tasks.size());
    }

    @Test
    public void testLoad_nonExistentFile_createsFile(@TempDir Path tempDir)
            throws SnoraxException {
        Path filePath = tempDir.resolve("nonexistent.txt");
        new Storage(filePath.toString()).load();
        assertTrue(filePath.toFile().exists());
    }

    @Test
    public void testLoad_emptyFile_returnsEmptyList(@TempDir Path tempDir)
            throws SnoraxException, IOException {
        Path filePath = tempDir.resolve("empty.txt");
        filePath.toFile().createNewFile();
        ArrayList<Task> tasks = new Storage(filePath.toString()).load();
        assertEquals(0, tasks.size());
    }

    @Test
    public void testLoad_corruptedLine_skipsLine(@TempDir Path tempDir)
            throws SnoraxException, IOException {
        Path filePath = tempDir.resolve("corrupted.txt");
        try (FileWriter fw = new FileWriter(filePath.toFile())) {
            fw.write("INVALID LINE WITH NO DELIMITERS\n");
            fw.write("T | 0 | Valid todo\n");
        }
        ArrayList<Task> tasks = new Storage(filePath.toString()).load();
        // Only the valid line should be loaded
        assertEquals(1, tasks.size());
        assertEquals("Valid todo", tasks.get(0).getDescription());
    }

    @Test
    public void testLoad_unknownTaskType_skipsLine(@TempDir Path tempDir)
            throws SnoraxException, IOException {
        Path filePath = tempDir.resolve("unknown.txt");
        try (FileWriter fw = new FileWriter(filePath.toFile())) {
            fw.write("X | 0 | Unknown task type\n");
            fw.write("T | 0 | Known task\n");
        }
        ArrayList<Task> tasks = new Storage(filePath.toString()).load();
        assertEquals(1, tasks.size());
        assertEquals("Known task", tasks.get(0).getDescription());
    }

    @Test
    public void testLoad_deadlineMissingTime_skipsLine(@TempDir Path tempDir)
            throws SnoraxException, IOException {
        Path filePath = tempDir.resolve("bad_deadline.txt");
        try (FileWriter fw = new FileWriter(filePath.toFile())) {
            fw.write("D | 0 | No time here\n"); // missing /by field
            fw.write("T | 0 | Valid todo\n");
        }
        ArrayList<Task> tasks = new Storage(filePath.toString()).load();
        assertEquals(1, tasks.size());
    }

    @Test
    public void testLoad_eventMissingToTime_skipsLine(@TempDir Path tempDir)
            throws SnoraxException, IOException {
        Path filePath = tempDir.resolve("bad_event.txt");
        try (FileWriter fw = new FileWriter(filePath.toFile())) {
            fw.write("E | 0 | Meeting | 2024-12-25 0900\n"); // missing /to field
            fw.write("T | 0 | Valid todo\n");
        }
        ArrayList<Task> tasks = new Storage(filePath.toString()).load();
        assertEquals(1, tasks.size());
    }

    @Test
    public void testLoad_emptyDescription_skipsLine(@TempDir Path tempDir)
            throws SnoraxException, IOException {
        Path filePath = tempDir.resolve("empty_desc.txt");
        try (FileWriter fw = new FileWriter(filePath.toFile())) {
            fw.write("T | 0 |  \n"); // empty description
            fw.write("T | 0 | Valid todo\n");
        }
        ArrayList<Task> tasks = new Storage(filePath.toString()).load();
        assertEquals(1, tasks.size());
    }

    @Test
    public void testLoad_mixedValidAndInvalid_loadsOnlyValid(@TempDir Path tempDir)
            throws SnoraxException, IOException {
        Path filePath = tempDir.resolve("mixed.txt");
        try (FileWriter fw = new FileWriter(filePath.toFile())) {
            fw.write("T | 0 | Valid 1\n");
            fw.write("GARBAGE\n");
            fw.write("T | 0 | Valid 2\n");
            fw.write("X | 9 | Bad type and status\n");
            fw.write("T | 0 | Valid 3\n");
        }
        ArrayList<Task> tasks = new Storage(filePath.toString()).load();
        assertEquals(3, tasks.size());
    }

    @Test
    public void testLoad_blankLines_skipped(@TempDir Path tempDir)
            throws SnoraxException, IOException {
        Path filePath = tempDir.resolve("blanks.txt");
        try (FileWriter fw = new FileWriter(filePath.toFile())) {
            fw.write("T | 0 | Valid todo\n");
            fw.write("\n");
            fw.write("   \n");
        }
        ArrayList<Task> tasks = new Storage(filePath.toString()).load();
        assertEquals(1, tasks.size());
    }

    // ======================== Save ========================

    @Test
    public void testSave_createsFile(@TempDir Path tempDir) throws SnoraxException {
        Path filePath = tempDir.resolve("test.txt");
        Storage storage = new Storage(filePath.toString());
        storage.save(new ArrayList<>());
        assertTrue(filePath.toFile().exists());
    }

    @Test
    public void testSave_emptyList_writesEmptyFile(@TempDir Path tempDir)
            throws SnoraxException, IOException {
        Path filePath = tempDir.resolve("empty.txt");
        new Storage(filePath.toString()).save(new ArrayList<>());
        assertEquals(0, filePath.toFile().length());
    }

    // ======================== Save and Load Round-trips ========================

    @Test
    public void testSaveAndLoad_todo(@TempDir Path tempDir) throws SnoraxException {
        Storage storage = new Storage(tempDir.resolve("todo.txt").toString());
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("Read book"));
        storage.save(tasks);

        ArrayList<Task> loaded = storage.load();
        assertEquals(1, loaded.size());
        assertEquals("Read book", loaded.get(0).getDescription());
        assertInstanceOf(Todo.class, loaded.get(0));
    }

    @Test
    public void testSaveAndLoad_deadline(@TempDir Path tempDir) throws SnoraxException {
        Storage storage = new Storage(tempDir.resolve("deadline.txt").toString());
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Deadline("Submit report", "2024-12-31 2359"));
        storage.save(tasks);

        ArrayList<Task> loaded = storage.load();
        assertEquals(1, loaded.size());
        assertInstanceOf(Deadline.class, loaded.get(0));
        assertEquals("Submit report", loaded.get(0).getDescription());
    }

    @Test
    public void testSaveAndLoad_event(@TempDir Path tempDir) throws SnoraxException {
        Storage storage = new Storage(tempDir.resolve("event.txt").toString());
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Event("Conference", "2024-12-25 0900", "2024-12-26 1700"));
        storage.save(tasks);

        ArrayList<Task> loaded = storage.load();
        assertEquals(1, loaded.size());
        assertInstanceOf(Event.class, loaded.get(0));
        assertEquals("Conference", loaded.get(0).getDescription());
    }

    @Test
    public void testSaveAndLoad_markedTask(@TempDir Path tempDir) throws SnoraxException {
        Storage storage = new Storage(tempDir.resolve("marked.txt").toString());
        ArrayList<Task> tasks = new ArrayList<>();
        Todo todo = new Todo("Done task");
        todo.markAsDone();
        tasks.add(todo);
        storage.save(tasks);

        ArrayList<Task> loaded = storage.load();
        assertTrue(loaded.get(0).isDone());
    }

    @Test
    public void testSaveAndLoad_unmarkedTask(@TempDir Path tempDir) throws SnoraxException {
        Storage storage = new Storage(tempDir.resolve("unmarked.txt").toString());
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("Not done task"));
        storage.save(tasks);

        ArrayList<Task> loaded = storage.load();
        assertFalse(loaded.get(0).isDone());
    }

    @Test
    public void testSaveAndLoad_multipleTasks(@TempDir Path tempDir) throws SnoraxException {
        Storage storage = new Storage(tempDir.resolve("multiple.txt").toString());
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("Task 1"));
        tasks.add(new Deadline("Task 2", "2024-12-31 2359"));
        tasks.add(new Event("Task 3", "2024-12-25 0900", "2024-12-26 1700"));
        storage.save(tasks);

        ArrayList<Task> loaded = storage.load();
        assertEquals(3, loaded.size());
        assertInstanceOf(Todo.class, loaded.get(0));
        assertInstanceOf(Deadline.class, loaded.get(1));
        assertInstanceOf(Event.class, loaded.get(2));
    }

    @Test
    public void testSaveAndLoad_preservesEventTimes(@TempDir Path tempDir)
            throws SnoraxException {
        Storage storage = new Storage(tempDir.resolve("event_times.txt").toString());
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Event("Meeting", "2024-12-25 0900", "2024-12-25 1700"));
        storage.save(tasks);

        ArrayList<Task> loaded = storage.load();
        Event loadedEvent = (Event) loaded.get(0);
        assertEquals("2024-12-25 0900", loadedEvent.getFrom());
        assertEquals("2024-12-25 1700", loadedEvent.getTo());
    }

    @Test
    public void testSaveAndLoad_preservesDeadlineTime(@TempDir Path tempDir)
            throws SnoraxException {
        Storage storage = new Storage(tempDir.resolve("deadline_time.txt").toString());
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Deadline("Submit", "2024-12-31 2359"));
        storage.save(tasks);

        ArrayList<Task> loaded = storage.load();
        Deadline loadedDeadline = (Deadline) loaded.get(0);
        assertEquals("2024-12-31 2359", loadedDeadline.getBy());
    }

    @Test
    public void testSave_createsParentDirectories(@TempDir Path tempDir)
            throws SnoraxException {
        Path nestedPath = tempDir.resolve("a/b/c/tasks.txt");
        Storage storage = new Storage(nestedPath.toString());
        storage.save(new ArrayList<>());
        assertTrue(nestedPath.toFile().exists());
    }
}
