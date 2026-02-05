package snorax.command;

import snorax.exception.SnoraxException;
import snorax.storage.Storage;
import snorax.tasklist.TaskList;
import snorax.ui.Ui;

/**
 * Represents an executable command in the Snorax application.
 * This is an abstract class that all specific command types extend.
 */
public abstract class Command {
    
    /**
     * Executes the command with the given task list, UI, and storage.
     *
     * @param tasks The task list to operate on.
     * @param ui The UI to interact with the user.
     * @param storage The storage to save or load tasks.
     * @throws SnoraxException if there is an error during execution.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws SnoraxException;

    /**
     * Indicates whether this command causes the application to exit.
     *
     * @return true if the application should exit after this command, false otherwise.
     */
    public abstract boolean isExit();
}


