package snorax.tasklist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snorax.exception.SnoraxException;
import snorax.task.Task;
import snorax.task.Todo;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {
    
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }

    @Test
    public void testConstructor_emptyList() {
        TaskList emptyList = new TaskList();
        assertEquals(0, emptyList.size());
    }

    @Test
    public void testConstructor_withTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("Task 1"));
        tasks.add(new Todo("Task 2"));
        
        TaskList list = new TaskList(tasks);
        assertEquals(2, list.size());
    }

    @Test
    public void testAdd_singleTask() {
        Task task = new Todo("Read book");
        taskList.add(task);
        
        assertEquals(1, taskList.size());
        assertEquals(task, taskList.get(0));
    }

    @Test
    public void testAdd_multipleTasks() {
        Task task1 = new Todo("Task 1");
        Task task2 = new Todo("Task 2");
        
        taskList.add(task1);
        taskList.add(task2);
        
        assertEquals(2, taskList.size());
    }

    @Test
    public void testDelete_validIndex() throws SnoraxException {
        Task task = new Todo("Delete me");
        taskList.add(task);
        
        Task deleted = taskList.delete(0);
        
        assertEquals(task, deleted);
        assertEquals(0, taskList.size());
    }

    @Test
    public void testDelete_invalidIndex() {
        assertThrows(SnoraxException.class, () -> taskList.delete(0));
        assertThrows(SnoraxException.class, () -> taskList.delete(-1));
    }

    @Test
    public void testGet_validIndex() throws SnoraxException {
        Task task = new Todo("Get me");
        taskList.add(task);
        
        assertEquals(task, taskList.get(0));
    }

    @Test
    public void testGet_invalidIndex() {
        assertThrows(SnoraxException.class, () -> taskList.get(0));
        assertThrows(SnoraxException.class, () -> taskList.get(-1));
    }

    @Test
    public void testSize_emptyList() {
        assertEquals(0, taskList.size());
    }

    @Test
    public void testSize_afterAddingTasks() {
        taskList.add(new Todo("Task 1"));
        taskList.add(new Todo("Task 2"));
        taskList.add(new Todo("Task 3"));
        
        assertEquals(3, taskList.size());
    }

    @Test
    public void testGetTasks_returnsCopy() {
        Task task1 = new Todo("Task 1");
        taskList.add(task1);
        
        ArrayList<Task> tasks = taskList.getTasks();
        assertEquals(1, tasks.size());
        
        // Modify the returned list
        tasks.clear();
        
        // Original should be unchanged
        assertEquals(1, taskList.size());
    }
}