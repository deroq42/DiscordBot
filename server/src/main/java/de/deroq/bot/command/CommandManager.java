package de.deroq.bot.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandManager {

    private final Map<String, InternalCommand> internalCommandHashMap;
    private final Map<String, ModuleCommand> moduleCommandHashMap;

    public CommandManager() {
        this.internalCommandHashMap = new HashMap<>();
        this.moduleCommandHashMap = new HashMap<>();
    }

    /**
     * Registers an internal command.
     *
     * @param commandName the name of the command.
     * @param command the class of the command.
     */
    public void registerCommand(String commandName, InternalCommand command) {
        internalCommandHashMap.put(commandName, command);
    }

    /**
     * Registers a module command.
     *
     * @param commandName the name of the command.
     * @param command the class of the command.
     */
    public void registerCommand(String commandName, ModuleCommand command) {
        moduleCommandHashMap.put(commandName, command);
    }

    /**
     * Gets an internal command by its name.
     *
     * @param commandName the name of the command.
     * @return an InternalCommand by its name.
     */
    public InternalCommand getInternalCommand(String commandName) {
        return internalCommandHashMap.get(commandName);
    }

    /**
     * Gets a module command by its name.
     *
     * @param commandName the name of the command.
     * @return an ModuleCommand by its name.
     */
    public ModuleCommand getModuleCommand(String commandName) {
        return moduleCommandHashMap.get(commandName);
    }

    /**
     * Checks if an internal command has been registered by its name.
     *
     * @param commandName the name of the command.
     * @return true if it is in the commandMap.
     */
    public boolean existsInternalCommand(String commandName) {
        return internalCommandHashMap.containsKey(commandName);
    }

    /**
     * Checks if a module command has been registered by its name.
     *
     * @param commandName the name of the command.
     * @return true if it is in the commandMap.
     */
    public boolean existsModuleCommand(String commandName) {
        return moduleCommandHashMap.containsKey(commandName);
    }

    /**
     * Gets the internal commandMap.
     *
     * @return a Map with the commandName as key and the InternalCommand as value.
     */
    public Map<String, InternalCommand> getInternalCommandMap() {
        return internalCommandHashMap;
    }

    /**
     * Gets the module commandMap.
     *
     * @return a Map with the commandName as key and the ModuleCommand as value.
     */
    public Map<String, ModuleCommand> getModuleCommandMap() {
        return moduleCommandHashMap;
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
