package snorax.ui;

import java.util.Scanner;
import snorax.task.Task;
import snorax.tasklist.TaskList;

public class Ui {
    private static final String LINE = "____________________________________________________________";
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println(LINE + "\n"
                + "Hello! I'm Snorax\n"
                + "What can I do for you?\n"
                + LINE + "\n");
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public void showGoodbye() {
        System.out.println("Goodnight. Time for a nap!");
        showLine();
    }

    public void showError(String message) {
        System.out.println(message);
        showLine();
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("ok added this task liao:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        showLine();
    }

    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("done bro removed task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        showLine();
    }

    public void showTaskMarked(Task task) {
        System.out.println("good job, marked as done:");
        System.out.println("  " + task);
        showLine();
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("ok marked as undone u lazy:");
        System.out.println("  " + task);
        showLine();
    }

    public void showTaskList(TaskList tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.getTask(i));
        }
        showLine();
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }
}