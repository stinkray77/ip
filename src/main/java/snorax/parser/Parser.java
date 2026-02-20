package snorax.parser;

import snorax.command.AddCommand;
import snorax.command.Command;
import snorax.command.DeleteCommand;
import snorax.command.ExitCommand;
import snorax.command.FindCommand;
import snorax.command.ListCommand;
import snorax.command.MarkCommand;
import snorax.command.SortCommand;
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
    private static final String COMMAND_SORT = "sort";

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
        if (input == null || input.trim().isEmpty()) {
            throw new SnoraxException("Please enter a command!");
        }

        // Normalise multiple spaces to single space
        String normalised = input.trim().replaceAll("\\s+", " ");
        String[] parts = normalised.split(" ", 2);
        String commandWord = parts[0].toLowerCase();

        switch (commandWord) {
            case COMMAND_BYE:
                return new ExitCommand();
            case COMMAND_LIST:
                return new ListCommand();
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
            case COMMAND_SORT:
                return parseSortCommand(parts);
            default:
                throw new SnoraxException("Unknown command: '" + commandWord + "'\n"
                        + "Valid commands: todo, deadline, event, list, mark, unmark, "
                        + "delete, find, sort, bye");
        }
    }

    private static Command parseMarkCommand(String[] parts) throws SnoraxException {
        validateHasArgument(parts, "mark <task number>");
        return new MarkCommand(parseTaskIndex(parts[1], "mark"));
    }

    private static Command parseUnmarkCommand(String[] parts) throws SnoraxException {
        validateHasArgument(parts, "unmark <task number>");
        return new UnmarkCommand(parseTaskIndex(parts[1], "unmark"));
    }

    private static Command parseDeleteCommand(String[] parts) throws SnoraxException {
        validateHasArgument(parts, "delete <task number>");
        return new DeleteCommand(parseTaskIndex(parts[1], "delete"));
    }

    private static Command parseTodoCommand(String[] parts) throws SnoraxException {
        validateHasArgument(parts, "todo <description>");
        String description = parts[1].trim();
        validateNotEmpty(description, "Todo description cannot be empty.\nUsage: todo <description>");
        validateNoSpecialDelimiters(description, "todo description");
        return new AddCommand(new Todo(description));
    }

    private static Command parseDeadlineCommand(String[] parts) throws SnoraxException {
        validateHasArgument(parts, "deadline <description> /by <date time>");

        // Check for multiple /by
        long byCount = countOccurrences(parts[1], DELIMITER_BY);
        if (byCount > 1) {
            throw new SnoraxException("Multiple '/by' found. Please use only one.\n"
                    + "Usage: deadline <description> /by <date time>");
        }

        String[] deadlineParts = parts[1].split(DELIMITER_BY, 2);
        if (deadlineParts.length < 2) {
            throw new SnoraxException("Missing '/by' in deadline command.\n"
                    + "Usage: deadline <description> /by <yyyy-MM-dd HHmm>");
        }

        String description = deadlineParts[0].trim();
        String by = deadlineParts[1].trim();

        validateNotEmpty(description, "Deadline description cannot be empty.\n"
                + "Usage: deadline <description> /by <yyyy-MM-dd HHmm>");
        validateNotEmpty(by, "Deadline time cannot be empty.\n"
                + "Usage: deadline <description> /by <yyyy-MM-dd HHmm>");
        validateNoSpecialDelimiters(description, "deadline description");

        return new AddCommand(new Deadline(description, by));
    }

    private static Command parseEventCommand(String[] parts) throws SnoraxException {
        validateHasArgument(parts, "event <description> /from <start> /to <end>");

        // Check for multiple /from or /to
        long fromCount = countOccurrences(parts[1], DELIMITER_FROM);
        long toCount = countOccurrences(parts[1], DELIMITER_TO);

        if (fromCount > 1) {
            throw new SnoraxException("Multiple '/from' found. Please use only one.\n"
                    + "Usage: event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
        }
        if (toCount > 1) {
            throw new SnoraxException("Multiple '/to' found. Please use only one.\n"
                    + "Usage: event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
        }

        String[] fromParts = parts[1].split(DELIMITER_FROM, 2);
        if (fromParts.length < 2) {
            throw new SnoraxException("Missing '/from' in event command.\n"
                    + "Usage: event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
        }

        String description = fromParts[0].trim();
        String[] toParts = fromParts[1].split(DELIMITER_TO, 2);

        if (toParts.length < 2) {
            throw new SnoraxException("Missing '/to' in event command.\n"
                    + "Usage: event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
        }

        String from = toParts[0].trim();
        String to = toParts[1].trim();

        validateNotEmpty(description, "Event description cannot be empty.");
        validateNotEmpty(from, "Event start time cannot be empty.");
        validateNotEmpty(to, "Event end time cannot be empty.");
        validateNoSpecialDelimiters(description, "event description");

        return new AddCommand(new Event(description, from, to));
    }

    private static Command parseFindCommand(String[] parts) throws SnoraxException {
        validateHasArgument(parts, "find <keyword>");
        String keyword = parts[1].trim();
        validateNotEmpty(keyword, "Search keyword cannot be empty.\nUsage: find <keyword>");
        return new FindCommand(keyword);
    }

    private static Command parseSortCommand(String[] parts) throws SnoraxException {
        if (parts.length < 2) {
            return new SortCommand();
        }
        String sortType = parts[1].trim().toLowerCase();
        if (!sortType.equals("deadline") && !sortType.equals("event") && !sortType.equals("all")) {
            throw new SnoraxException("Invalid sort type: '" + sortType + "'\n"
                    + "Usage: sort | sort all | sort deadline | sort event");
        }
        return new SortCommand(sortType);
    }

    private static void validateHasArgument(String[] parts, String usage) throws SnoraxException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new SnoraxException("Missing argument.\nUsage: " + usage);
        }
    }

    private static void validateNotEmpty(String value, String errorMessage) throws SnoraxException {
        if (value.trim().isEmpty()) {
            throw new SnoraxException(errorMessage);
        }
    }

    private static void validateNoSpecialDelimiters(String value, String field)
            throws SnoraxException {
        if (value.contains("|")) {
            throw new SnoraxException("The character '|' is not allowed in " + field + ".");
        }
    }

    private static long countOccurrences(String text, String target) {
        int count = 0;
        int idx = 0;
        while ((idx = text.indexOf(target, idx)) != -1) {
            count++;
            idx += target.length();
        }
        return count;
    }

    private static int parseTaskIndex(String indexString, String command) throws SnoraxException {
        String trimmed = indexString.trim();

        // Check for non-numeric input
        if (!trimmed.matches("\\d+")) {
            throw new SnoraxException("'" + trimmed + "' is not a valid task number.\n"
                    + "Usage: " + command + " <task number>");
        }

        try {
            int index = Integer.parseInt(trimmed) - TASK_INDEX_OFFSET;
            if (index < 0) {
                throw new SnoraxException("Task number must be 1 or greater.");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new SnoraxException("Task number is too large. Please enter a valid number.");
        }
    }
}
