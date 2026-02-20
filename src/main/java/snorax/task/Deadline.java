package snorax.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    private static final String INPUT_DATE_FORMAT = "uuuu-MM-dd HHmm";
    private static final String OUTPUT_DATE_FORMAT = "MMM dd yyyy, HH:mm";
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern(INPUT_DATE_FORMAT)
            .withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern(OUTPUT_DATE_FORMAT);

    private String by;
    private LocalDateTime byDateTime;

    /**
     * Constructs a Deadline task.
     *
     * @param description The description of the task.
     * @param by          The deadline date/time string.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        this.byDateTime = parseDateTime(by);
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            return LocalDateTime.parse(dateTimeStr.trim(), INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            // Return null for raw string storage (loaded from file in old format)
            return null;
        }
    }

    /**
     * Validates the deadline date string, throwing if invalid or non-existent.
     *
     * @param by The deadline string to validate.
     * @throws IllegalArgumentException If the date is invalid.
     */
    public static void validate(String by) {
        try {
            LocalDateTime.parse(by.trim(), INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Invalid deadline date/time: '" + by + "'\n"
                            + "Please use format: yyyy-MM-dd HHmm (e.g., 2025-03-15 1430)\n"
                            + "Make sure the date actually exists (e.g., not Feb 30).");
        }
    }

    /**
     * Gets the raw deadline string.
     *
     * @return The deadline string.
     */
    public String getBy() {
        return by;
    }

    /**
     * Gets the deadline as a LocalDateTime.
     *
     * @return The deadline LocalDateTime, or null if not parseable.
     */
    public LocalDateTime getByDateTime() {
        return byDateTime;
    }

    @Override
    public String toString() {
        String displayBy = byDateTime != null
                ? byDateTime.format(OUTPUT_FORMATTER)
                : by;
        return "[D]" + super.toString() + " (by: " + displayBy + ")";
    }
}
