package snorax.command;

import snorax.exception.SnoraxException;
import snorax.storage.Storage;
import snorax.task.Task;
import snorax.tasklist.TaskList;
import snorax.ui.Ui;

/**
 * Represents a command to add a task to the task list.
 * The task is added to the list and saved to storage.
 */
public class AddCommand extends Command {
    private Task task;

    /**
     * Constructs an AddCommand with the specified task.
     *
     * @param task The task to be added to the task list.
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * Executes the add command by adding the task to the task list,
     * saving the updated list to storage, and displaying a confirmation message.
     *
     * @param tasks The task list to add the task to.
     * @param ui The UI to display messages.
     * @param storage The storage to save tasks.
     * @throws SnoraxException if there is an error saving to storage.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnoraxException {
        tasks.addTask(task);
        storage.save(tasks.getTasks());
        ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Indicates whether this command causes the application to exit.
     *
     * @return false, as the add command does not terminate the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}