package ru.bandamc.advancedHNS.events;

import com.destroystokyo.paper.ClientOption;
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

        event.getPlayer().setFlySpeed(0.2f);
        event.getPlayer().setWalkSpeed(0.2f);
        event.getPlayer().removePotionEffect(PotionEffectType.SPEED);
        event.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
    }
}
