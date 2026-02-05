package snorax.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task with a start time and end time.
 * An event task has a description and occurs during a specific time period.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMAT = 
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = 
                    DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");

    /**
     * Constructs an Event task with the specified description, start time, and end time.
     * Parses the date/time strings using the expected format (yyyy-MM-dd HHmm).
     *
     * @param description The description of the event.
     * @param from The start time as a string (e.g., "2024-12-25 1400").
     * @param to The end time as a string (e.g., "2024-12-25 1600").
     */
    public Event(String description, String from, String to) {
        super(description);
        try {
            this.from = LocalDateTime.parse(from, INPUT_FORMAT);
            this.to = LocalDateTime.parse(to, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            // Fallback
            this.from = LocalDateTime.parse(from);
            this.to = LocalDateTime.parse(to);
        }
    }

    /**
     * Returns the start time as a formatted string.
     *
     * @return The start time in the format "yyyy-MM-dd HHmm".
     */
    public String getFrom() {
        return from.format(INPUT_FORMAT);
    }

    /**
     * Returns the end time as a formatted string.
     *
     * @return The end time in the format "yyyy-MM-dd HHmm".
     */
    public String getTo() {
        return to.format(INPUT_FORMAT);
    }

    /**
     * Returns the start time as a LocalDateTime object.
     *
     * @return The start time as LocalDateTime.
     */
    public LocalDateTime getFromDateTime() {
        return this.from;
    }

    /**
     * Returns the end time as a LocalDateTime object.
     *
     * @return The end time as LocalDateTime.
     */
    public LocalDateTime getToDateTime() {
        return this.to;
    }
    
    /**
     * Returns a string representation of the event task.
     *
     * @return String with format "[E][status] description (from: start time to: end time)".
     */
    @Override
    public String toString() {
        return "[" + TaskType.EVENT.getSymbol() + "]" + super.toString() 
                + " (from: " + from.format(OUTPUT_FORMAT) + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }
}