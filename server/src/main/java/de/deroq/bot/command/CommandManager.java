package de.deroq.bot.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandManager {

    private final Map<String, InternalCommand> internalCommandMap;
    private final Map<String, ModuleCommand> moduleCommandMap;

    public CommandManager() {
        this.internalCommandMap = new HashMap<>();
        this.moduleCommandMap = new HashMap<>();
    }

    /**
     * Registers an internal command.
     *
     * @param commandName the name of the command.
     * @param command the class of the command.
     */
    public void registerCommand(String commandName, InternalCommand command) {
        internalCommandMap.put(commandName, command);
    }

    /**
     * Registers a module command.
     *
     * @param commandName the name of the command.
     * @param command the class of the command.
     */
    public void registerCommand(String commandName, ModuleCommand command) {
        moduleCommandMap.put(commandName, command);
    }

    /**
     * Gets an internal command by its name.
     *
     * @param commandName the name of the command.
     * @return an InternalCommand by its name.
     */
    public InternalCommand getInternalCommand(String commandName) {
        return internalCommandMap.get(commandName);
    }

    /**
     * Gets a module command by its name.
     *
     * @param commandName the name of the command.
     * @return an ModuleCommand by its name.
     */
    public ModuleCommand getModuleCommand(String commandName) {
        return moduleCommandMap.get(commandName);
    }

    /**
     * Checks if an internal command has been registered by its name.
     *
     * @param commandName the name of the command.
     * @return true if it is in the commandMap.
     */
    public boolean existsInternalCommand(String commandName) {
        return internalCommandMap.containsKey(commandName);
    }

    /**
     * Checks if a module command has been registered by its name.
     *
     * @param commandName the name of the command.
     * @return true if it is in the commandMap.
     */
    public boolean existsModuleCommand(String commandName) {
        return moduleCommandMap.containsKey(commandName);
    }

    /**
     * Gets the internal commandMap.
     *
     * @return a Map with the commandName as key and the InternalCommand as value.
     */
    public Map<String, InternalCommand> getInternalCommandMap() {
        return internalCommandMap;
    }

    /**
     * Gets the module commandMap.
     *
     * @return a Map with the commandName as key and the ModuleCommand as value.
     */
    public Map<String, ModuleCommand> getModuleCommandMap() {
        return moduleCommandMap;
    }

    /**
     * Starts reading console commands.
     */
    public void startReadingInternalCommands() {
        while(true) {
            Scanner scanner = new Scanner(System.in);
            String[] args = scanner.nextLine().split(" ");
            String command = args[0];

            if(existsInternalCommand(command)) {
                getInternalCommand(command).executeCommand(args);
            }
        }
    }
}
