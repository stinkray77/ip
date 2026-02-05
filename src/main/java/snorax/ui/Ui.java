package snorax.ui;

import java.util.Scanner;
import snorax.task.Task;
import snorax.tasklist.TaskList;

/**
 * Handles user interface operations including input and output.
 * Manages interactions between the user and the application.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    private Scanner scanner;

    /**
     * Constructs a new Ui instance.
     * Initializes the scanner for reading user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message when the application starts.
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
     * Displays the goodbye message when the application exits.
     */
    public void showGoodbye() {
        System.out.println("Goodnight. Time for a nap!");
        showLine();
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println(message);
        showLine();
    }

    /**
     * Displays a message confirming that a task has been added.
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
     * Displays a message confirming that a task has been deleted.
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
     * Displays a message confirming that a task has been marked as done.
     *
     * @param task The task that was marked.
     */
    public void showTaskMarked(Task task) {
        System.out.println("good job, marked as done:");
        System.out.println("  " + task);
        showLine();
    }

    /**
     * Displays a message confirming that a task has been unmarked.
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
     * @param tasks The TaskList containing all tasks to display.
     */
    public void showTaskList(TaskList tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.getTask(i));
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
     * Closes the scanner to release system resources.
     */
    public void close() {
        scanner.close();
    }
}
