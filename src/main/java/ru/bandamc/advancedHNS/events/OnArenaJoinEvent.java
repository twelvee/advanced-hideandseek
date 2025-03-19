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

        if(event.getArena().getPlayers().isEmpty()) {
            event.getArena().setStatus(2); // set "Waiting for players status"
        }
        event.getArena().joinHiders(event.getPlayer());

        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        plugin.boards.get(event.getPlayer()).updateLines(
                Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.youre_in_arena")),
                Component.text(event.getArena().getName()),
                Component.text(""),
                Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.status").replace("{status}", LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.status_"+event.getArena().getStatus()))),
                Component.text(""),
                Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.footer"))
        );
    }
}
