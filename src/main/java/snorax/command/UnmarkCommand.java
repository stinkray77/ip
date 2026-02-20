package snorax.command;

import snorax.exception.SnoraxException;
import snorax.storage.Storage;
import snorax.task.Task;
import snorax.tasklist.TaskList;
import snorax.ui.Ui;

/**
 * Represents a command to mark a task as not done.
 */
public class UnmarkCommand extends Command {
    private int index;

    /**
     * Constructs an UnmarkCommand with the given task index.
     *
     * @param index The zero-based index of the task to unmark.
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws SnoraxException {
        if (tasks.isEmpty()) {
            throw new SnoraxException("There are no tasks to unmark.");
        }
        if (index >= tasks.size()) {
            throw new SnoraxException("Task " + (index + 1) + " does not exist.\n"
                    + "You have " + tasks.size() + " task(s). "
                    + "Please enter a number between 1 and " + tasks.size() + ".");
        }

        Task task = tasks.getTask(index);

        if (!task.isDone()) {
            throw new SnoraxException("Task " + (index + 1) + " is already marked as not done:\n"
                    + task);
        }

        task.markAsNotDone();
        storage.save(tasks.getTasks());

        return "OK, I've marked this task as not done yet:\n  " + task;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
