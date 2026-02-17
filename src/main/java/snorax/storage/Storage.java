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
    private static final String TASK_TYPE_TODO = "T";
    private static final String TASK_TYPE_DEADLINE = "D";
    private static final String TASK_TYPE_EVENT = "E";
    private static final String STATUS_DONE = "1";
    private static final String DELIMITER = " \\| ";
    private static final int MIN_PARTS_COUNT = 3;
    private static final int INDEX_TASK_TYPE = 0;
    private static final int INDEX_STATUS = 1;
    private static final int INDEX_DESCRIPTION = 2;
    private static final int INDEX_DEADLINE_TIME = 3;
    private static final int INDEX_EVENT_FROM = 3;
    private static final int INDEX_EVENT_TO = 4;

    private String filePath;

    /**
     * Constructs a Storage instance with the specified file path.
     *
     * @param filePath The path to the file for storing tasks.
     */
    public Storage(String filePath) {
        assert filePath != null : "File path cannot be null";
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file.
     * Creates the file if it does not exist.
     * Skips corrupted lines and continues loading valid tasks.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws SnoraxException If there is an error reading the file.
     */
    public ArrayList<Task> load() throws SnoraxException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            createFileAndDirectories(file);
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = parseTaskFromLine(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            throw new SnoraxException("Error reading file: " + e.getMessage());
        }

        assert tasks != null : "Loaded tasks list should not be null";
        return tasks;
    }

    private void createFileAndDirectories(File file) throws SnoraxException {
        try {
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            file.createNewFile();
        } catch (IOException e) {
            throw new SnoraxException("Error creating file: " + e.getMessage());
        }
    }

    private Task parseTaskFromLine(String line) {
        String[] parts = line.split(DELIMITER);

        if (parts.length < MIN_PARTS_COUNT) {
            System.out.println("Skipping corrupted line: " + line);
            return null;
        }

        String taskType = parts[INDEX_TASK_TYPE];
        boolean isDone = parts[INDEX_STATUS].equals(STATUS_DONE);
        String description = parts[INDEX_DESCRIPTION];

        Task task = createTaskByType(taskType, description, parts);

        if (task != null && isDone) {
            task.markAsDone();
        }

        return task;
    }

    private Task createTaskByType(String taskType, String description, String[] parts) {
        switch (taskType) {
            case TASK_TYPE_TODO:
                return new Todo(description);
            case TASK_TYPE_DEADLINE:
                return createDeadlineTask(description, parts);
            case TASK_TYPE_EVENT:
                return createEventTask(description, parts);
            default:
                System.out.println("Unknown task type: " + taskType);
                return null;
        }
    }

    private Task createDeadlineTask(String description, String[] parts) {
        if (parts.length <= INDEX_DEADLINE_TIME) {
            System.out.println("Invalid deadline format");
            return null;
        }
        return new Deadline(description, parts[INDEX_DEADLINE_TIME]);
    }

    private Task createEventTask(String description, String[] parts) {
        if (parts.length <= INDEX_EVENT_TO) {
            System.out.println("Invalid event format");
            return null;
        }
        return new Event(description, parts[INDEX_EVENT_FROM], parts[INDEX_EVENT_TO]);
    }

    /**
     * Saves the task list to the file.
     * Overwrites the existing file content.
     *
     * @param tasks The list of tasks to save.
     * @throws SnoraxException If there is an error writing to the file.
     */
    public void save(ArrayList<Task> tasks) throws SnoraxException {
        assert tasks != null : "Tasks list cannot be null";

        try (FileWriter writer = new FileWriter(filePath)) {
            for (Task task : tasks) {
                writer.write(formatTaskForStorage(task) + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new SnoraxException("Error saving tasks: " + e.getMessage());
        }
    }

    private String formatTaskForStorage(Task task) {
        assert task != null : "Task cannot be null";

        StringBuilder sb = new StringBuilder();
        String taskType = getTaskType(task);
        String status = task.isDone() ? STATUS_DONE : "0";

        sb.append(taskType).append(" | ")
                .append(status).append(" | ")
                .append(task.getDescription());

        if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            sb.append(" | ").append(deadline.getBy());
        } else if (task instanceof Event) {
            Event event = (Event) task;
            sb.append(" | ").append(event.getFrom())
                    .append(" | ").append(event.getTo());
        }

        return sb.toString();
    }

    private String getTaskType(Task task) {
        if (task instanceof Todo) {
            return TASK_TYPE_TODO;
        } else if (task instanceof Deadline) {
            return TASK_TYPE_DEADLINE;
        } else if (task instanceof Event) {
            return TASK_TYPE_EVENT;
        }
        throw new IllegalArgumentException("Unknown task type");
    }
}
