package snorax.command;

import snorax.exception.SnoraxException;
import snorax.storage.Storage;
import snorax.tasklist.TaskList;
import snorax.ui.Ui;

/**
 * Represents a command to sort tasks in the task list.
 */
public class SortCommand extends Command {
    private static final String SORT_TYPE_ALL = "all";
    private static final String SORT_TYPE_DEADLINE = "deadline";
    private static final String SORT_TYPE_EVENT = "event";
    private static final String DEFAULT_SORT_TYPE = SORT_TYPE_ALL;

    private String sortType;

    /**
     * Constructs a SortCommand with the default sort type (all tasks).
     */
    public SortCommand() {
        this.sortType = DEFAULT_SORT_TYPE;
    }

    /**
     * Constructs a SortCommand with the specified sort type.
     *
     * @param sortType The type of sorting to perform (all/deadline/event).
     */
    public SortCommand(String sortType) {
        this.sortType = sortType != null ? sortType.toLowerCase() : DEFAULT_SORT_TYPE;
    }

    /**
     * Executes the sort command by sorting the task list based on the sort type.
     *
     * @param tasks   The task list to sort.
     * @param ui      The UI to display messages.
     * @param storage The storage to save the sorted task list.
     * @throws SnoraxException If there is an error saving to storage.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws SnoraxException {
        assert tasks != null : "Task list cannot be null";

        if (tasks.isEmpty()) {
            return "No tasks to sort!";
        }

        switch (sortType) {
            case SORT_TYPE_ALL:
                tasks.sortTasks();
                storage.save(tasks.getTasks());
                return "All tasks have been sorted chronologically!\n"
                        + "(Deadlines first, then Events, then Todos)";

            case SORT_TYPE_DEADLINE:
                tasks.sortDeadlines();
                storage.save(tasks.getTasks());
                return "Deadlines have been sorted chronologically!";

            case SORT_TYPE_EVENT:
                tasks.sortEvents();
                storage.save(tasks.getTasks());
                return "Events have been sorted chronologically!";

            default:
                throw new SnoraxException("Invalid sort type! Use: sort, sort deadline, or sort event");
        }
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
