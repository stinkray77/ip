package commands;

import Storage;
import TaskList;
import Ui;

public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
        ui.close();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}