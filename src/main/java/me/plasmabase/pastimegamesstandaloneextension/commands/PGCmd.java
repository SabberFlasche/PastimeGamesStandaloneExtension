package me.plasmabase.pastimegamesstandaloneextension.commands;

import me.plasmabase.pastimegamesstandaloneextension.Main;
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

public class PGCmd implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Main.settingsHandler().wrongSyntaxMessage());
            return true;
        }
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        try {
            switch (args[0]) {
                /*case "reload":
                    if (player.hasPermission("pastimegames.reload")) {
                        Main.settingsHandler().reload();
                        player.sendMessage(Main.settingsHandler().reloadMessage());
                    }else {
                        player.sendMessage(Main.settingsHandler().noPermissionMessage());
                    }
                    return true;*/
                case "config":
                    if (player.hasPermission("pastimegames.config")) {
                        if (args.length >= 3) {
                            List<String> settings = Main.settingsHandler().getSettingsList();
                            if (settings.contains(args[1])) {
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int i = 2; i < args.length; i++) {
                                    stringBuilder.append(args[i]).append(" ");
                                }
                                stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
                                String newValue = ChatColor.translateAlternateColorCodes('&', stringBuilder.toString());
                                switch (args[1]) {
                                    case "reloadMessage":
                                        Main.settingsHandler().reloadMessage(newValue);
                                        player.sendMessage(ChatColor.GREEN + "reloadMessage set to " + ChatColor.GREEN + " \"" + newValue + "\"");
                                        break;
                                    case "noPermissionMessage":
                                        Main.settingsHandler().noPermissionMessage(newValue);
                                        player.sendMessage(ChatColor.GREEN + "noPermissionMessage set to " + ChatColor.GREEN + " \"" + newValue + "\"");
                                        break;
                                    case "wrongSyntaxMessage":
                                        Main.settingsHandler().wrongSyntaxMessage(newValue);
                                        player.sendMessage(ChatColor.GREEN + "wrongSyntaxMessage set to " + ChatColor.GREEN + "\"" + newValue + "\"");
                                        break;
                                    case "restrictedGameCreation":
                                        boolean x = Boolean.parseBoolean(newValue);
                                        Main.settingsHandler().restrictedGameCreation(x);
                                        player.sendMessage(ChatColor.GREEN + "restrictedGameCreation set to " + ChatColor.DARK_AQUA + x);
                                        break;
                                }
                            }else {
                                player.sendMessage(ChatColor.RED + "this setting doesn't exist");
                            }
                        }else {
                            if (args[1].equalsIgnoreCase("list")) {
                                player.sendMessage(ChatColor.GREEN + "Config:");
                                player.sendMessage(ChatColor.GRAY + "   - reloadMessage: " + ChatColor.GREEN +
                                        "\"" + ChatColor.RESET + Main.settingsHandler().reloadMessage() +
                                        ChatColor.GREEN + "\"");
                                player.sendMessage(ChatColor.GRAY + "   - noPermissionMessage: " + ChatColor.GREEN +
                                        "\""+ ChatColor.RESET + Main.settingsHandler().noPermissionMessage() +
                                        ChatColor.GREEN + "\"");
                                player.sendMessage(ChatColor.GRAY + "   - wrongSyntaxMessage: " + ChatColor.GREEN +
                                        "\""+ ChatColor.RESET + Main.settingsHandler().wrongSyntaxMessage() +
                                        ChatColor.GREEN + "\"");
                                player.sendMessage(ChatColor.GRAY + "   - restrictedGameCreation: " +
                                        ChatColor.DARK_AQUA + Main.settingsHandler().restrictedGameCreation());
                            }else {
                                player.sendMessage(Main.settingsHandler().wrongSyntaxMessage());
                            }
                        }
                    }else {
                        player.sendMessage(Main.settingsHandler().noPermissionMessage());
                    }
                    return true;
                case "stats":
                    if (args.length == 2) {
                        Player player2 = Bukkit.getPlayer(args[1]);
                        int connect4wins = 0;
                        int tictactoewins = 0;
                        if (player2 == null) {
                            player.sendMessage(ChatColor.RED + "Could not find player (The player has to be online)");
                            return true;
                        }else {
                            tictactoewins = Bukkit.getScoreboardManager().getMainScoreboard().getObjective("tictactoewins").getScore(player2).getScore();
                            connect4wins = Bukkit.getScoreboardManager().getMainScoreboard().getObjective("connect4wins").getScore(player2).getScore();
                        }
                        player.sendMessage(ChatColor.GREEN + "All Stats of " + player2.getName());
                        player.sendMessage(ChatColor.GRAY + "    - Connect4 Wins: " + connect4wins);
                        player.sendMessage(ChatColor.GRAY + "    - TicTacToe Wins: " + tictactoewins);
                    }else {
                        player.sendMessage(Main.settingsHandler().wrongSyntaxMessage());
                    }
                    return true;
                case "help":
                    if (args.length == 2) {
                        switch (args [1]) {
                            case "accept":
                                player.sendMessage(ChatColor.GREEN + "Help:");
                                this.sendHelpAcceptCmd(player);
                                break;
                            case "connect4":
                                player.sendMessage(ChatColor.GREEN + "Help:");
                                this.sendHelpConnect4Cmd(player);
                                break;
                            case "tictactoe":
                                player.sendMessage(ChatColor.GREEN + "Help:");
                                this.sendHelpTicTacToeCmd(player);
                                break;
                            default:
                                player.sendMessage(ChatColor.GREEN + "Help:");
                                this.sendHelpAcceptCmd(player);
                                this.sendHelpConnect4Cmd(player);
                                this.sendHelpTicTacToeCmd(player);
                                break;
                        }
                        return true;
                    }else {
                        player.sendMessage(ChatColor.GREEN + "Help:");
                        this.sendHelpAcceptCmd(player);
                        this.sendHelpConnect4Cmd(player);
                        this.sendHelpTicTacToeCmd(player);
                    }
                    return true;
            }
        }catch (Exception e) {
            player.sendMessage(Main.settingsHandler().wrongSyntaxMessage());
        }
        return true;
    }

    private void sendHelpAcceptCmd(Player player) {
        player.sendMessage(ChatColor.GRAY + "   - Accept: /accept PLAYERNAME CATEGORY");
        player.sendMessage(ChatColor.GRAY + "       - Usage: accepts a request from another player");
        player.sendMessage(ChatColor.GRAY + "       - Arguments:");
        player.sendMessage(ChatColor.GRAY + "           - PLAYERNAME: String, Not necessary");
        player.sendMessage(ChatColor.GRAY + "           - CATEGORY: String, Not necessary");
    }

    private void sendHelpConnect4Cmd(Player player) {
        player.sendMessage(ChatColor.GRAY + "   - Connect4: /connect4 PLAYERNAME");
        player.sendMessage(ChatColor.GRAY + "       - Usage: sends a request to the given player\n" +
                "         to play Connect 4");
        player.sendMessage(ChatColor.GRAY + "       - Arguments:");
        player.sendMessage(ChatColor.GRAY + "           - PLAYERNAME: String, Necessary");
    }

    private void sendHelpTicTacToeCmd(Player player) {
        player.sendMessage(ChatColor.GRAY + "   - TicTacToe: /TicTacToe PLAYERNAME");
        player.sendMessage(ChatColor.GRAY + "       - Usage: sends a request to the given player\n" +
                "         to play TicTacToe");
        player.sendMessage(ChatColor.GRAY + "       - Arguments:");
        player.sendMessage(ChatColor.GRAY + "           - PLAYERNAME: String, Necessary");
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (!(sender instanceof Player)) {
            return new ArrayList<>();
        }
        Player player = (Player) sender;
        if (args.length == 1) {
            if (player.hasPermission("pastimegames.config")) {
                list.add("config");
            }
            if (player.hasPermission("pastimegames.reload")) {
                //list.add("reload");
            }
            list.add("stats");
            list.add("help");
        }else if (args.length == 2) {
            //pg config
            switch (args[0]) {
                case "config":
                    if (player.hasPermission("pastimegames.config")) {
                        list.addAll(Main.settingsHandler().getSettingsList());
                        list.add("list");
                    }
                    break;
                case "help":
                    list.add("all");
                    list.add("accept");
                    list.add("connect4");
                    list.add("tictactoe");
                    break;
                case "stats":
                    return null;
                default:
                    return new ArrayList<>();
            }
        }else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("config")
                    && args[1].equalsIgnoreCase("restrictedGameCreation")
                    && player.hasPermission("pastimegames.config")) {
                list.add("true");
                list.add("false");
            }
        }
        return list;
    }
}
