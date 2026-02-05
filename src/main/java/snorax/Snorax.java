package snorax;

import snorax.command.Command;
import snorax.exception.SnoraxException;
import snorax.parser.Parser;
import snorax.storage.Storage;
import snorax.tasklist.TaskList;
import snorax.ui.Ui;

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
                Command c = Parser.parse(input);
                isExit = c.isExit();
            } catch (Exception e) {
                ui.showError("Sorry something went wrong I'm gg back to sleep");
            }
        }
    }
        public static void main(String[] args) {
        new Snorax("./data/snorax.txt").run();
    }
}

       