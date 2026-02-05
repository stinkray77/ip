package snorax.command;

import snorax.exception.SnoraxException;
import snorax.storage.Storage;
import snorax.task.Task;
import snorax.tasklist.TaskList;
import snorax.ui.Ui;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private int index;

    /**
     * Constructs a DeleteCommand with the specified task index.
     *
     * @param index The index of the task to delete (0-based).
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the delete command by removing the specified task from the list.
     *
     * @param tasks   The task list containing all tasks.
     * @param ui      The user interface for displaying messages.
     * @param storage The storage handler for saving tasks.
     * @throws SnoraxException If the task index is invalid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnoraxException {
        Task removedTask = tasks.deleteTask(index);
        storage.save(tasks.getTasks());
        ui.showTaskDeleted(removedTask, tasks.size());
    }

    /**
     * Indicates whether this command will exit the application.
     *
     * @return false, as this command does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
