package ru.bandamc.advancedHNS.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.bandamc.advancedHNS.AdvancedHNS;

public class HelpCommandHandler implements CommandHandler {

    String[] chatMessages = {
            AdvancedHNS.HNS_CHAT_PREFIX + " Available commands:",
            "§7/hns help - shows available commands",
            "§7/hns reload - reload configuration files",
            "§7/hns admin help - shows information about how you setup arenas.",
    };

    String[] consoleMessages = {
            AdvancedHNS.HNS_PREFIX + " Available commands:",
            "hns help - shows available commands",
            "hns reload - reload configuration files",
            "hns admin help - shows information about how you setup arenas.",
    };

    @Override
    public boolean Handle(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            for (String msg : chatMessages) {
                player.sendMessage(msg);
            }
            return true;
        }
        for (String msg : consoleMessages) {
            Bukkit.getLogger().info(msg);
        }
        return true;
    }
}
