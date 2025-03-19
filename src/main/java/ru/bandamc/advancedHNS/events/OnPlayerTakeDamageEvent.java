package ru.bandamc.advancedHNS.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
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
                    for (var seeker : arena.getSeekers()) {
                        if (seeker.getPlayer() == player) {
                            event.setCancelled(true);
                            break;
                        }
                    }
                    if (event.getCause() == EntityDamageEvent.DamageCause.FALL
                            || event.getCause() == EntityDamageEvent.DamageCause.DROWNING
                            || event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION
                            || event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION
                            || event.getCause() == EntityDamageEvent.DamageCause.CONTACT
                            || event.getCause() == EntityDamageEvent.DamageCause.CRAMMING
                            || event.getCause() == EntityDamageEvent.DamageCause.DRYOUT
                            || event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK
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
}