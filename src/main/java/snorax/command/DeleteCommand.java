package snorax.command;

import snorax.exception.SnoraxException;
import snorax.storage.Storage;
import snorax.task.Task;
import snorax.tasklist.TaskList;
import snorax.ui.Ui;

public class DeleteCommand extends Command {
    private int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnoraxException {
        Task removedTask = tasks.deleteTask(index);
        storage.save(tasks.getTasks());
        ui.showTaskDeleted(removedTask, tasks.size());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}