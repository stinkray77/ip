package commands;

import Storage;
import TaskList;
import Ui;
import Task;
import SnoraxException;

public class UnmarkCommand extends Command {
    private int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnoraxException {
        Task task = tasks.getTask(index);
        task.markAsNotDone();
        storage.save(tasks.getTasks());
        ui.showTaskUnmarked(task);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}