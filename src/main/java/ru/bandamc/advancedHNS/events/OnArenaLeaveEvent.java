package ru.bandamc.advancedHNS.events;

import com.destroystokyo.paper.ClientOption;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.api.events.ArenaJoinEvent;
import ru.bandamc.advancedHNS.api.events.ArenaLeaveEvent;

public class OnArenaLeaveEvent implements Listener {
    @EventHandler
    public void onArenaLeave(ArenaLeaveEvent event) {
        String language = event.getPlayer().getClientOption(ClientOption.LOCALE);
        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        plugin.boards.get(event.getPlayer()).updateLines();
    }
}
