package snorax.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a deadline task with a specific due date and time.
 * A deadline task must be completed by a certain date and time.
 */
public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyy h:mma");

    /**
     * Constructs a Deadline task with the specified description and deadline.
     *
     * @param description The description of the deadline task.
     * @param by          The deadline as a string (format: yyyy-MM-dd HHmm).
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
     * @param by          The deadline as a LocalDateTime object.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the deadline as a formatted string.
     *
     * @return The deadline in format yyyy-MM-dd HHmm.
     */
    public String getBy() {
        return by.format(INPUT_FORMAT);
    }

    /**
     * Returns the deadline as a LocalDateTime object.
     *
     * @return The deadline.
     */
    public LocalDateTime getByDateTime() {
        return by;
    }

    /**
     * Returns a string representation of the deadline task.
     *
     * @return String with format "[D][status] description (by: deadline)".
     */
    @Override
    public String toString() {
        return "[" + TaskType.DEADLINE.getSymbol() + "]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}
