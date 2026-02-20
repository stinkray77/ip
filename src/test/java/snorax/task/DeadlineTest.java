package snorax.task;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class DeadlineTest {

    // ======================== Constructor ========================

    @Test
    public void testConstructor_validInput() {
        Deadline deadline = new Deadline("Submit report", "2024-12-31 2359");
        assertEquals("Submit report", deadline.getDescription());
        assertFalse(deadline.isDone());
    }

    @Test
    public void testConstructor_unparsableDateStored() {
        // Raw strings (e.g., loaded from old file) should still be stored
        Deadline deadline = new Deadline("Submit report", "next monday");
        assertEquals("next monday", deadline.getBy());
        assertNull(deadline.getByDateTime());
    }

    // ======================== Getters ========================

    @Test
    public void testGetBy_returnsRawString() {
        Deadline deadline = new Deadline("Return book", "2024-12-31 2359");
        assertEquals("2024-12-31 2359", deadline.getBy());
    }

    @Test
    public void testGetByDateTime_returnsCorrectDateTime() {
        Deadline deadline = new Deadline("Submit assignment", "2024-12-31 2359");
        LocalDateTime expected = LocalDateTime.of(2024, 12, 31, 23, 59);
        assertEquals(expected, deadline.getByDateTime());
    }

    @Test
    public void testGetByDateTime_invalidFormat_returnsNull() {
        Deadline deadline = new Deadline("Task", "invalid-date");
        assertNull(deadline.getByDateTime());
    }

    // ======================== Marking ========================

    @Test
    public void testMarkAsDone() {
        Deadline deadline = new Deadline("Submit report", "2024-12-31 2359");
        deadline.markAsDone();
        assertTrue(deadline.isDone());
    }

    @Test
    public void testMarkAsNotDone() {
        Deadline deadline = new Deadline("Submit report", "2024-12-31 2359");
        deadline.markAsDone();
        deadline.markAsNotDone();
        assertFalse(deadline.isDone());
    }

    // ======================== toString ========================

    @Test
    public void testToString_unmarked() {
        Deadline deadline = new Deadline("Return book", "2024-12-31 2359");
        String result = deadline.toString();
        assertTrue(result.startsWith("[D][ ] Return book"));
        assertTrue(result.contains("by:"));
    }

    @Test
    public void testToString_marked() {
        Deadline deadline = new Deadline("Return book", "2024-12-31 2359");
        deadline.markAsDone();
        assertTrue(deadline.toString().startsWith("[D][X] Return book"));
    }

    @Test
    public void testToString_invalidDate_showsRawString() {
        Deadline deadline = new Deadline("Task", "next monday");
        assertTrue(deadline.toString().contains("next monday"));
    }

    // ======================== Validation ========================

    @Test
    public void testValidate_validDate_noException() {
        assertDoesNotThrow(() -> Deadline.validate("2024-12-31 2359"));
    }

    @Test
    public void testValidate_invalidFormat_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> Deadline.validate("31-12-2024 2359"));
    }

    @Test
    public void testValidate_nonExistentDate_throwsException() {
        // Feb 30 does not exist
        assertThrows(IllegalArgumentException.class, () -> Deadline.validate("2024-02-30 1200"));
    }

    @Test
    public void testValidate_nonExistentMonth_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> Deadline.validate("2024-13-01 1200"));
    }

    @Test
    public void testValidate_invalidTime_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> Deadline.validate("2024-12-31 2500"));
    }

    @Test
    public void testValidate_emptyString_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> Deadline.validate(""));
    }

    @Test
    public void testValidate_leapYearValid() {
        // 2024 is a leap year, Feb 29 exists
        assertDoesNotThrow(() -> Deadline.validate("2024-02-29 1200"));
    }

    @Test
    public void testValidate_leapYearInvalid() {
        // 2023 is not a leap year, Feb 29 does not exist
        assertThrows(IllegalArgumentException.class, () -> Deadline.validate("2023-02-29 1200"));
    }
}
