package snorax.tasklist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    public void testAddTask_singleTask() {
        Task task = new Todo("Read book");
        taskList.addTask(task);
        
        assertEquals(1, taskList.size());
        assertEquals(task, taskList.getTask(0));
    }

    @Test
    public void testAddTask_multipleTasks() {
        Task task1 = new Todo("Task 1");
        Task task2 = new Todo("Task 2");
        
        taskList.addTask(task1);
        taskList.addTask(task2);
        
        assertEquals(2, taskList.size());
    }

    @Test
    public void testDeleteTask_validIndex() {
        Task task = new Todo("Delete me");
        taskList.addTask(task);
        
        Task deleted = taskList.deleteTask(0);
        
        assertEquals(task, deleted);
        assertEquals(0, taskList.size());
    }

    @Test
    public void testDeleteTask_invalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.deleteTask(0));
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.deleteTask(-1));
    }

    @Test
    public void testGetTask_validIndex() {
        Task task = new Todo("Get me");
        taskList.addTask(task);
        
        assertEquals(task, taskList.getTask(0));
    }

    @Test
    public void testGetTask_invalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.getTask(0));
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.getTask(-1));
    }

    @Test
    public void testSize_emptyList() {
        assertEquals(0, taskList.size());
    }

    @Test
    public void testSize_afterAddingTasks() {
        taskList.addTask(new Todo("Task 1"));
        taskList.addTask(new Todo("Task 2"));
        taskList.addTask(new Todo("Task 3"));
        
        assertEquals(3, taskList.size());
    }

    @Test
    public void testGetTasks_returnsActualList() {
        Task task1 = new Todo("Task 1");
        taskList.addTask(task1);
        
        ArrayList<Task> tasks = taskList.getTasks();
        assertEquals(1, tasks.size());
        assertEquals(task1, tasks.get(0));
    }
}