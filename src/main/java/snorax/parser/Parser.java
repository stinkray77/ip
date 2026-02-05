package snorax.parser;

import snorax.command.AddCommand;
import snorax.command.Command;
import snorax.command.DeleteCommand;
import snorax.command.ExitCommand;
import snorax.command.FindCommand;
import snorax.command.ListCommand;
import snorax.command.MarkCommand;
import snorax.command.UnmarkCommand;
import snorax.exception.SnoraxException;
import snorax.task.Deadline;
import snorax.task.Event;
import snorax.task.Todo;

/**
 * Parses user input and converts it into executable commands.
 */
public class Parser {

    /**
     * Parses the user input string and returns the corresponding command.
     *
     * @param input The user input string.
     * @return The command corresponding to the user input.
     * @throws SnoraxException if the input is invalid or cannot be parsed.
     */
    public static Command parse(String input) throws SnoraxException {
        if (input == null || input.trim().isEmpty()) {
            throw new SnoraxException("idk what that means, goodnight");
        }

        String[] parts = input.split(" ", 2);
        String commandWord = parts[0].toLowerCase();

        switch (commandWord) {
            case "bye":
                return new ExitCommand();

            case "list":
                return new ListCommand();

            case "mark":
                return new MarkCommand(parseTaskIndex(input));

            case "unmark":
                return new UnmarkCommand(parseTaskIndex(input));

            case "delete":
                return new DeleteCommand(parseTaskIndex(input));

            case "find":
                return new FindCommand(parseFindKeyword(input));

            case "todo":
                String description = parseTodoDescription(input);
                return new AddCommand(new Todo(description));

            case "deadline":
                String[] deadlineParts = parseDeadline(input);
                return new AddCommand(new Deadline(deadlineParts[0], deadlineParts[1]));

            case "event":
                String[] eventParts = parseEvent(input);
                return new AddCommand(new Event(eventParts[0], eventParts[1], eventParts[2]));

            default:
                throw new SnoraxException("idk what that means, goodnight");
        }
    }

    /**
     * Parses the task index from a command string.
     *
     * @param input The full command input.
     * @return The task index (0-based).
     * @throws SnoraxException if the index is invalid.
     */
    public static int parseTaskIndex(String input) throws SnoraxException {
        String[] parts = input.split(" ");

        if (parts.length < 2) {
            throw new SnoraxException("give valid index");
        }
        try {
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new SnoraxException("give valid index");
        }
    }

    /**
     * Parses the keyword from a find command.
     *
     * @param input The full command input.
     * @return The keyword to search for.
     * @throws SnoraxException if the keyword is empty.
     */
    public static String parseFindKeyword(String input) throws SnoraxException {
        String keyword = input.substring(5).trim();
        if (keyword.isEmpty()) {
            throw new SnoraxException("cannot be empty bro");
        }
        return keyword;
    }

    /**
     * Parses the description from a todo command.
     *
     * @param input The full command input.
     * @return The todo description.
     * @throws SnoraxException if the description is empty.
     */
    public static String parseTodoDescription(String input) throws SnoraxException {
        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new SnoraxException("cannot be empty bro");
        }
        return description;
    }

    /**
     * Parses the description and deadline from a deadline command.
     *
     * @param input The full command input.
     * @return An array containing [description, by].
     * @throws SnoraxException if the format is invalid.
     */
    public static String[] parseDeadline(String input) throws SnoraxException {
        String[] parts = input.substring(9).split(" /by ");
        if (parts.length < 2 || parts[0].trim().isEmpty()) {
            throw new SnoraxException("cannot be empty bro");
        }
        return new String[] { parts[0].trim(), parts[1].trim() };
    }

    /**
     * Parses the description, from, and to from an event command.
     *
     * @param input The full command input.
     * @return An array containing [description, from, to].
     * @throws SnoraxException if the format is invalid.
     */
    public static String[] parseEvent(String input) throws SnoraxException {
        String remaining = input.substring(6);
        String[] eventParts = remaining.split(" /from | /to ");
        if (eventParts.length < 3 || eventParts[0].trim().isEmpty()) {
            throw new SnoraxException("cannot be empty bro");
        }
        return new String[] { eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim() };
    }
}
