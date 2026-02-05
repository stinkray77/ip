package snorax.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import snorax.exception.SnoraxException;
import snorax.task.Task;
import snorax.task.Todo;
import snorax.task.Deadline;
import snorax.task.Event;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {

    @Test
    public void testLoad_nonExistentFile(@TempDir Path tempDir) throws SnoraxException {
        String filePath = tempDir.resolve("nonexistent.txt").toString();
        Storage storage = new Storage(filePath);
        
        ArrayList<Task> tasks = storage.load();
        assertNotNull(tasks);
        assertEquals(0, tasks.size());
    }

    @Test
    public void testSave_createFile(@TempDir Path tempDir) throws SnoraxException, IOException {
        String filePath = tempDir.resolve("test.txt").toString();
        Storage storage = new Storage(filePath);
        
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("Test task"));
        
        storage.save(tasks);
        
        File file = new File(filePath);
        assertTrue(file.exists());
    }

    @Test
    public void testSaveAndLoad_todo(@TempDir Path tempDir) throws SnoraxException, IOException {
        String filePath = tempDir.resolve("todo.txt").toString();
        Storage storage = new Storage(filePath);
        
        ArrayList<Task> tasks = new ArrayList<>();
        Todo todo = new Todo("Read book");
        tasks.add(todo);
        
        storage.save(tasks);
        ArrayList<Task> loadedTasks = storage.load();
        
        assertEquals(1, loadedTasks.size());
        assertEquals("Read book", loadedTasks.get(0).getDescription());
    }

    @Test
    public void testSaveAndLoad_deadline(@TempDir Path tempDir) throws SnoraxException, IOException {
        String filePath = tempDir.resolve("deadline.txt").toString();
        Storage storage = new Storage(filePath);
        
        ArrayList<Task> tasks = new ArrayList<>();
        Deadline deadline = new Deadline("Submit report", "2024-12-31");
        tasks.add(deadline);
        
        storage.save(tasks);
        ArrayList<Task> loadedTasks = storage.load();
        
        assertEquals(1, loadedTasks.size());
        assertEquals("Submit report", loadedTasks.get(0).getDescription());
    }

    @Test
    public void testSaveAndLoad_event(@TempDir Path tempDir) throws SnoraxException, IOException {
        String filePath = tempDir.resolve("event.txt").toString();
        Storage storage = new Storage(filePath);
        
        ArrayList<Task> tasks = new ArrayList<>();
        Event event = new Event("Conference", "2024-12-25", "2024-12-26");
        tasks.add(event);
        
        storage.save(tasks);
        ArrayList<Task> loadedTasks = storage.load();
        
        assertEquals(1, loadedTasks.size());
        assertEquals("Conference", loadedTasks.get(0).getDescription());
    }

    @Test
    public void testSaveAndLoad_multipleTasks(@TempDir Path tempDir) throws SnoraxException, IOException {
        String filePath = tempDir.resolve("multiple.txt").toString();
        Storage storage = new Storage(filePath);
        
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("Task 1"));
        tasks.add(new Deadline("Task 2", "2024-12-31"));
        tasks.add(new Event("Task 3", "2024-12-25", "2024-12-26"));
        
        storage.save(tasks);
        ArrayList<Task> loadedTasks = storage.load();
        
        assertEquals(3, loadedTasks.size());
    }

    @Test
    public void testSaveAndLoad_markedTasks(@TempDir Path tempDir) throws SnoraxException, IOException {
        String filePath = tempDir.resolve("marked.txt").toString();
        Storage storage = new Storage(filePath);
        
        ArrayList<Task> tasks = new ArrayList<>();
        Todo todo = new Todo("Completed task");
        todo.mark();
        tasks.add(todo);
        
        storage.save(tasks);
        ArrayList<Task> loadedTasks = storage.load();
        
        assertEquals(1, loadedTasks.size());
        assertTrue(loadedTasks.get(0).isDone());
    }
}