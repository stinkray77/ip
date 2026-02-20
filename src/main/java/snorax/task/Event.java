package snorax.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents an event task with a start and end time.
 */
public class Event extends Task {
    private static final String INPUT_DATE_FORMAT = "uuuu-MM-dd HHmm";
    private static final String OUTPUT_DATE_FORMAT = "MMM dd yyyy, HH:mm";
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern(INPUT_DATE_FORMAT)
            .withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern(OUTPUT_DATE_FORMAT);

    private String from;
    private String to;
    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;

    /**
     * Constructs an Event task.
     *
     * @param description The description of the event.
     * @param from        The start date/time string.
     * @param to          The end date/time string.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
        this.fromDateTime = parseDateTime(from);
        this.toDateTime = parseDateTime(to);
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            return LocalDateTime.parse(dateTimeStr.trim(), INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Validates event times: format, non-existent dates, and start before end.
     *
     * @param from The start date/time string.
     * @param to   The end date/time string.
     * @throws IllegalArgumentException If validation fails.
     */
    public static void validate(String from, String to) {
        LocalDateTime fromDt;
        LocalDateTime toDt;

        try {
            fromDt = LocalDateTime.parse(from.trim(), INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Invalid start date/time: '" + from + "'\n"
                            + "Please use format: yyyy-MM-dd HHmm (e.g., 2025-03-15 0900)\n"
                            + "Make sure the date actually exists (e.g., not Feb 30).");
        }

        try {
            toDt = LocalDateTime.parse(to.trim(), INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Invalid end date/time: '" + to + "'\n"
                            + "Please use format: yyyy-MM-dd HHmm (e.g., 2025-03-15 1700)\n"
                            + "Make sure the date actually exists (e.g., not Feb 30).");
        }

        if (!fromDt.isBefore(toDt)) {
            throw new IllegalArgumentException(
                    "Event start time must be before end time.\n"
                            + "Start: " + from + "\n"
                            + "End:   " + to);
        }
    }

    /**
     * Gets the raw start date/time string.
     *
     * @return The from string.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Gets the raw end date/time string.
     *
     * @return The to string.
     */
    public String getTo() {
        return to;
    }

    /**
     * Gets the start time as LocalDateTime.
     *
     * @return The fromDateTime.
     */
    public LocalDateTime getFromDateTime() {
        return fromDateTime;
    }

    /**
     * Gets the end time as LocalDateTime.
     *
     * @return The toDateTime.
     */
    public LocalDateTime getToDateTime() {
        return toDateTime;
    }

    @Override
    public String toString() {
        String displayFrom = fromDateTime != null
                ? fromDateTime.format(OUTPUT_FORMATTER)
                : from;
        String displayTo = toDateTime != null
                ? toDateTime.format(OUTPUT_FORMATTER)
                : to;
        return "[E]" + super.toString() + " (from: " + displayFrom + " to: " + displayTo + ")";
    }
}
