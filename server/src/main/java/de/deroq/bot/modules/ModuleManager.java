package de.deroq.bot.modules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ModuleManager {

    private final List<Module> MODULE_LIST;

    public ModuleManager() {
        this.MODULE_LIST = new ArrayList<>();
    }

    /**
     * Registers a module.
     *
     * @param module The module to register.
     */
    public void registerModule(Module module) {
        MODULE_LIST.add(module);
    }


    /**
     * @param name The name of the module.
     * @return The module by its name.
     */
    public Module getModule(String name) {
        Optional<Module> optionalModule = MODULE_LIST.stream()
                .filter(module -> module.getName().equals(name))
                .findFirst();

        if(!optionalModule.isPresent()) {
            throw new NullPointerException("Error while getting module " + name + ": Module can not be found.");
        }

        return optionalModule.get();
    }

    /**
     * @param clazz the class of the module.
     * @return The module by its class.
     */
    public Module getModule(Class<? extends Module> clazz) {
        Optional<Module> optionalModule = MODULE_LIST.stream()
                .filter(module -> module.getClass().equals(clazz))
                .findFirst();

        if(!optionalModule.isPresent()) {
            throw new NullPointerException("Error while getting module from class " + clazz.getSimpleName() + ": Module can not be found.");
        }

        return optionalModule.get();
    }

    /**
     * @return a List with all Modules.
     */
    public List<Module> getModules() {
        return MODULE_LIST;
    }
}
