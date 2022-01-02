package me.plasmabase.pastimegamesstandaloneextension.commands;

import me.plasmabase.pastimegames.manager.Games.GameType;
import me.plasmabase.pastimegamesstandaloneextension.manager.RequestManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Accept implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (args.length >= 1) {
            List<String> players = new ArrayList<>();
            Bukkit.getOnlinePlayers().forEach(player1 -> players.add(player1.getName()));
            if (players.contains(args[0])) {
                if (args.length >= 2) {
                    if (getCategories().contains(args[1])) {
                        try {
                            this.sendResult(RequestManager.accept(player, Bukkit.getPlayer(args[0]), GameType.valueOf(args[1].toLowerCase())), player, true);
                        }catch (IllegalArgumentException e) {
                            int x = RequestManager.accept(player, Bukkit.getPlayer(args[0]));
                            if (x == 2) {
                                player.sendMessage(ChatColor.RED + "Couldn't find Category");
                            }else if (x == 0) {
                                player.sendMessage(ChatColor.RED + "Couldn't find any open request");
                            }
                        }
                    }else {
                        this.sendResult(RequestManager.accept(player, Bukkit.getPlayer(args[0])), player, false);
                    }
                }else {
                    this.sendResult(RequestManager.accept(player, Bukkit.getPlayer(args[0])), player, false);
                }
            }else {
                player.sendMessage(ChatColor.RED + "Couldn't find player");
            }
        }else {
            this.sendResult(RequestManager.accept(player), player, false);
        }
        return true;
    }

    private void sendResult(int result, Player player, boolean lastOption) {
        switch (result) {
            case 0:
                player.sendMessage(ChatColor.RED + "Couldn't find any open request");
                return;
            case 2:
                if (lastOption) {
                    player.sendMessage(ChatColor.RED + "Multiple requests exist. Please check if you have spelled everything correct, because this shouldn't happen if you used /accept PLAYERNAME CATEGORY.");
                    return;
                }
                player.sendMessage(ChatColor.RED + "Multiple requests exist. Please specify the request, you want to accept. /accept PLAYERNAME CATEGORY");
        }
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 2) {
            return getCategories();
        }else if (args.length > 2) {
            return new ArrayList<>();
        }
        return null;
    }

    private List<String> getCategories() {
        List<String> list = new ArrayList<>();
        list.add(GameType.CONNECT4.name());
        return list;
    }
}
