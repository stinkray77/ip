package snorax.parser;

import org.junit.jupiter.api.Test;
import snorax.command.*;
import snorax.exception.SnoraxException;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void testParse_exitCommand() throws SnoraxException {
        Command command = Parser.parse("bye");
        assertNotNull(command);
        assertTrue(command instanceof ExitCommand);
        assertTrue(command.isExit());
    }

    @Test
    public void testParse_listCommand() throws SnoraxException {
        Command command = Parser.parse("list");
        assertNotNull(command);
        assertTrue(command instanceof ListCommand);
        assertFalse(command.isExit());
    }

    @Test
    public void testParse_todoCommand() throws SnoraxException {
        Command command = Parser.parse("todo read book");
        assertNotNull(command);
        assertTrue(command instanceof AddCommand);
        assertFalse(command.isExit());
    }

    @Test
    public void testParse_deadlineCommand() throws SnoraxException {
        Command command = Parser.parse("deadline return book /by 2024-12-31");
        assertNotNull(command);
        assertTrue(command instanceof AddCommand);
    }

    @Test
    public void testParse_eventCommand() throws SnoraxException {
        Command command = Parser.parse("event project meeting /from 2024-12-25 /to 2024-12-26");
        assertNotNull(command);
        assertTrue(command instanceof AddCommand);
    }

    @Test
    public void testParse_markCommand() throws SnoraxException {
        Command command = Parser.parse("mark 1");
        assertNotNull(command);
        assertTrue(command instanceof MarkCommand);
    }

    @Test
    public void testParse_unmarkCommand() throws SnoraxException {
        Command command = Parser.parse("unmark 1");
        assertNotNull(command);
        assertTrue(command instanceof UnmarkCommand);
    }

    @Test
    public void testParse_deleteCommand() throws SnoraxException {
        Command command = Parser.parse("delete 1");
        assertNotNull(command);
        assertTrue(command instanceof DeleteCommand);
    }

    @Test
    public void testParse_emptyInput() {
        assertThrows(SnoraxException.class, () -> Parser.parse(""));
    }

    @Test
    public void testParse_unknownCommand() {
        assertThrows(SnoraxException.class, () -> Parser.parse("unknown command"));
    }

    @Test
    public void testParse_todoWithoutDescription() {
        assertThrows(SnoraxException.class, () -> Parser.parse("todo"));
    }

    @Test
    public void testParse_deadlineWithoutDescription() {
        assertThrows(SnoraxException.class, () -> Parser.parse("deadline"));
    }

    @Test
    public void testParse_eventWithoutDescription() {
        assertThrows(SnoraxException.class, () -> Parser.parse("event"));
    }
}