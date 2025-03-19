package ru.bandamc.advancedHNS.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OnPlayerBreakBlockEvent implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // todo: add configuration only on arena or everywhere
        event.setCancelled(true);
    }
}