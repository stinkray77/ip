package snorax.task;

/**
 * Represents a todo task without any date/time attached to it.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task with the specified description.
     *
     * @param description The description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the todo task.
     *
     * @return String with format "[T][status] description".
     */
    @Override
    public String toString() {
        return "[" + TaskType.TODO.getSymbol() + "]" + super.toString();
    }
}
