package com.example.loginplugin.commands;

import com.example.loginplugin.LoginPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoginCommand implements CommandExecutor {

    private LoginPlugin plugin;

    public LoginCommand(LoginPlugin plugin) {
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
            player.sendMessage(ChatColor.RED + "Использование: /login <пароль>");
            return true;
        }

        String password = args[0];
        String storedPassword = plugin.getPlayerPasswords().get(player.getUniqueId());

        if (storedPassword == null) {
            player.sendMessage(ChatColor.RED + "Вы не зарегистрированы. Используйте /register.");
            return true;
        }

        if (!storedPassword.equals(password)) {
            player.sendMessage(ChatColor.RED + "Неверный пароль. Попробуйте снова.");
            return true;
        }

        player.sendMessage(ChatColor.GREEN + "Вы успешно вошли в систему!");
        plugin.getLastLoginTime().put(player.getAddress().getAddress().getHostAddress(), System.currentTimeMillis());
        return true;
    }
}
