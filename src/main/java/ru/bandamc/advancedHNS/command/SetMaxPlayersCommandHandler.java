package ru.bandamc.advancedHNS.command;

import com.destroystokyo.paper.ClientOption;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;
import ru.bandamc.advancedHNS.entities.Arena;

public class SetMaxPlayersCommandHandler implements CommandHandler {
    @Override
    public boolean Handle(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 4) {
            return false;
        }

        if (sender instanceof Player player) {
            String language = player.getClientOption(ClientOption.LOCALE);

            AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
            int maxPlayers = Integer.parseInt(args[3]);
            if (maxPlayers <= 0) {
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.arena_error_maxplayers_less_than_two"));
                return false;
            }
            if (plugin.arenaEdits.containsKey(player)) {
                Arena arena = plugin.arenaEdits.get(player);
                arena.setMaxPlayers(maxPlayers);
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.arena_maxplayers_set").replace("{name}", arena.getName()).replace("{number}", maxPlayers+""));
                return true;
            }
        }
        return false;
    }
}
