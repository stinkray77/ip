package snorax.exception;

/**
 * Represents a custom exception specific to the Snorax application.
 * This exception is thrown when errors occur during task management operations.
 */
public class SnoraxException extends Exception {
    
    /**
     * Constructs a SnoraxException with the specified error message.
     *
     * @param message The error message describing the exception.
     */
    public SnoraxException(String message) {
        super(message);
    }
}