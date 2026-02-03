import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyy h:mma");

    public Deadline(String description, String by) {
        super(description);
        try {
            this.by = LocalDateTime.parse(by, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            this.by = LocalDateTime.parse(by);
        }
    }

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public String getBy() {
        return by.format(INPUT_FORMAT);
    }

    public LocalDateTime getByDateTime() {
        return by;
    }
    
    @Override
    public String toString() {
        return "[" + TaskType.DEADLINE.getSymbol() + "]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}
