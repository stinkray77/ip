package snorax.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeadlineTest {

    @Test
    public void testConstructor_validInput() {
        Deadline deadline = new Deadline("Submit report", "2024-12-31");
        assertEquals("Submit report", deadline.getDescription());
        assertFalse(deadline.isDone());
    }

    @Test
    public void testToString_unmarkedDeadline() {
        Deadline deadline = new Deadline("Return book", "2024-12-31");
        String result = deadline.toString();
        assertTrue(result.startsWith("[D][ ] Return book"));
        assertTrue(result.contains("2024-12-31") || result.contains("Dec"));
    }

    @Test
    public void testToString_markedDeadline() {
        Deadline deadline = new Deadline("Return book", "2024-12-31");
        deadline.mark();
        String result = deadline.toString();
        assertTrue(result.startsWith("[D][X] Return book"));
    }

    @Test
    public void testToFileFormat() {
        Deadline deadline = new Deadline("Submit assignment", "2024-12-31");
        String expected = "D | 0 | Submit assignment | 2024-12-31";
        assertEquals(expected, deadline.toFileFormat());
    }

    @Test
    public void testToFileFormat_marked() {
        Deadline deadline = new Deadline("Submit assignment", "2024-12-31");
        deadline.mark();
        String expected = "D | 1 | Submit assignment | 2024-12-31";
        assertEquals(expected, deadline.toFileFormat());
    }
}