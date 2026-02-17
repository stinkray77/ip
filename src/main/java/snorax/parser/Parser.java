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
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK = "mark";
    private static final String COMMAND_UNMARK = "unmark";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";
    private static final String COMMAND_FIND = "find";

    private static final String DELIMITER_BY = "/by";
    private static final String DELIMITER_FROM = "/from";
    private static final String DELIMITER_TO = "/to";

    private static final int TASK_INDEX_OFFSET = 1;

    /**
     * Parses the user input string and returns the corresponding command.
     *
     * @param input The user input string.
     * @return The command corresponding to the user input.
     * @throws SnoraxException If the input is invalid or cannot be parsed.
     */
    public static Command parse(String input) throws SnoraxException {
        assert input != null : "Input cannot be null";

        String trimmedInput = input.trim();
        if (trimmedInput.isEmpty()) {
            throw new SnoraxException("Please enter a command!");
        }

        String[] parts = trimmedInput.split(" ", 2);
        String commandWord = parts[0].toLowerCase();

        switch (commandWord) {
            case COMMAND_BYE:
                return parseExitCommand();
            case COMMAND_LIST:
                return parseListCommand();
            case COMMAND_MARK:
                return parseMarkCommand(parts);
            case COMMAND_UNMARK:
                return parseUnmarkCommand(parts);
            case COMMAND_DELETE:
                return parseDeleteCommand(parts);
            case COMMAND_TODO:
                return parseTodoCommand(parts);
            case COMMAND_DEADLINE:
                return parseDeadlineCommand(parts);
            case COMMAND_EVENT:
                return parseEventCommand(parts);
            case COMMAND_FIND:
                return parseFindCommand(parts);
            default:
                throw new SnoraxException("I don't understand that command!");
        }
    }

    private static Command parseExitCommand() {
        return new ExitCommand();
    }

    private static Command parseListCommand() {
        return new ListCommand();
    }

    private static Command parseMarkCommand(String[] parts) throws SnoraxException {
        validateCommandHasArgument(parts, "mark");
        int taskIndex = parseTaskIndex(parts[1]);
        return new MarkCommand(taskIndex);
    }

    private static Command parseUnmarkCommand(String[] parts) throws SnoraxException {
        validateCommandHasArgument(parts, "unmark");
        int taskIndex = parseTaskIndex(parts[1]);
        return new UnmarkCommand(taskIndex);
    }

    private static Command parseDeleteCommand(String[] parts) throws SnoraxException {
        validateCommandHasArgument(parts, "delete");
        int taskIndex = parseTaskIndex(parts[1]);
        return new DeleteCommand(taskIndex);
    }

    private static Command parseTodoCommand(String[] parts) throws SnoraxException {
        validateCommandHasArgument(parts, "todo");
        String description = parts[1].trim();
        validateNotEmpty(description, "The description of a todo cannot be empty.");
        return new AddCommand(new Todo(description));
    }

    private static Command parseDeadlineCommand(String[] parts) throws SnoraxException {
        validateCommandHasArgument(parts, "deadline");

        String[] deadlineParts = parts[1].split(DELIMITER_BY, 2);
        validateDeadlineFormat(deadlineParts);

        String description = deadlineParts[0].trim();
        String by = deadlineParts[1].trim();

        validateNotEmpty(description, "The description of a deadline cannot be empty.");
        validateNotEmpty(by, "The deadline time cannot be empty.");

        return new AddCommand(new Deadline(description, by));
    }

    private static Command parseEventCommand(String[] parts) throws SnoraxException {
        validateCommandHasArgument(parts, "event");

        String[] eventParts = parts[1].split(DELIMITER_FROM, 2);
        validateEventHasFrom(eventParts);

        String description = eventParts[0].trim();
        String[] timeParts = eventParts[1].split(DELIMITER_TO, 2);
        validateEventHasTo(timeParts);

        String from = timeParts[0].trim();
        String to = timeParts[1].trim();

        validateNotEmpty(description, "The description of an event cannot be empty.");
        validateNotEmpty(from, "The start time cannot be empty.");
        validateNotEmpty(to, "The end time cannot be empty.");

        return new AddCommand(new Event(description, from, to));
    }

    private static Command parseFindCommand(String[] parts) throws SnoraxException {
        validateCommandHasArgument(parts, "find");
        String keyword = parts[1].trim();
        validateNotEmpty(keyword, "The search keyword cannot be empty.");
        return new FindCommand(keyword);
    }

    private static void validateCommandHasArgument(String[] parts, String commandName)
            throws SnoraxException {
        if (parts.length < 2) {
            throw new SnoraxException("The " + commandName + " command requires an argument!");
        }
    }

    private static void validateNotEmpty(String value, String errorMessage) throws SnoraxException {
        if (value.isEmpty()) {
            throw new SnoraxException(errorMessage);
        }
    }

    private static void validateDeadlineFormat(String[] parts) throws SnoraxException {
        if (parts.length < 2) {
            throw new SnoraxException("Please use format: deadline <description> /by <time>");
        }
    }

    private static void validateEventHasFrom(String[] parts) throws SnoraxException {
        if (parts.length < 2) {
            throw new SnoraxException("Please use format: event <description> /from <start> /to <end>");
        }
    }

    private static void validateEventHasTo(String[] parts) throws SnoraxException {
        if (parts.length < 2) {
            throw new SnoraxException("Please use format: event <description> /from <start> /to <end>");
        }
    }

    private static int parseTaskIndex(String indexString) throws SnoraxException {
        try {
            int index = Integer.parseInt(indexString.trim()) - TASK_INDEX_OFFSET;
            if (index < 0) {
                throw new SnoraxException("Task number must be positive!");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new SnoraxException("Please provide a valid task number!");
        }
    }
}
