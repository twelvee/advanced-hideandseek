package ru.bandamc.advancedHNS.command;

import com.destroystokyo.paper.ClientOption;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;
import ru.bandamc.advancedHNS.api.events.ArenaSaveEvent;
import ru.bandamc.advancedHNS.entities.Arena;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SaveArenaCommandHandler implements CommandHandler {
    @Override
    public boolean Handle(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
            String language = player.getClientOption(ClientOption.LOCALE);
            Arena arena = plugin.arenaEdits.get(player);

            if (arena == null) {
                // player not editing any arenas
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.user_not_edit_arena"));
                return false;
            }

            if (!arena.validateToStore()) {
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.arena_save_failed_errors").replace("{name}", arena.getName()));
                for (var msg : arena.getValidationErrors()) {
                    player.sendMessage(msg);
                }
                return false;
            }

            ArenaSaveEvent event = new ArenaSaveEvent(player, arena);
            Bukkit.getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                plugin.arenaEdits.remove(event.getPlayer());
            }

            return true;
        }
        return false;
    }
}
