package snorax.command;

import snorax.exception.SnoraxException;
import snorax.storage.Storage;
import snorax.tasklist.TaskList;
import snorax.ui.Ui;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws SnoraxException;

    public abstract boolean isExit();
}


