package snorax;

import snorax.command.Command;
import snorax.exception.SnoraxException;
import snorax.parser.Parser;
import snorax.storage.Storage;
import snorax.tasklist.TaskList;
import snorax.ui.Ui;

/**
 * Main class for the Snorax task management application.
 * Handles initialization, task loading, and the main execution loop.
 */
public class Snorax {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    
    /**
     * Constructs a Snorax instance with the specified file path for task storage.
     * Initializes UI, storage, and attempts to load existing tasks from the file.
     * If loading fails, starts with an empty task list.
     *
     * @param filePath The path to the file where tasks are stored.
     */
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

    /**
     * Runs the main application loop.
     * Displays welcome message, reads user commands, parses them,
     * and executes them until an exit command is received.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String input = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(input);
                isExit = c.isExit();
            } catch (Exception e) {
                ui.showError("Sorry something went wrong I'm gg back to sleep");
            }
        }
    }
     
    /**
     * Main entry point for the Snorax application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Snorax("./data/snorax.txt").run();
    }
}

       