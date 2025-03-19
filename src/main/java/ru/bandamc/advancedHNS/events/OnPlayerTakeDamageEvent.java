package ru.bandamc.advancedHNS.events;

import com.google.protobuf.MapEntry;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.entities.Arena;

public class OnPlayerTakeDamageEvent implements Listener {
    @EventHandler
    public void onPlayerTakeDamage(EntityDamageEvent event) {
        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        if (event.getEntity() instanceof Player player) {
            if (plugin.playerArena.containsKey(player)) {
                // player inside some arena
                Arena arena = plugin.playerArena.get(player);
                if (arena.getStatus() != 3) {
                    event.setCancelled(true);
                } else {
                    for (var hider : arena.getHiders()) {
                        if (hider.getPlayer() == player) {
                            event.setCancelled(true);
                            break;
                        }
                    }
                    // todo: disable damage for players if they are hide as solid block
                    if (event.getCause() == EntityDamageEvent.DamageCause.FALL
                            || event.getCause() == EntityDamageEvent.DamageCause.DROWNING
                            || event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION
                            || event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION
                            || event.getCause() == EntityDamageEvent.DamageCause.CONTACT
                            || event.getCause() == EntityDamageEvent.DamageCause.CRAMMING
                            || event.getCause() == EntityDamageEvent.DamageCause.DRYOUT
                            || event.getCause() == EntityDamageEvent.DamageCause.VOID
                            || event.getCause() == EntityDamageEvent.DamageCause.DRAGON_BREATH
                            || event.getCause() == EntityDamageEvent.DamageCause.FALLING_BLOCK
                            || event.getCause() == EntityDamageEvent.DamageCause.POISON
                            || event.getCause() == EntityDamageEvent.DamageCause.MAGIC
                            || event.getCause() == EntityDamageEvent.DamageCause.THORNS
                            || event.getCause() == EntityDamageEvent.DamageCause.SONIC_BOOM
                            || event.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK
                            || event.getCause() == EntityDamageEvent.DamageCause.DROWNING
                            || event.getCause() == EntityDamageEvent.DamageCause.FIRE
                            || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK
                            || event.getCause() == EntityDamageEvent.DamageCause.FREEZE
                            || event.getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR
                            || event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING
                            || event.getCause() == EntityDamageEvent.DamageCause.MELTING
                            || event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE
                            || event.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL
                            || event.getCause() == EntityDamageEvent.DamageCause.STARVATION
                            || event.getCause() == EntityDamageEvent.DamageCause.LAVA
                            || event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION
                            || event.getCause() == EntityDamageEvent.DamageCause.WITHER) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        // simulate block hit
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            if (plugin.playerArena.containsKey(event.getPlayer())) {
                // player is playing on arena
                Arena arena = plugin.playerArena.get(event.getPlayer());
                if (arena.getStatus() != 3) {
                    return;
                }
                boolean hit = false;
                for (var pb : arena.spawnedBlocks.entrySet()) {
                    if (pb.getValue() == event.getClickedBlock()) {
                        pb.getKey().damage(5); // todo: make it customizable
                        Location newLocation = event.getPlayer().getLocation();
                        newLocation.setY(newLocation.getY() - 1);
                        PlayerMoveEvent playerMove = new PlayerMoveEvent(event.getPlayer(), event.getPlayer().getLocation(), newLocation);
                        Bukkit.getPluginManager().callEvent(playerMove);

                        // Create firework?
                        FireworkEffect effect = FireworkEffect.builder()
                                .withColor(Color.RED)
                                .withFade(Color.YELLOW)
                                .build();
                        Firework firework = (Firework) Bukkit.getWorld(arena.getWorld()).spawnEntity(newLocation, EntityType.FIREWORK);
                        FireworkMeta meta = firework.getFireworkMeta();
                        meta.setPower(1);
                        meta.addEffect(effect);
                        firework.setFireworkMeta(meta);
                        firework.detonate();
                        hit = true;
                        break;
                    }
                }
                if (!hit) {
                    event.getPlayer().damage(0.5f);
                }
            }
        }/* else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();

        }*/
    }
}