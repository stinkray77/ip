package snorax.command;

import snorax.exception.SnoraxException;
import snorax.storage.Storage;
import snorax.tasklist.TaskList;
import snorax.ui.Ui;

/**
 * Represents an abstract command that can be executed.
 * All specific command types should extend this class.
 */
public abstract class Command {

    /**
     * Executes the command.
     *
     * @param tasks   The task list containing all tasks.
     * @param ui      The user interface for displaying messages.
     * @param storage The storage handler for saving tasks.
     * @throws SnoraxException If an error occurs during execution.
     */
    public abstract String execute(TaskList tasks, Ui ui, Storage storage) throws SnoraxException;

    /**
     * Indicates whether this command will exit the application.
     *
     * @return true if the command exits the application, false otherwise.
     */
    public abstract boolean isExit();
}
