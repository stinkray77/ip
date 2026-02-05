public class Snorax {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    
    public Snorax(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (SnoraxException e) {
            ui.showError("Error loading tasks: " + e.getMessage());
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String input = ui.readCommand();
                ui.showLine();
                Command command = Parser.parseCommand(input);
                isExit = executeCommand(command, input);
            } catch (Exception e) {
                ui.showError("Sorry something went wrong I'm gg back to sleep");
            }
        }
    }

    private boolean executeCommand(Command command, String input) {
        switch (command) {
            case BYE:
                ui.showGoodbye();
                ui.close();
                return true;

            case LIST:
                ui.showTaskList(tasks);
                break;
            
            case DELETE:
                try {
                    int deleteIndex = Parser.parseTaskIndex(input);
                    Task removeTask = tasks.deleteTask(deleteIndex);
                    storage.save(tasks.getTasks());
                    ui.showTaskDeleted(removeTask, tasks.size());
                } catch (SnoraxException e) {
                    ui.showError(e.getMessage());
                }
                break;

            case UNMARK:
                try {
                    int unmarkIndex = Parser.parseTaskIndex(input);
                    tasks.getTask(unmarkIndex).markAsNotDone();
                    storage.save(tasks.getTasks());
                    ui.showTaskUnmarked(tasks.getTask(unmarkIndex));
                } catch (SnoraxException e) {
                    ui.showError(e.getMessage());
                }
                break;

            case MARK:
                try {
                    int markIndex = Parser.parseTaskIndex(input);
                    tasks.getTask(markIndex).markAsDone();
                    storage.save(tasks.getTasks());
                    ui.showTaskMarked(tasks.getTask(markIndex));
                } catch (SnoraxException e) {
                    ui.showError(e.getMessage());
                }
                break;

            case TODO:
                try {
                    String description = Parser.parseTodoDescription(input);
                    Task todo = new Todo(description);
                    tasks.addTask(todo);
                    storage.save(tasks.getTasks());
                    ui.showTaskAdded(todo, tasks.size());
                } catch (SnoraxException e) {
                    ui.showError(e.getMessage());
                }
                break;

            case DEADLINE:
                try {
                    String[] deadlineParts = Parser.parseDeadline(input);
                    Task deadline = new Deadline(deadlineParts[0], deadlineParts[1]);
                    tasks.addTask(deadline);
                    storage.save(tasks.getTasks());
                    ui.showTaskAdded(deadline, tasks.size());
                } catch (SnoraxException e) {
                    ui.showError(e.getMessage());
                }
                break;

            case EVENT:
                try {
                    String[] eventParts = Parser.parseEvent(input);
                    Task event = new Event(eventParts[0], eventParts[1], eventParts[2]);
                    tasks.addTask(event);
                    storage.save(tasks.getTasks());
                    ui.showTaskAdded(event, tasks.size());
                } catch (SnoraxException e) {
                    ui.showError(e.getMessage());
                }
                break;

            case UNKNOWN:
                ui.showError("idk what that means, goodnight");
        }
        return false;
    }

    public static void main(String[] args) {
        new Snorax("./data/snorax.txt").run();
    }
}

       