package snorax.task;

/**
 * Enum representing the different types of tasks in the Snorax application.
 * Each task type has an associated symbol used for display and file storage.
 */
public enum TaskType {
    /** Represents a todo task. */
    TODO("T"),
    
    /** Represents a deadline task. */
    DEADLINE("D"),
    
    /** Represents an event task. */
    EVENT("E");

    private final String symbol;

    /**
     * Constructs a TaskType with the specified symbol.
     *
     * @param symbol The single-letter symbol representing this task type.
     */
    TaskType(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the symbol associated with this task type.
     *
     * @return The single-letter symbol (e.g., "T" for TODO).
     */
    public String getSymbol() {
        return symbol;
    }
}
