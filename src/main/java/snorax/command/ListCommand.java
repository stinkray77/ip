package snorax.command;

import snorax.storage.Storage;
import snorax.tasklist.TaskList;
import snorax.ui.Ui;

import java.util.stream.IntStream;
import java.util.stream.Collectors;

/**
 * Represents a command to list all tasks.
 */
public class ListCommand extends Command {
    private static final int TASK_NUMBER_OFFSET = 1;
    private static final String EMPTY_LIST_MESSAGE = "You have no tasks in your list.";
    private static final String HEADER = "Here are the tasks in your list:\n";

    /**
     * Executes the list command by displaying all tasks.
     *
     * @param tasks   The task list containing all tasks.
     * @param ui      The user interface for displaying messages.
     * @param storage The storage handler for saving tasks.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks);

        if (tasks.isEmpty()) {
            return EMPTY_LIST_MESSAGE;
        }

        String result = IntStream.range(0, tasks.size())
                .mapToObj(i -> (i + TASK_NUMBER_OFFSET) + ". " + tasks.getTask(i))
                .collect(Collectors.joining("\n", HEADER, ""));

        return result.trim();
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