import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMAT = 
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = 
                    DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");

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

    public String getFrom() {
        return from.format(INPUT_FORMAT);
    }

    public String getTo() {
        return to.format(INPUT_FORMAT);
    }

    public LocalDateTime getFromDateTime() {
        return this.from;
    }

    public LocalDateTime getToDateTime() {
        return this.to;
    }
    
    @Override
    public String toString() {
        return "[" + TaskType.EVENT.getSymbol() + "]" + super.toString() 
                + " (from: " + from.format(OUTPUT_FORMAT) + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }
}