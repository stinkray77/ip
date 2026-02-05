public class Parser {
    public static Command parseCommand (String input) {
        return Command.parse(input);
    }

    public static int parseTaskIndex(String input) throws SnoraxException {
        String[] parts = input.split(" ");

        if (parts.length < 2) {
            throw new SnoraxException("give valid index");
        }
        try {
            return Integer.parseInt(parts[1] - 1); // Convert to 0-index
        } catch (NumberFormatException e) {
            throw new SnoraxException("give valid index");
        }
    }

    public static String parseTodoDescription(String input) throws SnoraxException {
        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new SnoraxException("cannot be empty bro");
        }
        return description;
    }

    public static String[] parseDeadline(String input) throws SnoraxException {
        String[] parts = input.substring(9).split(" /by ");
        if (parts.length < 2 || parts[0].trim().isEmpty()) {
            throw new SnoraxException("cannot be empty bro");
        }
        return new String[]{parts[0].trim(), parts[1].trim()};
    }

    public static String[] parseEvent(String input) throws SnoraxException {
        String remaining = input.substring(6);
        String[] eventParts = remaining.split(" /from | /to ");
        if (eventParts.length < 3 || eventParts[0].trim().isEmpty()) {
            throw new SnoraxException("cannot be empty bro");
        }
        return new String[]{eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim()};
    }
}