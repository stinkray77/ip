package snorax.command;

import snorax.exception.SnoraxException;
import snorax.storage.Storage;
import snorax.task.Task;
import snorax.tasklist.TaskList;
import snorax.ui.Ui;

/**
 * Represents a command to delete a task from the task list.
 * The task at the specified index is removed from the list and the changes are saved to storage.
 */
public class DeleteCommand extends Command {
    private int index;

    /**
     * Constructs a DeleteCommand with the specified task index.
     *
     * @param index The index of the task to be deleted (0-based).
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the delete command by removing the task at the specified index,
     * saving the updated list to storage, and displaying a confirmation message.
     *
     * @param tasks The task list to delete the task from.
     * @param ui The UI to display messages.
     * @param storage The storage to save tasks.
     * @throws SnoraxException if the index is invalid or there is an error saving to storage.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnoraxException {
        Task removedTask = tasks.deleteTask(index);
        storage.save(tasks.getTasks());
        ui.showTaskDeleted(removedTask, tasks.size());
    }

    /**
     * Indicates whether this command causes the application to exit.
     *
     * @return false, as the delete command does not terminate the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}