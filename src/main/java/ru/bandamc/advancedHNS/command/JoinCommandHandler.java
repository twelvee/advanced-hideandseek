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
import ru.bandamc.advancedHNS.entities.Arena;

import java.util.ArrayList;

public class JoinCommandHandler implements CommandHandler {

    @Override
    public boolean Handle(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            // arena name not specified.
            return false;
        }
        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        if (sender instanceof Player player) {
            String language = player.getClientOption(ClientOption.LOCALE);
            Arena arena = plugin.arenas.get(args[1]);
            if (arena == null) {
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.join_arena_failed"));
                return true;
            }

            ArenaJoinEvent event = new ArenaJoinEvent(player, arena);

            Bukkit.getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                if (!plugin.arenaPlayers.containsKey(arena)) {
                    plugin.arenaPlayers.put(arena, new ArrayList<>());
                }

                if (plugin.playerArena.containsKey(player)) {
                    player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.already_in_arena").replace("{name}", arena.getName()));
                    return true;
                }

                plugin.arenaPlayers.get(arena).add(player);
                plugin.playerArena.put(player, arena);
                player.teleport(arena.getLobbyPos());
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.join_arena").replace("{name}", arena.getName()));
                return true;
            }

            player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.join_arena_failed"));
            return true;
        }

        Bukkit.getLogger().info(AdvancedHNS.HNS_PREFIX+" you can join arena only if you're player.");
        return true;
    }
}
