package me.plasmabase.pastimegamesstandaloneextension;

import me.plasmabase.pastimegames.exceptions.EventListenerAlreadyBoundException;
import me.plasmabase.pastimegames.manager.Games.GameManager;
import me.plasmabase.pastimegamesstandaloneextension.commands.Accept;
import me.plasmabase.pastimegamesstandaloneextension.commands.Connect4;
import me.plasmabase.pastimegamesstandaloneextension.commands.PGCmd;
import me.plasmabase.pastimegamesstandaloneextension.commands.TicTacToe;
import me.plasmabase.pastimegamesstandaloneextension.events.pastimegames.EndGameEvent;
import me.plasmabase.pastimegamesstandaloneextension.manager.SettingsHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main plugin;
    public static Main plugin() {
        return plugin;
    }

    private static SettingsHandler settingsHandler;
    public static SettingsHandler settingsHandler() {
        return settingsHandler;
    }

    @Override
    public void onEnable() {
        plugin = this;

        this.saveDefaultConfig();

        getCommand("accept").setExecutor(new Accept());
        getCommand("connect4").setExecutor(new Connect4());
        getCommand("tictactoe").setExecutor(new TicTacToe());
        getCommand("pg").setExecutor(new PGCmd());

        try {
            GameManager.gameEnded.subscribe(new EndGameEvent());
        } catch (EventListenerAlreadyBoundException e) {
            e.printStackTrace();
        }
        settingsHandler = new SettingsHandler();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
