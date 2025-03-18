package ru.bandamc.advancedHNS.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;

public class ReloadCommandHandler implements CommandHandler {

    @Override
    public boolean Handle(CommandSender sender, Command command, String label, String[] args) {
        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        if (sender instanceof Player player) {
            // todo: check permissions
            plugin.reloadConfig();
            player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " plugin configuration file was reload.");
            return true;
        }

        plugin.reloadConfig();
        Bukkit.getLogger().info(AdvancedHNS.HNS_PREFIX+" config reloaded.");
        return true;
    }
}
