package snorax.parser;

import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    // ======================== Basic Commands ========================

    @Test
    public void testParse_exitCommand() throws SnoraxException {
        Command command = Parser.parse("bye");
        assertInstanceOf(ExitCommand.class, command);
        assertTrue(command.isExit());
    }

    @Test
    public void testParse_listCommand() throws SnoraxException {
        Command command = Parser.parse("list");
        assertInstanceOf(ListCommand.class, command);
        assertFalse(command.isExit());
    }

    @Test
    public void testParse_todoCommand() throws SnoraxException {
        Command command = Parser.parse("todo read book");
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    public void testParse_deadlineCommand() throws SnoraxException {
        Command command = Parser.parse("deadline return book /by 2024-12-31 2359");
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    public void testParse_eventCommand() throws SnoraxException {
        Command command = Parser.parse("event meeting /from 2024-12-25 1400 /to 2024-12-25 1600");
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    public void testParse_markCommand() throws SnoraxException {
        Command command = Parser.parse("mark 1");
        assertInstanceOf(MarkCommand.class, command);
    }

    @Test
    public void testParse_unmarkCommand() throws SnoraxException {
        Command command = Parser.parse("unmark 1");
        assertInstanceOf(UnmarkCommand.class, command);
    }

    @Test
    public void testParse_deleteCommand() throws SnoraxException {
        Command command = Parser.parse("delete 1");
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    public void testParse_findCommand() throws SnoraxException {
        Command command = Parser.parse("find book");
        assertInstanceOf(FindCommand.class, command);
    }

    @Test
    public void testParse_sortCommand_noArgs() throws SnoraxException {
        Command command = Parser.parse("sort");
        assertInstanceOf(SortCommand.class, command);
    }

    @Test
    public void testParse_sortCommand_deadline() throws SnoraxException {
        Command command = Parser.parse("sort deadline");
        assertInstanceOf(SortCommand.class, command);
    }

    @Test
    public void testParse_sortCommand_event() throws SnoraxException {
        Command command = Parser.parse("sort event");
        assertInstanceOf(SortCommand.class, command);
    }

    // ======================== Whitespace Handling ========================

    @Test
    public void testParse_leadingTrailingSpaces() throws SnoraxException {
        Command command = Parser.parse("   bye   ");
        assertInstanceOf(ExitCommand.class, command);
    }

    @Test
    public void testParse_multipleSpacesBetweenWords() throws SnoraxException {
        Command command = Parser.parse("todo   read   book");
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    public void testParse_tabCharacter() throws SnoraxException {
        // tabs are whitespace, should be normalised
        Command command = Parser.parse("todo\tread book");
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    public void testParse_caseInsensitiveCommands() throws SnoraxException {
        assertInstanceOf(ExitCommand.class, Parser.parse("BYE"));
        assertInstanceOf(ListCommand.class, Parser.parse("LIST"));
        assertInstanceOf(AddCommand.class, Parser.parse("TODO read book"));
    }

    // ======================== Empty / Null Input ========================

    @Test
    public void testParse_emptyInput_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse(""));
    }

    @Test
    public void testParse_whitespaceOnly_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("   "));
    }

    @Test
    public void testParse_nullInput_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse(null));
    }

    // ======================== Unknown Commands ========================

    @Test
    public void testParse_unknownCommand_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("unknown"));
    }

    @Test
    public void testParse_unknownCommandWithArgs_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("blah blah blah"));
    }

    // ======================== Todo Errors ========================

    @Test
    public void testParse_todoWithoutDescription_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("todo"));
    }

    @Test
    public void testParse_todoWithOnlySpaces_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("todo    "));
    }

    @Test
    public void testParse_todoWithPipeCharacter_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("todo task | extra"));
    }

    // ======================== Deadline Errors ========================

    @Test
    public void testParse_deadlineWithoutDescription_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("deadline"));
    }

    @Test
    public void testParse_deadlineMissingBy_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("deadline submit report"));
    }

    @Test
    public void testParse_deadlineMissingByTime_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("deadline submit report /by"));
    }

    @Test
    public void testParse_deadlineMultipleBy_throwsException() {
        assertThrows(SnoraxException.class,
                () -> Parser.parse("deadline task /by 2024-12-31 2359 /by 2024-12-30 2359"));
    }

    @Test
    public void testParse_deadlineWithPipeCharacter_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("deadline task | extra /by 2024-12-31 2359"));
    }

    // ======================== Event Errors ========================

    @Test
    public void testParse_eventWithoutDescription_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("event"));
    }

    @Test
    public void testParse_eventMissingFrom_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("event meeting /to 2024-12-25 1600"));
    }

    @Test
    public void testParse_eventMissingTo_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("event meeting /from 2024-12-25 1400"));
    }

    @Test
    public void testParse_eventMultipleFrom_throwsException() {
        assertThrows(SnoraxException.class,
                () -> Parser.parse("event meeting /from 2024-12-25 1400 /from 2024-12-25 1500 /to 2024-12-25 1600"));
    }

    @Test
    public void testParse_eventMultipleTo_throwsException() {
        assertThrows(SnoraxException.class,
                () -> Parser.parse("event meeting /from 2024-12-25 1400 /to 2024-12-25 1600 /to 2024-12-25 1700"));
    }

    @Test
    public void testParse_eventMissingFromTime_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("event meeting /from /to 2024-12-25 1600"));
    }

    // ======================== Mark / Unmark / Delete Errors
    // ========================

    @Test
    public void testParse_markWithoutNumber_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("mark"));
    }

    @Test
    public void testParse_markWithNonInteger_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("mark abc"));
    }

    @Test
    public void testParse_markWithNegativeNumber_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("mark -1"));
    }

    @Test
    public void testParse_markWithZero_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("mark 0"));
    }

    @Test
    public void testParse_markWithDecimal_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("mark 1.5"));
    }

    @Test
    public void testParse_unmarkWithoutNumber_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("unmark"));
    }

    @Test
    public void testParse_deleteWithoutNumber_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("delete"));
    }

    @Test
    public void testParse_deleteWithNonInteger_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("delete abc"));
    }

    // ======================== Find Errors ========================

    @Test
    public void testParse_findWithoutKeyword_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("find"));
    }

    @Test
    public void testParse_findWithSpaceOnly_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("find   "));
    }

    // ======================== Sort Errors ========================

    @Test
    public void testParse_sortWithInvalidType_throwsException() {
        assertThrows(SnoraxException.class, () -> Parser.parse("sort invalid"));
    }
}
