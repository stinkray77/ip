package snorax.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void testConstructor_validDescription() {
        Todo todo = new Todo("Read book");
        assertEquals("Read book", todo.getDescription());
        assertFalse(todo.isDone());
    }

    @Test
    public void testMarkAsDone_taskBecomesCompleted() {
        Todo todo = new Todo("Complete task");
        assertFalse(todo.isDone());
        
        todo.markAsDone();
        assertTrue(todo.isDone());
    }

    @Test
    public void testMarkAsNotDone_taskBecomesIncomplete() {
        Todo todo = new Todo("Incomplete task");
        todo.markAsDone();
        assertTrue(todo.isDone());
        
        todo.markAsNotDone();
        assertFalse(todo.isDone());
    }

    @Test
    public void testGetStatusIcon_unmarkedTask() {
        Todo todo = new Todo("Incomplete task");
        assertEquals(" ", todo.getStatusIcon());
    }

    @Test
    public void testGetStatusIcon_markedTask() {
        Todo todo = new Todo("Completed task");
        todo.markAsDone();
        assertEquals("X", todo.getStatusIcon());
    }

    @Test
    public void testToString_unmarkedTask() {
        Todo todo = new Todo("Buy groceries");
        String expected = "[T][ ] Buy groceries";
        assertEquals(expected, todo.toString());
    }

    @Test
    public void testToString_markedTask() {
        Todo todo = new Todo("Buy groceries");
        todo.markAsDone();
        String expected = "[T][X] Buy groceries";
        assertEquals(expected, todo.toString());
    }
}