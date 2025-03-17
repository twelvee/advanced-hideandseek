package ru.bandamc.advancedHNS.command;

import com.destroystokyo.paper.ClientOption;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;
import ru.bandamc.advancedHNS.entities.Arena;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SaveArenaCommandHandler implements CommandHandler {
    @Override
    public boolean Handle(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 4) {
            // arena name not specified.
            return false;
        }
        if (sender instanceof Player player) {
            String arenaName = args[3]; // hns admin arena save ->name<-
            AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
            if (!arenaName.equalsIgnoreCase(" ")) {

                String language = player.getClientOption(ClientOption.LOCALE);
                if (!plugin.arenaEdits.containsKey(player)) {
                    player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.user_not_edit_arena").replace("{name}", arenaName));
                    return false;
                }
                Arena arena = plugin.arenaEdits.get(player);
                try {
                    ResultSet resultSet = plugin.getArenaRepository().getArenaInfo(arenaName);
                    if (!resultSet.isBeforeFirst()) {
                        if (plugin.arenaEdits.containsKey(player)) {
                            try {
                                if (!arena.validateToStore()) {
                                    player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " "+LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.arena_save_failed_errors").replace("{name}", arenaName));
                                    for(var msg : arena.getValidationErrors()) {
                                        player.sendMessage(msg);
                                    }
                                    return false;
                                }
                                plugin.getArenaRepository().createArena(arena);
                                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.arena_save_success").replace("{name}", arenaName));
                                plugin.arenaEdits.remove(player);
                                return true;
                            } catch (SQLException e) {
                                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.arena_create_failed").replace("{name}", arenaName));
                                return false;
                            }
                        }
                        return false;
                    }
                } catch (SQLException e) {
                    player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.arena_not_found").replace("{name}", arenaName));
                    return false;
                }

                try {
                    if (arena == null) {
                        player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.arena_not_found").replace("{name}", arenaName));
                        return false;
                    }
                    if (!arena.validateToStore()) {
                        player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " "+LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.arena_save_failed_errors").replace("{name}", arenaName));
                        for(var msg : arena.getValidationErrors()) {
                            player.sendMessage(msg);
                        }
                        return false;
                    }
                    plugin.getArenaRepository().updateArena(plugin.arenaEdits.get(player));
                    plugin.arenaEdits.remove(player);
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
