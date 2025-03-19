package ru.bandamc.advancedHNS.events;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;

public class OnPlayerJumpEvent implements Listener {
    @EventHandler
    public void onPlayerJump(PlayerJumpEvent event) {
        //AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        if (event.getPlayer().hasMetadata("currentArena")) {
            if (event.getPlayer().hasMetadata("seekerTimedOut")) {
                event.setCancelled(true);
            }
        }
    }
}