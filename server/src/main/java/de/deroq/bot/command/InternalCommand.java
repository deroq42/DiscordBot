package de.deroq.bot.command;

public abstract class InternalCommand {

    private final String name;

    public InternalCommand(String name) {
        this.name = name;
    }

    /**
     * Executes an internal command.
     *
     * @param args The arguments of the typed command.
     */
    public abstract void executeCommand(String[] args);

    /**
     * @return the command name.
     */
    public String getName() {
        return name;
    }
}
