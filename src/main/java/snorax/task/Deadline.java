package snorax.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline.
 * A deadline task has a description and a specific date/time by which it must be completed.
 */
public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyy h:mma");

    /**
     * Constructs a Deadline task with the specified description and deadline string.
     * Parses the deadline string using the expected format (yyyy-MM-dd HHmm).
     *
     * @param description The description of the deadline task.
     * @param by The deadline as a string (e.g., "2024-12-31 2359").
     */
    public Deadline(String description, String by) {
        super(description);
        try {
            this.by = LocalDateTime.parse(by, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            this.by = LocalDateTime.parse(by);
        }
    }

    /**
     * Constructs a Deadline task with the specified description and deadline.
     *
     * @param description The description of the deadline task.
     * @param by The deadline as a LocalDateTime object.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the deadline as a formatted string.
     *
     * @return The deadline in the format "yyyy-MM-dd HHmm".
     */
    public String getBy() {
        return by.format(INPUT_FORMAT);
    }

    /**
     * Returns the deadline as a LocalDateTime object.
     *
     * @return The deadline as LocalDateTime.
     */
    public LocalDateTime getByDateTime() {
        return by;
    }
    
    /**
     * Returns a string representation of the deadline task.
     *
     * @return String with format "[D][status] description (by: formatted deadline)".
     */
    @Override
    public String toString() {
        return "[" + TaskType.DEADLINE.getSymbol() + "]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}
