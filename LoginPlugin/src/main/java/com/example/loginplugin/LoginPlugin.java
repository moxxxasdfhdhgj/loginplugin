package com.example.loginplugin;

import com.example.loginplugin.commands.ChangePasswordCommand;
import com.example.loginplugin.commands.LoginCommand;
import com.example.loginplugin.commands.RegisterCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LoginPlugin extends JavaPlugin implements Listener {

    private Map<UUID, String> playerPasswords = new HashMap<>();
    private Map<String, Long> lastLoginTime = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("register").setExecutor(new RegisterCommand(this));
        getCommand("reg").setExecutor(new RegisterCommand(this));
        getCommand("login").setExecutor(new LoginCommand(this));
        getCommand("l").setExecutor(new LoginCommand(this));
        getCommand("changepassword").setExecutor(new ChangePasswordCommand(this));
        getCommand("cpw").setExecutor(new ChangePasswordCommand(this));
    }

    @Override
    public void onDisable() {
        // Сохранение данных при отключении плагина (если нужно)
    }

    public Map<UUID, String> getPlayerPasswords() {
        return playerPasswords;
    }

    public Map<String, Long> getLastLoginTime() {
        return lastLoginTime;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (lastLoginTime.containsKey(player.getAddress().getAddress().getHostAddress()) 
            && (System.currentTimeMillis() - lastLoginTime.get(player.getAddress().getAddress().getHostAddress()) < 300000)) {
            player.sendMessage(ChatColor.GREEN + "Автоматический вход выполнен.");
        } else {
            player.sendMessage(ChatColor.YELLOW + "Пожалуйста, войдите или зарегистрируйтесь. Используйте /login или /register.");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        lastLoginTime.put(player.getAddress().getAddress().getHostAddress(), System.currentTimeMillis());
    }
}
