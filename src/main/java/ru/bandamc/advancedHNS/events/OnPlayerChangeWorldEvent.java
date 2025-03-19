package ru.bandamc.advancedHNS.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.api.events.ArenaLeaveEvent;

public class OnPlayerChangeWorldEvent implements Listener {
    @EventHandler
    public void onPlayerChangeWorld(PlayerTeleportEvent event) {
        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);

        if (event.getPlayer().hasMetadata("currentArena")) {
            if (!event.getTo().getWorld().getName().equalsIgnoreCase(plugin.playerArena.get(event.getPlayer()).getWorld())) {
                // player who played arena changed world - left arena
                ArenaLeaveEvent e = new ArenaLeaveEvent(event.getPlayer(), plugin.playerArena.get(event.getPlayer()));
                Bukkit.getPluginManager().callEvent(e);
            }
        }
    }
}