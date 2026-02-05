package snorax.command;

import snorax.storage.Storage;
import snorax.tasklist.TaskList;
import snorax.ui.Ui;

/**
 * Represents a command to list all tasks in the task list.
 * Displays all tasks with their status and details to the user.
 */
public class ListCommand extends Command {
    
    /**
     * Executes the list command by displaying all tasks in the task list.
     *
     * @param tasks The task list to display.
     * @param ui The UI to display the task list.
     * @param storage The storage (not used in this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks);
    }

    /**
     * Indicates whether this command causes the application to exit.
     *
     * @return false, as the list command does not terminate the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}