package ru.bandamc.advancedHNS.command;

import com.destroystokyo.paper.ClientOption;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;

import java.sql.SQLException;

public class SaveArenaCommandHandler implements CommandHandler {
    @Override
    public boolean Handle(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 4) {
            // arena name not specified.
            return false;
        }
        if (sender instanceof Player player) {
            String arenaName = args[3]; // hns admin arena create ->name<-
            AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
            if (!arenaName.equalsIgnoreCase(" ")) {
                String language = player.getClientOption(ClientOption.LOCALE);
                try {
                    plugin.getArenaRepository().changeArenaStatus(arenaName, 1);
                    player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.arena_save_success").replace("{name}", arenaName));
                    return true;
                } catch (SQLException e) {
                    player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.arena_save_failed").replace("{name}", arenaName));
                    Bukkit.getLogger().info(e.getMessage());
                    return false;
                }
            }
        }
        return false;
    }
}
