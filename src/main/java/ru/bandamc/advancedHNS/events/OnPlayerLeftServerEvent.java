package ru.bandamc.advancedHNS.events;

import fr.mrmicky.fastboard.adventure.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.api.events.ArenaLeaveEvent;
import ru.bandamc.advancedHNS.entities.Arena;

public class OnPlayerLeftServerEvent implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        plugin.arenaEdits.remove(event.getPlayer());
        Arena arena = plugin.playerArena.get(event.getPlayer());
        if (arena != null) {
            if (plugin.arenaPlayers.get(arena) != null) {
                plugin.arenaPlayers.get(arena).remove(event.getPlayer());
            }
            plugin.playerArena.remove(event.getPlayer());
            event.getPlayer().setMetadata("in_arena", new FixedMetadataValue(JavaPlugin.getPlugin(AdvancedHNS.class), false));
            // call arena leave event if player was on arena.
            ArenaLeaveEvent leaveEvent = new ArenaLeaveEvent(event.getPlayer(), arena);
            Bukkit.getPluginManager().callEvent(leaveEvent);

            // remove scoreboards
            FastBoard board = plugin.boards.remove(event.getPlayer());
            if (board != null) {
                board.delete();
            }
        }
    }
}
