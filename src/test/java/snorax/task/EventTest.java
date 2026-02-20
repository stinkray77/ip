package snorax.task;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    // ======================== Constructor ========================

    @Test
    public void testConstructor_validInput() {
        Event event = new Event("Project meeting", "2024-12-25 1400", "2024-12-25 1600");
        assertEquals("Project meeting", event.getDescription());
        assertFalse(event.isDone());
    }

    @Test
    public void testConstructor_unparsableDatesStored() {
        Event event = new Event("Meeting", "monday 2pm", "monday 4pm");
        assertEquals("monday 2pm", event.getFrom());
        assertEquals("monday 4pm", event.getTo());
        assertNull(event.getFromDateTime());
        assertNull(event.getToDateTime());
    }

    // ======================== Getters ========================

    @Test
    public void testGetFrom_returnsRawString() {
        Event event = new Event("Conference", "2024-12-25 0900", "2024-12-26 1700");
        assertEquals("2024-12-25 0900", event.getFrom());
    }

    @Test
    public void testGetTo_returnsRawString() {
        Event event = new Event("Conference", "2024-12-25 0900", "2024-12-26 1700");
        assertEquals("2024-12-26 1700", event.getTo());
    }

    @Test
    public void testGetFromDateTime_returnsCorrectDateTime() {
        Event event = new Event("Workshop", "2024-12-25 0900", "2024-12-25 1700");
        LocalDateTime expected = LocalDateTime.of(2024, 12, 25, 9, 0);
        assertEquals(expected, event.getFromDateTime());
    }

    @Test
    public void testGetToDateTime_returnsCorrectDateTime() {
        Event event = new Event("Workshop", "2024-12-25 0900", "2024-12-25 1700");
        LocalDateTime expected = LocalDateTime.of(2024, 12, 25, 17, 0);
        assertEquals(expected, event.getToDateTime());
    }

    @Test
    public void testGetFromDateTime_invalidFormat_returnsNull() {
        Event event = new Event("Meeting", "invalid", "2024-12-25 1700");
        assertNull(event.getFromDateTime());
    }

    // ======================== Marking ========================

    @Test
    public void testMarkAsDone() {
        Event event = new Event("Conference", "2024-12-25 0900", "2024-12-26 1700");
        event.markAsDone();
        assertTrue(event.isDone());
    }

    @Test
    public void testMarkAsNotDone() {
        Event event = new Event("Conference", "2024-12-25 0900", "2024-12-26 1700");
        event.markAsDone();
        event.markAsNotDone();
        assertFalse(event.isDone());
    }

    // ======================== toString ========================

    @Test
    public void testToString_unmarked() {
        Event event = new Event("Conference", "2024-12-25 0900", "2024-12-26 1700");
        String result = event.toString();
        assertTrue(result.startsWith("[E][ ] Conference"));
        assertTrue(result.contains("from:"));
        assertTrue(result.contains("to:"));
    }

    @Test
    public void testToString_marked() {
        Event event = new Event("Conference", "2024-12-25 0900", "2024-12-26 1700");
        event.markAsDone();
        assertTrue(event.toString().startsWith("[E][X] Conference"));
    }

    @Test
    public void testToString_invalidDates_showsRawStrings() {
        Event event = new Event("Meeting", "monday 2pm", "monday 4pm");
        String result = event.toString();
        assertTrue(result.contains("monday 2pm"));
        assertTrue(result.contains("monday 4pm"));
    }

    // ======================== Validation ========================

    @Test
    public void testValidate_validDates_noException() {
        assertDoesNotThrow(() -> Event.validate("2024-12-25 0900", "2024-12-25 1700"));
    }

    @Test
    public void testValidate_startAfterEnd_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> Event.validate("2024-12-25 1700", "2024-12-25 0900"));
    }

    @Test
    public void testValidate_startEqualsEnd_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> Event.validate("2024-12-25 1400", "2024-12-25 1400"));
    }

    @Test
    public void testValidate_invalidFromDate_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> Event.validate("2024-02-30 0900", "2024-12-25 1700"));
    }

    @Test
    public void testValidate_invalidToDate_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> Event.validate("2024-12-25 0900", "2024-02-30 1700"));
    }

    @Test
    public void testValidate_nonExistentFromMonth_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> Event.validate("2024-13-01 0900", "2024-12-25 1700"));
    }

    @Test
    public void testValidate_invalidFromTime_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> Event.validate("2024-12-25 2500", "2024-12-26 1700"));
    }

    @Test
    public void testValidate_multiDayEvent_valid() {
        assertDoesNotThrow(() -> Event.validate("2024-12-25 0900", "2024-12-26 1700"));
    }

    @Test
    public void testValidate_leapYearFrom_valid() {
        assertDoesNotThrow(() -> Event.validate("2024-02-29 0900", "2024-02-29 1700"));
    }

    @Test
    public void testValidate_leapYearNonLeap_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> Event.validate("2023-02-29 0900", "2023-03-01 1700"));
    }
}
