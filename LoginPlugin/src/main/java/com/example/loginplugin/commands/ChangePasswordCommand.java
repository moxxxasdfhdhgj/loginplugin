package com.example.loginplugin.commands;

import com.example.loginplugin.LoginPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangePasswordCommand implements CommandExecutor {

    private LoginPlugin plugin;

    public ChangePasswordCommand(LoginPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Эту команду могут использовать только игроки.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length != 2) {
            player.sendMessage(ChatColor.RED + "Использование: /changepassword <старый пароль> <новый пароль>");
            return true;
        }

        String oldPassword = args[0];
        String newPassword = args[1];
        String storedPassword = plugin.getPlayerPasswords().get(player.getUniqueId());

        if (storedPassword == null) {
            player.sendMessage(ChatColor.RED + "Вы не зарегистрированы. Используйте /register.");
            return true;
        }

        if (!storedPassword.equals(oldPassword)) {
            player.sendMessage(ChatColor.RED + "Неверный старый пароль. Попробуйте снова.");
            return true;
        }

        plugin.getPlayerPasswords().put(player.getUniqueId(), newPassword);
        player.sendMessage(ChatColor.GREEN + "Пароль успешно изменен!");
        return true;
    }
}
