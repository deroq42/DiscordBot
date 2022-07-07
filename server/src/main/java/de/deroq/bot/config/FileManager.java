package de.deroq.bot.config;

import de.deroq.bot.config.models.RolesConfig;
import com.google.gson.Gson;
import de.deroq.bot.config.models.ChannelsConfig;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FileManager {

    private final File FOLDER;
    private final File CHANNELS_FILE;
    private final File ROLES_FILE;
    private ChannelsConfig channelsConfig;
    private RolesConfig rolesConfig;

    public FileManager() {
        this.FOLDER = new File("DiscordBot/");
        this.CHANNELS_FILE = new File(FOLDER.getPath(), "channels.json");
        this.ROLES_FILE = new File(FOLDER.getPath(), "roles.json");

        loadFiles();
    }

    private void loadFiles() {
        try {
            if (!FOLDER.exists()) {
                FOLDER.mkdirs();
            }

            //If the folder is empty, create files.
            if(FOLDER.isDirectory() && FOLDER.listFiles().length == 0) {
                createChannelsConfig();
                createRolesConfig();
                return;
            }

            this.channelsConfig = (ChannelsConfig) readConfig(CHANNELS_FILE, ChannelsConfig.class);
            this.rolesConfig = (RolesConfig) readConfig(ROLES_FILE, RolesConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createChannelsConfig() throws IOException {
        if(!CHANNELS_FILE.exists()) {
            if (!CHANNELS_FILE.createNewFile()) {
                throw new IOException("Error while creating Roles file: File has not been created.");
            }
        }

        this.channelsConfig = ChannelsConfig.create(CHANNELS_FILE, 1L, 2L);
        saveConfig(channelsConfig);
    }

    private void createRolesConfig() throws IOException {
        if(!ROLES_FILE.exists()) {
            if(!ROLES_FILE.createNewFile()) {
                throw new IOException("Error while creating Roles file: File has not been created.");
            }
        }

        Map<String, Long> roles = new HashMap<>();
        roles.put("Spieler", 1L);

        this.rolesConfig = RolesConfig.create(ROLES_FILE, roles);
        saveConfig(rolesConfig);
    }

    public void saveConfig(Config config) throws IOException {
        Optional<File> optionalConfigFile = Arrays.stream(FOLDER.listFiles())
                .filter(file -> file.getName().equals(config.getFileName()))
                .findFirst();

        if(!optionalConfigFile.isPresent()) {
            throw new IOException("Error while getting file " + config.getFileName() + ": File can not be found");
        }

        File configFile = optionalConfigFile.get();
        try (FileWriter fileWriter = new FileWriter(configFile)) {
            fileWriter.write(new Gson().toJson(config));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Config readConfig(File file, Class<? extends Config> clazz) throws FileNotFoundException {
        return new Gson().fromJson(new FileReader(file), clazz);
    }

    public ChannelsConfig getChannelsConfig() {
        return channelsConfig;
    }

    public RolesConfig getRolesConfig() {
        return rolesConfig;
    }
}
