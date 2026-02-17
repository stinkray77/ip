package snorax.tasklist;

import java.util.ArrayList;
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
        assert index >= FIRST_TASK_INDEX && index < tasks.size()
                : "Index must be within valid range";
        int sizeBefore = tasks.size();
        Task removed = tasks.remove(index);
        assert tasks.size() == sizeBefore - 1 : "Task should be removed from list";
        assert removed != null : "Removed task should not be null";
        return removed;
    }

    /**
     * Gets a task at the specified index.
     *
     * @param index The index of the task.
     * @return The task at the specified index.
     */
    public Task getTask(int index) {
        assert index >= FIRST_TASK_INDEX && index < tasks.size()
                : "Index must be within valid range";
        Task task = tasks.get(index);
        assert task != null : "Retrieved task should not be null";
        return task;
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
}
