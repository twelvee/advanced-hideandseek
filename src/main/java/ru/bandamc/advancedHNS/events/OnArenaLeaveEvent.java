package ru.bandamc.advancedHNS.events;

import com.destroystokyo.paper.ClientOption;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.api.events.ArenaJoinEvent;
import ru.bandamc.advancedHNS.api.events.ArenaLeaveEvent;

public class OnArenaLeaveEvent implements Listener {
    @EventHandler
    public void onArenaLeave(ArenaLeaveEvent event) {
        String language = event.getPlayer().getClientOption(ClientOption.LOCALE);
        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        event.getArena().getPlayers().remove(event.getPlayer());
        if (event.getArena().getPlayers().isEmpty()) {
            // todo: clear everything
            event.getArena().setStatus(1); // set "Ready"
        }
        plugin.boards.get(event.getPlayer()).updateLines();
        event.getPlayer().removeMetadata("currentArena", plugin);

        if (event.getPlayer().hasMetadata("activeBossBar")) {
            BossBar activeBossBar = (BossBar) event.getPlayer().getMetadata("activeBossBar").get(0);
            activeBossBar.removePlayer(event.getPlayer());
            activeBossBar.removeAll();
        }
        if (event.getPlayer().hasMetadata("currentBlock")) {
            if (event.getArena().spawnedBlocks.containsKey(event.getPlayer())) {
                Block d = event.getArena().spawnedBlocks.get(event.getPlayer());
                if (d != null) {
                    Bukkit.getWorld(event.getArena().getWorld()).setBlockData(d.getLocation(), Material.AIR.createBlockData());
                }
            }
            DisguiseAPI.undisguiseToAll(event.getPlayer());
            event.getPlayer().removeMetadata("currentBlock", plugin);
        }
        if (event.getPlayer().hasMetadata("solidBlockCheck")) {
            int taskId = (int) event.getPlayer().getMetadata("solidBlockCheck").get(0).value();
            Bukkit.getScheduler().cancelTask(taskId);
        }
        event.getPlayer().setFlySpeed(0.2f);
        event.getPlayer().setWalkSpeed(0.2f);
        event.getPlayer().removePotionEffect(PotionEffectType.SPEED);
        event.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
        event.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
    }
}
