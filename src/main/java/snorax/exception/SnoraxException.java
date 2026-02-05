package snorax.exception;

/**
 * Represents an exception specific to the Snorax application.
 * Used to signal errors that occur during Snorax operations.
 */
public class SnoraxException extends Exception {

    /**
     * Constructs a new SnoraxException with the specified error message.
     *
     * @param message The error message describing what went wrong.
     */
    public SnoraxException(String message) {
        super(message);
    }
}
