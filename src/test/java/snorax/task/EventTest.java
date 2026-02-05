package snorax.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    @Test
    public void testConstructor_validInput() {
        Event event = new Event("Project meeting", "2024-12-25", "2024-12-26");
        assertEquals("Project meeting", event.getDescription());
        assertFalse(event.isDone());
    }

    @Test
    public void testToString_unmarkedEvent() {
        Event event = new Event("Conference", "2024-12-25", "2024-12-26");
        String result = event.toString();
        assertTrue(result.startsWith("[E][ ] Conference"));
        assertTrue(result.contains("from") || result.contains("to"));
    }

    @Test
    public void testToString_markedEvent() {
        Event event = new Event("Conference", "2024-12-25", "2024-12-26");
        event.mark();
        String result = event.toString();
        assertTrue(result.startsWith("[E][X] Conference"));
    }

    @Test
    public void testToFileFormat() {
        Event event = new Event("Workshop", "2024-12-25", "2024-12-26");
        String expected = "E | 0 | Workshop | 2024-12-25 | 2024-12-26";
        assertEquals(expected, event.toFileFormat());
    }

    @Test
    public void testToFileFormat_marked() {
        Event event = new Event("Workshop", "2024-12-25", "2024-12-26");
        event.mark();
        String expected = "E | 1 | Workshop | 2024-12-25 | 2024-12-26";
        assertEquals(expected, event.toFileFormat());
    }
}