package snorax.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import snorax.exception.SnoraxException;
import snorax.task.Deadline;
import snorax.task.Event;
import snorax.task.Task;
import snorax.task.Todo;

/**
 * Handles loading and saving of tasks to a file.
 * Manages file I/O operations for persistent task storage.
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a Storage instance with the specified file path.
     *
     * @param filePath The path to the file for storing tasks.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file.
     * Creates the file if it does not exist.
     * Skips corrupted lines and continues loading valid tasks.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws SnoraxException if there is an error reading the file.
     */
    public ArrayList<Task> load() throws SnoraxException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        try {
            file.getParentFile().mkdirs();
            if (!file.exists()) {
                file.createNewFile();
                return tasks;
            }
        } catch (IOException e) {
            throw new SnoraxException("Error creating data file");
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    Task task = parseTask(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                } catch (Exception e) {
                    System.out.println("Warning: Skipping corrupted data: " + line);
                }
            }
        } catch (IOException e) {
            throw new SnoraxException("Error reading from file");
        }

        return tasks;
    }

    /**
     * Parses a line from the file and converts it to a Task object.
     * Supports parsing of Todo, Deadline, and Event tasks.
     *
     * @param line The line to parse.
     * @return The Task object created from the line, or null if the line format is invalid.
     * @throws SnoraxException if the line format is invalid.
     */
    private Task parseTask(String line) throws SnoraxException {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) return null;

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (parts.length < 4) return null;
                task = new Deadline(description, parts[3]);
                break;
            case "E":
                if (parts.length < 5) return null;
                task = new Event(description, parts[3], parts[4]);
                break;
            default:
                return null;
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    /**
     * Saves the list of tasks to the file.
     * Overwrites the existing file content.
     *
     * @param tasks The list of tasks to save.
     * @throws SnoraxException if there is an error writing to the file.
     */
    public void save(ArrayList<Task> tasks) throws SnoraxException {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Task task : tasks) {
                writer.write(formatTask(task) + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new SnoraxException("Error saving to file");
        }
    }

    /**
     * Formats a task for storage in the file.
     * Converts the task to a pipe-separated string format.
     *
     * @param task The task to format.
     * @return The formatted string representation of the task.
     */
    private String formatTask(Task task) {
        String type;
        String extraInfo = "";

        if (task instanceof Todo) {
            type = "T";
        } else if (task instanceof Deadline) {
            type = "D";
            extraInfo = " | " + ((Deadline) task).getBy();
        } else if (task instanceof Event) {
            type = "E";
            extraInfo = " | " + ((Event) task).getFrom() + " | " + ((Event) task).getTo();
        } else {
            type = "T";
        }

        String isDone = task.isDone() ? "1" : "0";
        return type + " | " + isDone + " | " + task.getDescription() + extraInfo;
    }
}
