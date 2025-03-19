package ru.bandamc.advancedHNS.events;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.entities.Arena;
import ru.bandamc.advancedHNS.entities.Hider;

import java.util.Collections;

public class OnPlayerMoveEvent implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        if (event.getPlayer().hasMetadata("currentArena")) {
            Arena arena = plugin.playerArena.get(event.getPlayer());
            for (Hider hider : arena.getHiders()) {
                if (hider.getPlayer() == event.getPlayer() && arena.getStatus() == 3) {
                    if (event.hasChangedPosition()) {
                        if (event.getPlayer().hasMetadata("solidBlockCheck")) {
                            int taskId = (int) event.getPlayer().getMetadata("solidBlockCheck").get(0).value();
                            Bukkit.getScheduler().cancelTask(taskId);
                        }
                        Material m = (Material) hider.getPlayer().getMetadata("currentBlock").get(0).value();
                        if (!DisguiseAPI.isDisguised(hider.getPlayer())) {
                            DisguiseAPI.disguiseToAll(hider.getPlayer(), new MiscDisguise(DisguiseType.FALLING_BLOCK, m));
                            hider.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
                            Block d = arena.spawnedBlocks.get(hider);
                            if (d != null) {
                                Bukkit.getWorld(arena.getWorld()).setBlockData(d.getLocation(), Material.AIR.createBlockData());
                            }
                        }
                        int taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            if (hider.getPlayer().hasMetadata("currentBlock")) {
                                hider.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false, false));
                                Bukkit.getWorld(arena.getWorld()).setBlockData(hider.getPlayer().getLocation().getBlock().getLocation(), m.createBlockData());
                                Location l = hider.getPlayer().getLocation();
                                Block b = Bukkit.getWorld(arena.getWorld()).getBlockAt(l);
                                l.setY(l.getY() + 1);
                                hider.getPlayer().teleport(l);
                                DisguiseAPI.undisguiseToAll(hider.getPlayer());
                                arena.spawnedBlocks.put(hider.getPlayer(), b);
                            }
                        }, 3 * 20);
                        hider.getPlayer().setMetadata("solidBlockCheck", new FixedMetadataValue(plugin, taskId));
                    }
                }
            }
        }
    }
}