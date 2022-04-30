package de.deroq.bot.models.configs;

import de.deroq.bot.models.Config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class RolesConfig extends Config {

    private final Map<String, Long> roles;

    private RolesConfig(File file, Map<String, Long> roles) {
        super(file.getName());
        this.roles = roles;
    }

    public Map<String, Long> getRoles() {
        return roles;
    }

    public static RolesConfig create(File file, Map<String, Long> roles) {
        return new RolesConfig(file, roles);
    }
}
