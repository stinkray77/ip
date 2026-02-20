package snorax.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.util.ArrayList;

import snorax.exception.SnoraxException;
import snorax.task.Deadline;
import snorax.task.Event;
import snorax.task.Task;
import snorax.task.Todo;

/**
 * Handles loading and saving of tasks to a file.
 */
public class Storage {
    private static final String TASK_TYPE_TODO = "T";
    private static final String TASK_TYPE_DEADLINE = "D";
    private static final String TASK_TYPE_EVENT = "E";
    private static final String STATUS_DONE = "1";
    private static final String DELIMITER = " \\| ";
    private static final String WRITE_DELIMITER = " | ";
    private static final int MIN_PARTS_TODO = 3;
    private static final int MIN_PARTS_DEADLINE = 4;
    private static final int MIN_PARTS_EVENT = 5;
    private static final int INDEX_TYPE = 0;
    private static final int INDEX_STATUS = 1;
    private static final int INDEX_DESC = 2;
    private static final int INDEX_BY = 3;
    private static final int INDEX_FROM = 3;
    private static final int INDEX_TO = 4;

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
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws SnoraxException If there is an error reading the file.
     */
    public ArrayList<Task> load() throws SnoraxException {
        File file = new File(filePath);

        if (!file.exists()) {
            createFileAndDirectories(file);
            return new ArrayList<>();
        }

        if (!file.canRead()) {
            throw new SnoraxException("Cannot read data file: " + filePath
                    + "\nPlease check file permissions.");
        }

        ArrayList<Task> tasks = new ArrayList<>();
        int lineNumber = 0;

        try {
            for (String line : Files.readAllLines(file.toPath())) {
                lineNumber++;
                if (line.trim().isEmpty()) {
                    continue;
                }
                Task task = parseTaskFromLine(line, lineNumber);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (AccessDeniedException e) {
            throw new SnoraxException("Access denied to data file: " + filePath);
        } catch (IOException e) {
            throw new SnoraxException("Error reading data file: " + e.getMessage());
        }

        return tasks;
    }

    private void createFileAndDirectories(File file) throws SnoraxException {
        try {
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
                throw new SnoraxException("Could not create directory: " + parentDir.getPath());
            }
            if (!file.createNewFile()) {
                throw new SnoraxException("Could not create data file: " + filePath);
            }
        } catch (AccessDeniedException e) {
            throw new SnoraxException("Access denied when creating data file: " + filePath
                    + "\nPlease check folder permissions.");
        } catch (IOException e) {
            throw new SnoraxException("Error creating data file: " + e.getMessage());
        }
    }

    private Task parseTaskFromLine(String line, int lineNumber) {
        String[] parts = line.split(DELIMITER);
        String context = " (line " + lineNumber + ": \"" + line + "\")";

        if (parts.length < MIN_PARTS_TODO) {
            System.err.println("Skipping corrupted entry" + context);
            return null;
        }

        String type = parts[INDEX_TYPE].trim();
        String statusStr = parts[INDEX_STATUS].trim();
        String description = parts[INDEX_DESC].trim();

        if (!statusStr.equals("0") && !statusStr.equals("1")) {
            System.err.println("Invalid status '" + statusStr + "'" + context + " — skipping.");
            return null;
        }

        boolean isDone = statusStr.equals(STATUS_DONE);

        if (description.isEmpty()) {
            System.err.println("Empty description" + context + " — skipping.");
            return null;
        }

        Task task = createTask(type, description, parts, context);
        if (task != null && isDone) {
            task.markAsDone();
        }
        return task;
    }

    private Task createTask(String type, String description, String[] parts, String context) {
        switch (type) {
            case TASK_TYPE_TODO:
                return new Todo(description);
            case TASK_TYPE_DEADLINE:
                if (parts.length < MIN_PARTS_DEADLINE) {
                    System.err.println("Missing deadline time" + context + " — skipping.");
                    return null;
                }
                return new Deadline(description, parts[INDEX_BY].trim());
            case TASK_TYPE_EVENT:
                if (parts.length < MIN_PARTS_EVENT) {
                    System.err.println("Missing event from/to times" + context + " — skipping.");
                    return null;
                }
                return new Event(description, parts[INDEX_FROM].trim(), parts[INDEX_TO].trim());
            default:
                System.err.println("Unknown task type '" + type + "'" + context + " — skipping.");
                return null;
        }
    }

    /**
     * Saves the task list to the file.
     *
     * @param tasks The list of tasks to save.
     * @throws SnoraxException If there is an error writing to the file.
     */
    public void save(ArrayList<Task> tasks) throws SnoraxException {
        File file = new File(filePath);

        if (file.exists() && !file.canWrite()) {
            throw new SnoraxException("Cannot write to data file: " + filePath
                    + "\nPlease check file permissions.");
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            for (Task task : tasks) {
                writer.write(formatTask(task) + System.lineSeparator());
            }
        } catch (AccessDeniedException e) {
            throw new SnoraxException("Access denied when saving: " + filePath);
        } catch (IOException e) {
            throw new SnoraxException("Error saving tasks: " + e.getMessage());
        }
    }

    private String formatTask(Task task) {
        StringBuilder sb = new StringBuilder();
        String type = task instanceof Todo ? TASK_TYPE_TODO
                : task instanceof Deadline ? TASK_TYPE_DEADLINE
                        : TASK_TYPE_EVENT;
        String status = task.isDone() ? STATUS_DONE : "0";

        sb.append(type).append(WRITE_DELIMITER)
                .append(status).append(WRITE_DELIMITER)
                .append(task.getDescription());

        if (task instanceof Deadline) {
            sb.append(WRITE_DELIMITER).append(((Deadline) task).getBy());
        } else if (task instanceof Event) {
            Event e = (Event) task;
            sb.append(WRITE_DELIMITER).append(e.getFrom())
                    .append(WRITE_DELIMITER).append(e.getTo());
        }

        return sb.toString();
    }
}
