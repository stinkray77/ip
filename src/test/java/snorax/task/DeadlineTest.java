package snorax.task;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class DeadlineTest {

    @Test
    public void testConstructor_validInput() {
        Deadline deadline = new Deadline("Submit report", "2024-12-31 2359");
        assertEquals("Submit report", deadline.getDescription());
        assertFalse(deadline.isDone());
    }

    @Test
    public void testGetBy_returnsFormattedDate() {
        Deadline deadline = new Deadline("Return book", "2024-12-31 2359");
        assertEquals("2024-12-31 2359", deadline.getBy());
    }

    @Test
    public void testGetByDateTime_returnsLocalDateTime() {
        Deadline deadline = new Deadline("Submit assignment", "2024-12-31 2359");
        LocalDateTime expected = LocalDateTime.of(2024, 12, 31, 23, 59);
        assertEquals(expected, deadline.getByDateTime());
    }

    @Test
    public void testToString_unmarkedDeadline() {
        Deadline deadline = new Deadline("Return book", "2024-12-31 2359");
        String result = deadline.toString();
        assertTrue(result.startsWith("[D][ ] Return book"));
        assertTrue(result.contains("by:"));
    }

    @Test
    public void testToString_markedDeadline() {
        Deadline deadline = new Deadline("Return book", "2024-12-31 2359");
        deadline.markAsDone();
        String result = deadline.toString();
        assertTrue(result.startsWith("[D][X] Return book"));
    }
}
