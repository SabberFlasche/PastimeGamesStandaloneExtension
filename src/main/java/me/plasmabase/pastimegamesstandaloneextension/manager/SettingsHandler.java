package me.plasmabase.pastimegamesstandaloneextension.manager;

import me.plasmabase.pastimegames.manager.Games.GameManager;
import me.plasmabase.pastimegames.manager.SettingsManager;
import me.plasmabase.pastimegamesstandaloneextension.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsHandler {

    private final String version;
    private final FileConfiguration config;
    private final SettingsManager settingsManager;
    private final HashMap<String, String> settingsList;
    private final HashMap<String, Object> settingsValues;

    public SettingsHandler() {
        settingsManager = GameManager.getSettingsManager();
        settingsList = new HashMap<>();
        settingsValues = new HashMap<>();
        this.config = Main.plugin().getConfig();
        this.version = Main.plugin().getDescription().getVersion();
        this.loadConfig();
        this.checkVersion();
    }

    public void loadConfig() {
        //SettingsList
        this.settingsList.put("reloadMessage", "Displayed when you reload the config");
        this.settingsList.put("noPermissionMessage", "Displayed when you have no permission to do something");
        this.settingsList.put("wrongSyntaxMessage", "Displayed when the syntax of a command is wrong");
        this.settingsList.put("restrictedGameCreation", "If you need permission \"pastimegames.play\" to create a game");
        //SettingsValues
        this.settingsValues.put("reloadMessage", this.settingsManager.reloadMessage());
        this.settingsValues.put("noPermissionMessage", this.settingsManager.noPermissionMessage());
        this.settingsValues.put("wrongSyntaxMessage", this.settingsManager.wrongSyntaxMessage());
        this.settingsValues.put("restrictedGameCreation", this.config.getBoolean("restrict_creation_of_games_to_permission"));
    }

    public List<String> getSettingsList() {
        List<String> list = new ArrayList<>();
        list.addAll(settingsList.keySet());
        return list;
    }

    private void checkVersion() {
        try {
            if (!(this.config.getString("version").equalsIgnoreCase(this.version))) {
                this.fixConfig();
            }
        }catch (NullPointerException e) {
            this.fixConfig();
        }
    }

    private void fixConfig() {
        this.config.set("version", version);
        this.config.set("restrict_creation_of_games_to_permission", restrictedGameCreation());

        this.save();
    }

    /**
     * Reloads the config
     */
    public void reload() {
        Main.plugin().reloadConfig();
        this.loadConfig();
        this.checkVersion();
        this.settingsManager.reloadConfig();
    }

    /**
     * Saves the config
     */
    public void save() {
        Main.plugin().saveConfig();
    }

    public String reloadMessage() {
        return (String) this.settingsValues.get("reloadMessage");
    }

    public void reloadMessage(String newValue) {
        this.settingsValues.put("reloadMessage", newValue);
        this.settingsManager.reloadMessage(newValue);
    }

    public String noPermissionMessage() {
        return (String) this.settingsValues.get("noPermissionMessage");
    }

    public void noPermissionMessage(String newValue) {
        this.settingsValues.put("noPermissionMessage", newValue);
        this.settingsManager.noPermissionMessage(newValue);
    }

    public String wrongSyntaxMessage() {
        return (String) this.settingsValues.get("wrongSyntaxMessage");
    }

    public void wrongSyntaxMessage(String newValue) {
        this.settingsValues.put("wrongSyntaxMessage", newValue);
        this.settingsManager.wrongSyntaxMessage(newValue);
    }

    public boolean restrictedGameCreation() {
        return (boolean) this.settingsValues.get("restrictedGameCreation");
    }

    public void restrictedGameCreation(boolean newValue) {
        this.config.set("restrict_creation_of_games_to_permission", newValue);
        this.settingsValues.put("restrictedGameCreation", newValue);
        this.save();
    }
}