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
        System.out.println(LINE + "\n"
                + "Hello! I'm Snorax\n"
                + "What can I do for you?\n"
                + LINE + "\n");
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
        System.out.println("Goodnight. Time for a nap!");
        showLine();
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println(message);
        showLine();
    }

    /**
     * Displays a message indicating a task has been added.
     *
     * @param task      The task that was added.
     * @param taskCount The total number of tasks after adding.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("ok added this task liao:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        showLine();
    }

    /**
     * Displays a message indicating a task has been deleted.
     *
     * @param task      The task that was deleted.
     * @param taskCount The total number of tasks after deletion.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("done bro removed task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        showLine();
    }

    /**
     * Displays a message indicating a task has been marked as done.
     *
     * @param task The task that was marked.
     */
    public void showTaskMarked(Task task) {
        System.out.println("good job, marked as done:");
        System.out.println("  " + task);
        showLine();
    }

    /**
     * Displays a message indicating a task has been unmarked.
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("ok marked as undone u lazy:");
        System.out.println("  " + task);
        showLine();
    }

    /**
     * Displays the list of all tasks.
     *
     * @param tasks The TaskList containing all tasks.
     */
    public void showTaskList(TaskList tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.getTask(i));
        }
        showLine();
    }

    /**
     * Displays the list of tasks matching a search.
     *
     * @param matchingTasks The list of tasks that match the search criteria.
     */
    public void showFoundTasks(ArrayList<Task> matchingTasks) {
        if (matchingTasks.isEmpty()) {
            System.out.println("No matching tasks found bro");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println((i + 1) + ". " + matchingTasks.get(i));
            }
        }
        showLine();
    }

    /**
     * Reads a command from the user.
     *
     * @return The user's input as a string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Closes the scanner resource.
     */
    public void close() {
        scanner.close();
    }
}
