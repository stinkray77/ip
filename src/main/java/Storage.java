import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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

    public void save(ArrayList<Task> tasks) throws SnoraxException {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Task task : tasks) {
                writer.write(formatTask(task) + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new SnoraxException("Error saving to file");
        }
    }

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
