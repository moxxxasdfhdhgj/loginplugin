package com.example.loginplugin.commands;

import com.example.loginplugin.LoginPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegisterCommand implements CommandExecutor {

    private LoginPlugin plugin;

    public RegisterCommand(LoginPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Эту команду могут использовать только игроки.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Использование: /register <пароль>");
            return true;
        }

        String password = args[0];
        plugin.getPlayerPasswords().put(player.getUniqueId(), password);
        player.sendMessage(ChatColor.GREEN + "Вы успешно зарегистрировались!");
        return true;
    }
}
