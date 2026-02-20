package snorax.command;

import snorax.exception.SnoraxException;
import snorax.storage.Storage;
import snorax.task.Deadline;
import snorax.task.Event;
import snorax.task.Task;
import snorax.tasklist.TaskList;
import snorax.ui.Ui;

/**
 * Represents a command to add a task to the task list.
 */
public class AddCommand extends Command {
    private Task task;

    /**
     * Constructs an AddCommand with the given task.
     *
     * @param task The task to add.
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws SnoraxException {
        // Validate date/time before adding
        try {
            if (task instanceof Deadline) {
                Deadline d = (Deadline) task;
                Deadline.validate(d.getBy());
            } else if (task instanceof Event) {
                Event e = (Event) task;
                Event.validate(e.getFrom(), e.getTo());
            }
        } catch (IllegalArgumentException e) {
            throw new SnoraxException(e.getMessage());
        }

        // Check for duplicate tasks
        for (int i = 0; i < tasks.size(); i++) {
            Task existing = tasks.getTask(i);
            if (existing.toString().equals(task.toString())) {
                throw new SnoraxException(
                        "A duplicate task already exists at position " + (i + 1) + ":\n"
                                + existing + "\nPlease add a different task.");
            }
        }

        tasks.addTask(task);

        try {
            storage.save(tasks.getTasks());
        } catch (SnoraxException e) {
            // Roll back the add if save fails
            tasks.deleteTask(tasks.size() - 1);
            throw new SnoraxException("Failed to save task: " + e.getMessage()
                    + "\nTask was not added.");
        }

        return "Got it. I've added this task:\n  " + task
                + "\nNow you have " + tasks.size() + " task(s) in the list.";
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
