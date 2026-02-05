package snorax.command;

import snorax.storage.Storage;
import snorax.tasklist.TaskList;
import snorax.ui.Ui;

/**
 * Represents a command to exit the application.
 * Displays a goodbye message and terminates the program.
 */
public class ExitCommand extends Command {
    
    /**
     * Executes the exit command by displaying a goodbye message
     * and closing the UI.
     *
     * @param tasks The task list (not used in this command).
     * @param ui The UI to display the goodbye message and close.
     * @param storage The storage (not used in this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
        ui.close();
    }

    /**
     * Indicates whether this command causes the application to exit.
     *
     * @return true, as the exit command terminates the application.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}