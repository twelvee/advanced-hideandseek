package ru.bandamc.advancedHNS.command;

import com.destroystokyo.paper.ClientOption;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;
import ru.bandamc.advancedHNS.api.events.ArenaDeleteEvent;
import ru.bandamc.advancedHNS.entities.Arena;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteArenaCommandHandler implements CommandHandler {
    @Override
    public boolean Handle(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 4) {
            // arena name not specified.
            return false;
        }
        if (sender instanceof Player player) {
            String arenaName = args[3]; // hns admin arena delete ->name<-
            AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
            if (!arenaName.equalsIgnoreCase(" ")) {
                String language = player.getClientOption(ClientOption.LOCALE);
                try {
                    ResultSet arena = plugin.getArenaRepository().getArenaInfo(arenaName);
                    if (!arena.isBeforeFirst()) {
                        // arena not found
                        player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.arena_not_found").replace("{name}", arenaName));
                        return false;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                try {
                    plugin.getArenaRepository().deleteArena(arenaName);
                    Arena arena = plugin.arenaEdits.get(player);
                    ArenaDeleteEvent event = new ArenaDeleteEvent(player, arena);
                    Bukkit.getPluginManager().callEvent(event);
                    if (!event.isCancelled()) {
                        plugin.arenaEdits.remove(event.getPlayer());
                    }
                    return true;
                } catch (SQLException e) {
                    player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.arena_delete_failed").replace("{name}", arenaName));
                    Bukkit.getLogger().info(e.getMessage());
                    return false;
                }
            }
        }
        return false;
    }
}
