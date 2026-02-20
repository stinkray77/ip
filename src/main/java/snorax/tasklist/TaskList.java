package snorax.tasklist;

import java.util.ArrayList;
import java.util.Comparator;

import snorax.task.Deadline;
import snorax.task.Event;
import snorax.task.Task;

/**
 * Represents a list of tasks.
 * Provides operations to add, delete, and retrieve tasks.
 */
public class TaskList {
    private static final int FIRST_TASK_INDEX = 0;
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
        assert tasks != null : "Tasks list should be initialized";
    }

    /**
     * Constructs a TaskList with the given list of tasks.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        assert tasks != null : "Initial tasks list cannot be null";
        this.tasks = tasks;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        assert task != null : "Cannot add null task";
        int sizeBefore = tasks.size();
        tasks.add(task);
        assert tasks.size() == sizeBefore + 1 : "Task should be added to list";
    }

    /**
     * Deletes a task at the specified index.
     *
     * @param index The index of the task to delete.
     * @return The deleted task.
     */
    public Task deleteTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Index must be within valid range");
        }
        return tasks.remove(index);
    }

    /**
     * Gets a task at the specified index.
     *
     * @param index The index of the task.
     * @return The task at the specified index.
     */
    public Task getTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Index must be within valid range");
        }
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the internal task list.
     *
     * @return The ArrayList of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Checks if the task list is empty.
     *
     * @return true if the list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Sorts all tasks by type and chronologically.
     * Deadlines are sorted by their due date, Events by start time, Todos appear
     * last.
     */
    public void sortTasks() {
        tasks.sort(Comparator
                .comparing(this::getTaskPriority)
                .thenComparing(this::getTaskDateTime));
    }

    /**
     * Sorts only deadline tasks chronologically and keeps other tasks in original
     * order.
     */
    public void sortDeadlines() {
        ArrayList<Task> sorted = new ArrayList<>();
        ArrayList<Task> nonDeadlines = new ArrayList<>();

        // Separate deadlines from other tasks
        for (Task task : tasks) {
            if (task instanceof Deadline) {
                sorted.add(task);
            } else {
                nonDeadlines.add(task);
            }
        }

        // Sort deadlines chronologically
        sorted.sort(Comparator.comparing(task -> ((Deadline) task).getByDateTime()));

        // Add non-deadline tasks back
        sorted.addAll(nonDeadlines);

        this.tasks = sorted;
    }

    /**
     * Sorts only event tasks chronologically by start time.
     */
    public void sortEvents() {
        ArrayList<Task> sorted = new ArrayList<>();
        ArrayList<Task> nonEvents = new ArrayList<>();

        for (Task task : tasks) {
            if (task instanceof Event) {
                sorted.add(task);
            } else {
                nonEvents.add(task);
            }
        }

        sorted.sort(Comparator.comparing(task -> ((Event) task).getFromDateTime()));
        sorted.addAll(nonEvents);

        this.tasks = sorted;
    }

    /**
     * Gets the priority of a task for sorting.
     * Deadlines have highest priority (0), Events next (1), Todos last (2).
     *
     * @param task The task to get priority for.
     * @return The priority value.
     */
    private int getTaskPriority(Task task) {
        if (task instanceof Deadline) {
            return 0;
        } else if (task instanceof Event) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * Gets the comparable date/time of a task for sorting.
     *
     * @param task The task to get date/time from.
     * @return The task's date/time or a far future date for todos.
     */
    private Comparable getTaskDateTime(Task task) {
        if (task instanceof Deadline) {
            return ((Deadline) task).getByDateTime();
        } else if (task instanceof Event) {
            return ((Event) task).getFromDateTime();
        } else {
            // Todos don't have dates, so they go to the end
            return java.time.LocalDateTime.MAX;
        }
    }
}
