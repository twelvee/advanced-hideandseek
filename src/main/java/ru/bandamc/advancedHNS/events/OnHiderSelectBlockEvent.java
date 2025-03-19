package ru.bandamc.advancedHNS.events;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.api.events.ArenaLeaveEvent;
import ru.bandamc.advancedHNS.api.events.HiderSelectBlockEvent;

public class OnHiderSelectBlockEvent implements Listener {
    @EventHandler
    public void onHiderSelectBlock(HiderSelectBlockEvent event) {
        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);

        if (event.getPlayer().hasMetadata("currentArena")) {
            event.getPlayer().setMetadata("currentBlock", new FixedMetadataValue(plugin, event.getMaterial()));
            DisguiseAPI.disguiseToAll(event.getPlayer(), new MiscDisguise(DisguiseType.FALLING_BLOCK, event.getMaterial()));
        }
    }
}