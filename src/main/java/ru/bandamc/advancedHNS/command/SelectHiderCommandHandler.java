package ru.bandamc.advancedHNS.command;

import com.destroystokyo.paper.ClientOption;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;
import ru.bandamc.advancedHNS.api.events.ArenaJoinEvent;
import ru.bandamc.advancedHNS.api.events.JoinHiderEvent;
import ru.bandamc.advancedHNS.entities.Arena;
import ru.bandamc.advancedHNS.entities.Hider;

import java.util.ArrayList;

public class SelectHiderCommandHandler implements CommandHandler {

    @Override
    public boolean Handle(CommandSender sender, Command command, String label, String[] args) {
        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        if (sender instanceof Player player) {
            String language = player.getClientOption(ClientOption.LOCALE);
            Arena arena = plugin.playerArena.get(player);
            if (arena == null) {
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.not_in_arena"));
                return true;
            }
            if (arena.getStatus() != 1 && arena.getStatus() != 2) {
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.change_team_error"));
                return true;
            }

            if(arena.getMaxHiders() <= arena.getHiders().size()) {
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.max_amount_of_hiders_reached"));
                return true;
            }
            JoinHiderEvent event = new JoinHiderEvent(player, arena);
            Bukkit.getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                arena.joinHiders(event.getPlayer());
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.join_hider"));
                return true;
            }
        }

        Bukkit.getLogger().info(AdvancedHNS.HNS_PREFIX+" you can join arena only if you're player.");
        return true;
    }
}
