package ru.bandamc.advancedHNS.events;

import com.destroystokyo.paper.ClientOption;
import net.kyori.adventure.text.Component;
import org.bukkit.Statistic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;
import ru.bandamc.advancedHNS.api.events.ArenaDeleteEvent;
import ru.bandamc.advancedHNS.api.events.ArenaJoinEvent;

import static org.bukkit.Bukkit.getServer;

public class OnArenaJoinEvent implements Listener {
    @EventHandler
    public void onArenaJoin(ArenaJoinEvent event) {
        String language = event.getPlayer().getClientOption(ClientOption.LOCALE);
        if (!event.getArena().isReadyToJoin())
            event.setCancelled(true);

        event.getArena().joinHiders(event.getPlayer());

        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        plugin.boards.get(event.getPlayer()).updateLines(
                Component.text("Joined arena:"),
                Component.text(event.getArena().getName())
        );
    }
}
