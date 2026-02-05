package snorax.task;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    @Test
    public void testConstructor_validInput() {
        Event event = new Event("Project meeting", "2024-12-25 1400", "2024-12-25 1600");
        assertEquals("Project meeting", event.getDescription());
        assertFalse(event.isDone());
    }

    @Test
    public void testGetFrom_returnsFormattedDate() {
        Event event = new Event("Conference", "2024-12-25 0900", "2024-12-26 1700");
        assertEquals("2024-12-25 0900", event.getFrom());
    }

    @Test
    public void testGetTo_returnsFormattedDate() {
        Event event = new Event("Conference", "2024-12-25 0900", "2024-12-26 1700");
        assertEquals("2024-12-26 1700", event.getTo());
    }

    @Test
    public void testGetFromDateTime_returnsLocalDateTime() {
        Event event = new Event("Workshop", "2024-12-25 0900", "2024-12-25 1700");
        LocalDateTime expected = LocalDateTime.of(2024, 12, 25, 9, 0);
        assertEquals(expected, event.getFromDateTime());
    }

    @Test
    public void testGetToDateTime_returnsLocalDateTime() {
        Event event = new Event("Workshop", "2024-12-25 0900", "2024-12-25 1700");
        LocalDateTime expected = LocalDateTime.of(2024, 12, 25, 17, 0);
        assertEquals(expected, event.getToDateTime());
    }

    @Test
    public void testToString_unmarkedEvent() {
        Event event = new Event("Conference", "2024-12-25 0900", "2024-12-26 1700");
        String result = event.toString();
        assertTrue(result.startsWith("[E][ ] Conference"));
        assertTrue(result.contains("from:") && result.contains("to:"));
    }

    @Test
    public void testToString_markedEvent() {
        Event event = new Event("Conference", "2024-12-25 0900", "2024-12-26 1700");
        event.markAsDone();
        String result = event.toString();
        assertTrue(result.startsWith("[E][X] Conference"));
    }
}