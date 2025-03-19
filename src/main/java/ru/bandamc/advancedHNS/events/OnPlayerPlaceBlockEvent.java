package ru.bandamc.advancedHNS.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;

public class OnPlayerPlaceBlockEvent implements Listener {
    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        if (!plugin.getConfig().getBoolean("allow_placing_blocks_outside_arena")) {
            event.setCancelled(true);
            return;
        }

        if (event.getPlayer().hasMetadata("currentArena")) {
            event.setCancelled(true);
        }
    }
}