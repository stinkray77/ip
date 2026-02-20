package snorax.ui;

import java.util.ArrayList;
import java.util.Scanner;

import snorax.task.Task;
import snorax.tasklist.TaskList;

/**
 * Handles user interface interactions including displaying messages and reading
 * input.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    private static final String LOGO = "  _________                               \n"
            + " /   _____/ ____   ________________  ___  ___\n"
            + " \\_____  \\ /    \\ /  _ \\_  __ \\__  \\ \\  \\/  /\n"
            + " /        \\   |  (  <_> )  | \\// __ \\_>    < \n"
            + "/_______  /___|  /\\____/|__|  (____  /__/\\_ \\\n"
            + "        \\/     \\/                   \\/      \\/";
    private static final String WELCOME_MESSAGE = "Hello! I'm Snorax\nWhat can I do for you?";
    private static final String GOODBYE_MESSAGE = "Goodnight. Time for a nap!";
    private static final String TASKS_HEADER = "Here are the tasks in your list:";
    private static final String EMPTY_LIST_MESSAGE = "Your task list is empty!";
    private static final String MATCHING_TASKS_HEADER = "Here are the matching tasks in your list:";
    private static final String NO_MATCHING_TASKS = "No matching tasks found.";
    private static final String TASK_ADDED_PREFIX = "Got it. I've added this task:";
    private static final String TASK_DELETED_PREFIX = "Noted. I've removed this task:";
    private static final String TASK_MARKED_PREFIX = "Nice! I've marked this task as done:";
    private static final String TASK_UNMARKED_PREFIX = "OK, I've marked this task as not done yet:";
    private static final String TASKS_COUNT_FORMAT = "Now you have %d tasks in the list.";
    private static final int TASK_NUMBER_OFFSET = 1;

    private Scanner scanner;

    /**
     * Constructs a new Ui instance and initializes the scanner for user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message.
     */
    public void showWelcome() {
        showLine();
        System.out.println(LOGO);
        System.out.println(WELCOME_MESSAGE);
        showLine();
    }

    /**
     * Displays a horizontal line separator.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Displays the goodbye message.
     */
    public void showGoodbye() {
        System.out.println(GOODBYE_MESSAGE);
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        assert message != null : "Error message cannot be null";
        System.out.println("Error: " + message);
    }

    /**
     * Reads a command from the user.
     *
     * @return The user's input as a string.
     */
    public String readCommand() {
        assert scanner != null : "Scanner should be initialized";
        return scanner.nextLine();
    }

    /**
     * Displays the task list.
     *
     * @param tasks The task list to display.
     */
    public void showTaskList(TaskList tasks) {
        assert tasks != null : "Task list cannot be null";

        if (tasks.isEmpty()) {
            System.out.println(EMPTY_LIST_MESSAGE);
            return;
        }

        System.out.println(TASKS_HEADER);
        printTasksWithNumbers(tasks.getTasks());
    }

    /**
     * Displays a message when a task is added.
     *
     * @param task       The task that was added.
     * @param totalTasks The total number of tasks after adding.
     */
    public void showTaskAdded(Task task, int totalTasks) {
        assert task != null : "Task cannot be null";
        assert totalTasks > 0 : "Total tasks must be positive";

        System.out.println(TASK_ADDED_PREFIX);
        System.out.println("  " + task);
        printTaskCount(totalTasks);
    }

    /**
     * Displays a message when a task is deleted.
     *
     * @param task       The task that was deleted.
     * @param totalTasks The total number of tasks after deletion.
     */
    public void showTaskDeleted(Task task, int totalTasks) {
        assert task != null : "Task cannot be null";
        assert totalTasks >= 0 : "Total tasks cannot be negative";

        System.out.println(TASK_DELETED_PREFIX);
        System.out.println("  " + task);
        printTaskCount(totalTasks);
    }

    /**
     * Displays a message when a task is marked as done.
     *
     * @param task The task that was marked.
     */
    public void showTaskMarked(Task task) {
        assert task != null : "Task cannot be null";

        System.out.println(TASK_MARKED_PREFIX);
        System.out.println("  " + task);
    }

    /**
     * Displays a message when a task is unmarked.
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        assert task != null : "Task cannot be null";

        System.out.println(TASK_UNMARKED_PREFIX);
        System.out.println("  " + task);
    }

    /**
     * Displays the list of tasks that match a search keyword.
     *
     * @param matchingTasks The list of tasks that match the search.
     */
    public void showFoundTasks(ArrayList<Task> matchingTasks) {
        assert matchingTasks != null : "Matching tasks list cannot be null";

        if (matchingTasks.isEmpty()) {
            System.out.println(NO_MATCHING_TASKS);
            return;
        }

        System.out.println(MATCHING_TASKS_HEADER);
        printTasksWithNumbers(matchingTasks);
    }

    /**
     * Prints a list of tasks with numbers.
     *
     * @param tasks The list of tasks to print.
     */
    private void printTasksWithNumbers(ArrayList<Task> tasks) {
        assert tasks != null : "Tasks list cannot be null";

        for (int i = 0; i < tasks.size(); i++) {
            int taskNumber = i + TASK_NUMBER_OFFSET;
            System.out.println(taskNumber + ". " + tasks.get(i));
        }
    }

    /**
     * Prints the total number of tasks.
     *
     * @param count The number of tasks.
     */
    private void printTaskCount(int count) {
        assert count >= 0 : "Task count cannot be negative";
        System.out.println(String.format(TASKS_COUNT_FORMAT, count));
    }

    /**
     * Closes the scanner.
     */
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
