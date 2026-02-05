package snorax.task;

/**
 * Enumeration representing the different types of tasks.
 * Each task type has an associated symbol for display purposes.
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
     * @param symbol The single-character symbol representing this task type.
     */
    TaskType(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the symbol associated with this task type.
     *
     * @return The single-character symbol (e.g., "T" for TODO).
     */
    public String getSymbol() {
        return symbol;
    }
}
