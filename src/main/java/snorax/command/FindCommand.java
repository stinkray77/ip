package snorax.command;

import snorax.storage.Storage;
import snorax.task.Task;
import snorax.tasklist.TaskList;
import snorax.ui.Ui;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents a command to find tasks containing a specific keyword.
 */
public class FindCommand extends Command {
    private static final int TASK_NUMBER_OFFSET = 1;

    private String keyword;

    /**
     * Constructs a FindCommand with the specified keyword.
     *
     * @param keyword The keyword to search for in task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the find command by searching for tasks that contain the keyword
     * and displaying the matching tasks to the user.
     *
     * @param tasks   The task list to search in.
     * @param ui      The UI to display messages.
     * @param storage The storage (not used in this command).
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> matchingTasks = tasks.getTasks().stream()
                .filter(task -> task.getDescription().toLowerCase()
                        .contains(keyword.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new));

        if (matchingTasks.isEmpty()) {
            return "No matching tasks found bro";
        }

        String result = IntStream.range(0, matchingTasks.size())
                .mapToObj(i -> (i + TASK_NUMBER_OFFSET) + ". " + matchingTasks.get(i))
                .collect(Collectors.joining("\n",
                        "Here are the matching tasks in your list:\n", ""));

        ui.showFoundTasks(matchingTasks);
        return result.trim();
    }

    /**
     * Indicates whether this command will cause the application to exit.
     *
     * @return false, as this command does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
