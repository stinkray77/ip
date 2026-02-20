package snorax.command;

import snorax.exception.SnoraxException;
import snorax.storage.Storage;
import snorax.task.Task;
import snorax.tasklist.TaskList;
import snorax.ui.Ui;

/**
 * Represents a command to mark a task as done.
 */
public class MarkCommand extends Command {
    private int index;

    /**
     * Constructs a MarkCommand with the given task index.
     *
     * @param index The zero-based index of the task to mark.
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws SnoraxException {
        if (tasks.isEmpty()) {
            throw new SnoraxException("There are no tasks to mark.");
        }
        if (index >= tasks.size()) {
            throw new SnoraxException("Task " + (index + 1) + " does not exist.\n"
                    + "You have " + tasks.size() + " task(s). "
                    + "Please enter a number between 1 and " + tasks.size() + ".");
        }

        Task task = tasks.getTask(index);

        if (task.isDone()) {
            throw new SnoraxException("Task " + (index + 1) + " is already marked as done:\n"
                    + task);
        }

        task.markAsDone();
        storage.save(tasks.getTasks());

        return "Nice! I've marked this task as done:\n  " + task;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
