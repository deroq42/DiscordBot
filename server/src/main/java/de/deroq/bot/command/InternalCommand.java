package de.deroq.bot.command;

public abstract class InternalCommand {

    private final String NAME;

    public InternalCommand(String name) {
        this.NAME = name;
    }

    public abstract void executeCommand(String[] args);

    public String getName() {
        return NAME;
    }
}
