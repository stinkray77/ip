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
    public void testMark_taskBecomesCompleted() {
        Todo todo = new Todo("Complete task");
        assertFalse(todo.isDone());
        
        todo.mark();
        assertTrue(todo.isDone());
    }

    @Test
    public void testUnmark_taskBecomesIncomplete() {
        Todo todo = new Todo("Incomplete task");
        todo.mark();
        assertTrue(todo.isDone());
        
        todo.unmark();
        assertFalse(todo.isDone());
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
        todo.mark();
        String expected = "[T][X] Buy groceries";
        assertEquals(expected, todo.toString());
    }

    @Test
    public void testToFileFormat_unmarkedTask() {
        Todo todo = new Todo("Read book");
        String expected = "T | 0 | Read book";
        assertEquals(expected, todo.toFileFormat());
    }

    @Test
    public void testToFileFormat_markedTask() {
        Todo todo = new Todo("Read book");
        todo.mark();
        String expected = "T | 1 | Read book";
        assertEquals(expected, todo.toFileFormat());
    }
}