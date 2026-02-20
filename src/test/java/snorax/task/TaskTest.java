package snorax.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    // ======================== Constructor ========================

    @Test
    public void testConstructor_validDescription() {
        Todo todo = new Todo("Read book");
        assertEquals("Read book", todo.getDescription());
        assertFalse(todo.isDone());
    }

    @Test
    public void testConstructor_descriptionWithSpaces() {
        Todo todo = new Todo("  Read book  ");
        assertEquals("  Read book  ", todo.getDescription());
    }

    // ======================== Mark / Unmark ========================

    @Test
    public void testMarkAsDone() {
        Todo todo = new Todo("Complete task");
        todo.markAsDone();
        assertTrue(todo.isDone());
    }

    @Test
    public void testMarkAsDone_alreadyDone_remainsDone() {
        Todo todo = new Todo("Complete task");
        todo.markAsDone();
        todo.markAsDone();
        assertTrue(todo.isDone());
    }

    @Test
    public void testMarkAsNotDone() {
        Todo todo = new Todo("Incomplete task");
        todo.markAsDone();
        todo.markAsNotDone();
        assertFalse(todo.isDone());
    }

    @Test
    public void testMarkAsNotDone_alreadyNotDone_remainsNotDone() {
        Todo todo = new Todo("Incomplete task");
        todo.markAsNotDone();
        assertFalse(todo.isDone());
    }

    // ======================== Status Icon ========================

    @Test
    public void testGetStatusIcon_notDone() {
        Todo todo = new Todo("Incomplete task");
        assertEquals(" ", todo.getStatusIcon());
    }

    @Test
    public void testGetStatusIcon_done() {
        Todo todo = new Todo("Completed task");
        todo.markAsDone();
        assertEquals("X", todo.getStatusIcon());
    }

    // ======================== toString ========================

    @Test
    public void testToString_notDone() {
        Todo todo = new Todo("Buy groceries");
        assertEquals("[T][ ] Buy groceries", todo.toString());
    }

    @Test
    public void testToString_done() {
        Todo todo = new Todo("Buy groceries");
        todo.markAsDone();
        assertEquals("[T][X] Buy groceries", todo.toString());
    }

    @Test
    public void testToString_descriptionPreserved() {
        String description = "A task with special chars: @#$%";
        Todo todo = new Todo(description);
        assertTrue(todo.toString().contains(description));
    }

    // ======================== getDescription ========================

    @Test
    public void testGetDescription_returnsExactDescription() {
        String desc = "Buy 3 apples & 2 oranges";
        Todo todo = new Todo(desc);
        assertEquals(desc, todo.getDescription());
    }
}
