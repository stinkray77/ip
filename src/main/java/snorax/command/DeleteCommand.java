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
     * Constructs a DeleteCommand with the given task index.
     *
     * @param index The zero-based index of the task to delete.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws SnoraxException {
        if (tasks.isEmpty()) {
            throw new SnoraxException("There are no tasks to delete.");
        }
        if (index >= tasks.size()) {
            throw new SnoraxException("Task " + (index + 1) + " does not exist.\n"
                    + "You have " + tasks.size() + " task(s). "
                    + "Please enter a number between 1 and " + tasks.size() + ".");
        }

        Task removed = tasks.deleteTask(index);

        try {
            storage.save(tasks.getTasks());
        } catch (SnoraxException e) {
            // Roll back deletion if save fails
            tasks.addTask(removed);
            throw new SnoraxException("Failed to save after deletion: " + e.getMessage()
                    + "\nTask was not deleted.");
        }

        return "Noted. I've removed this task:\n  " + removed
                + "\nNow you have " + tasks.size() + " task(s) in the list.";
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
