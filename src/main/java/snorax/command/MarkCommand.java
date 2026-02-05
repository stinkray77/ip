package snorax.command;

import snorax.exception.SnoraxException;
import snorax.storage.Storage;
import snorax.task.Task;
import snorax.tasklist.TaskList;
import snorax.ui.Ui;

/**
 * Represents a command to mark a task as done.
 * The task at the specified index is marked as completed and the changes are saved to storage.
 */
public class MarkCommand extends Command {
    private int index;

    /**
     * Constructs a MarkCommand with the specified task index.
     *
     * @param index The index of the task to be marked as done (0-based).
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the mark command by marking the task at the specified index as done,
     * saving the updated list to storage, and displaying a confirmation message.
     *
     * @param tasks The task list containing the task to mark.
     * @param ui The UI to display messages.
     * @param storage The storage to save tasks.
     * @throws SnoraxException if the index is invalid or there is an error saving to storage.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnoraxException {
        Task task = tasks.getTask(index);
        task.markAsDone();
        storage.save(tasks.getTasks());
        ui.showTaskMarked(task);
    }

    /**
     * Indicates whether this command causes the application to exit.
     *
     * @return false, as the mark command does not terminate the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}