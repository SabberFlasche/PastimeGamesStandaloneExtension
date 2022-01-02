package me.plasmabase.pastimegamesstandaloneextension.commands;

import me.plasmabase.pastimegamesstandaloneextension.Main;
import me.plasmabase.pastimegamesstandaloneextension.manager.RequestManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TicTacToe implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (Main.settingsHandler().restrictedGameCreation() && !sender.hasPermission("pastimegames.play")) {
            sender.sendMessage(Main.settingsHandler().noPermissionMessage());
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(Main.settingsHandler().wrongSyntaxMessage());
            return true;
        }
        if (!(sender instanceof Player)) {
            return true;
        }
        try {
            Player player1 = (Player) sender;
            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))) {
                Player player2 = Bukkit.getPlayer(args[0]);
                if (RequestManager.addTicTacToeRequest(player1, player2)) {
                    this.sendMessage(player2, player1);
                    player1.sendMessage(ChatColor.GREEN + "Request sent");
                }else {
                    try {
                        player1.sendMessage(ChatColor.RED + "You already sent a request to " + player2.getName());
                    }catch (NullPointerException e) {
                        player1.sendMessage(ChatColor.RED + "You already sent a request to this player");
                    }
                }
            }else {
                player1.sendMessage(ChatColor.RED + "Could not find an opponent with this name");
            }
        }catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Something went wrong");
        }
        return true;
    }

    public void sendMessage(Player p, Player requester) {
        TextComponent message = new TextComponent( ChatColor.GRAY + requester.getDisplayName() + " wants to play TicTacToe with you: " );
        TextComponent message2 = new TextComponent( ChatColor.GREEN + "[ Accept ]" );
        message2.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/accept ") );
        BaseComponent[] hoverMessage = new BaseComponent[1];
        hoverMessage[0] = new TextComponent( ChatColor.GRAY + "Click to accept");
        message2.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage));
        message.addExtra(message2);
        p.spigot().sendMessage( message );
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (!(sender instanceof Player)){
            return null;
        }
        Player player = (Player) sender;
        List<Player> players = new ArrayList<>();
        players.addAll(Bukkit.getOnlinePlayers());
        players.remove(player);
        List<String> playerNames = new ArrayList<>();
        for (Player player1 : players) {
            playerNames.add(player1.getDisplayName());
        }
        return playerNames;
    }
}
