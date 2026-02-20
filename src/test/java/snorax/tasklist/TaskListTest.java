package snorax.tasklist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snorax.task.Deadline;
import snorax.task.Event;
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

    // ======================== Constructor ========================

    @Test
    public void testConstructor_emptyList() {
        assertEquals(0, new TaskList().size());
    }

    @Test
    public void testConstructor_withTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("Task 1"));
        tasks.add(new Todo("Task 2"));
        assertEquals(2, new TaskList(tasks).size());
    }

    // ======================== Add ========================

    @Test
    public void testAddTask_singleTask() {
        Task task = new Todo("Read book");
        taskList.addTask(task);
        assertEquals(1, taskList.size());
        assertEquals(task, taskList.getTask(0));
    }

    @Test
    public void testAddTask_multipleTasks() {
        taskList.addTask(new Todo("Task 1"));
        taskList.addTask(new Todo("Task 2"));
        assertEquals(2, taskList.size());
    }

    @Test
    public void testAddTask_preservesOrder() {
        Task t1 = new Todo("First");
        Task t2 = new Todo("Second");
        taskList.addTask(t1);
        taskList.addTask(t2);
        assertEquals(t1, taskList.getTask(0));
        assertEquals(t2, taskList.getTask(1));
    }

    // ======================== Delete ========================

    @Test
    public void testDeleteTask_validIndex() {
        Task task = new Todo("Delete me");
        taskList.addTask(task);
        Task deleted = taskList.deleteTask(0);
        assertEquals(task, deleted);
        assertEquals(0, taskList.size());
    }

    @Test
    public void testDeleteTask_invalidIndex_throwsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.deleteTask(0));
    }

    @Test
    public void testDeleteTask_negativeIndex_throwsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.deleteTask(-1));
    }

    @Test
    public void testDeleteTask_shiftsRemainingTasks() {
        Task t1 = new Todo("First");
        Task t2 = new Todo("Second");
        Task t3 = new Todo("Third");
        taskList.addTask(t1);
        taskList.addTask(t2);
        taskList.addTask(t3);

        taskList.deleteTask(1); // delete "Second"

        assertEquals(2, taskList.size());
        assertEquals(t1, taskList.getTask(0));
        assertEquals(t3, taskList.getTask(1));
    }

    // ======================== Get ========================

    @Test
    public void testGetTask_validIndex() {
        Task task = new Todo("Get me");
        taskList.addTask(task);
        assertEquals(task, taskList.getTask(0));
    }

    @Test
    public void testGetTask_invalidIndex_throwsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.getTask(0));
    }

    @Test
    public void testGetTask_negativeIndex_throwsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.getTask(-1));
    }

    // ======================== Size / isEmpty ========================

    @Test
    public void testSize_emptyList() {
        assertEquals(0, taskList.size());
    }

    @Test
    public void testSize_afterAddingTasks() {
        taskList.addTask(new Todo("Task 1"));
        taskList.addTask(new Todo("Task 2"));
        assertEquals(2, taskList.size());
    }

    @Test
    public void testSize_afterDeletion() {
        taskList.addTask(new Todo("Task"));
        taskList.deleteTask(0);
        assertEquals(0, taskList.size());
    }

    @Test
    public void testIsEmpty_emptyList() {
        assertTrue(taskList.isEmpty());
    }

    @Test
    public void testIsEmpty_nonEmptyList() {
        taskList.addTask(new Todo("Task"));
        assertFalse(taskList.isEmpty());
    }

    // ======================== getTasks ========================

    @Test
    public void testGetTasks_returnsCorrectList() {
        Task task = new Todo("Task 1");
        taskList.addTask(task);
        ArrayList<Task> tasks = taskList.getTasks();
        assertEquals(1, tasks.size());
        assertEquals(task, tasks.get(0));
    }

    @Test
    public void testGetTasks_emptyList_returnsEmptyArrayList() {
        assertEquals(0, taskList.getTasks().size());
    }

    // ======================== Sort ========================

    @Test
    public void testSortDeadlines_sortsChronologically() {
        taskList.addTask(new Deadline("Later", "2024-12-31 2359"));
        taskList.addTask(new Deadline("Earlier", "2024-01-01 0900"));

        taskList.sortDeadlines();

        assertEquals("Earlier", taskList.getTask(0).getDescription());
        assertEquals("Later", taskList.getTask(1).getDescription());
    }

    @Test
    public void testSortEvents_sortsChronologically() {
        taskList.addTask(new Event("Later", "2024-12-25 0900", "2024-12-25 1700"));
        taskList.addTask(new Event("Earlier", "2024-01-01 0900", "2024-01-01 1700"));

        taskList.sortEvents();

        assertEquals("Earlier", taskList.getTask(0).getDescription());
        assertEquals("Later", taskList.getTask(1).getDescription());
    }

    @Test
    public void testSortTasks_groupsByType() {
        taskList.addTask(new Todo("A todo"));
        taskList.addTask(new Event("An event", "2024-12-25 0900", "2024-12-25 1700"));
        taskList.addTask(new Deadline("A deadline", "2024-12-31 2359"));

        taskList.sortTasks();

        // Deadlines first, then Events, then Todos
        assertInstanceOf(Deadline.class, taskList.getTask(0));
        assertInstanceOf(Event.class, taskList.getTask(1));
        assertInstanceOf(Todo.class, taskList.getTask(2));
    }

    @Test
    public void testSort_emptyList_noException() {
        assertDoesNotThrow(() -> taskList.sortTasks());
        assertDoesNotThrow(() -> taskList.sortDeadlines());
        assertDoesNotThrow(() -> taskList.sortEvents());
    }
}
