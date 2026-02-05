package snorax.command;

import snorax.exception.SnoraxException;
import snorax.storage.Storage;
import snorax.task.Task;
import snorax.tasklist.TaskList;
import snorax.ui.Ui;

public class MarkCommand extends Command {
    private int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnoraxException {
        Task task = tasks.getTask(index);
        task.markAsDone();
        storage.save(tasks.getTasks());
        ui.showTaskMarked(task);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}