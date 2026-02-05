package commands;

import Storage;
import TaskList;
import Ui;
import SnoraxException;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws SnoraxException;

    public abstract boolean isExit();
}


