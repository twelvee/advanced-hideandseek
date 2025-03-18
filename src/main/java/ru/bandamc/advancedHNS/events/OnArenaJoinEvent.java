package ru.bandamc.advancedHNS.events;

import com.destroystokyo.paper.ClientOption;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;
import ru.bandamc.advancedHNS.api.events.ArenaDeleteEvent;
import ru.bandamc.advancedHNS.api.events.ArenaJoinEvent;

public class OnArenaJoinEvent implements Listener {
    @EventHandler
    public void onArenaJoin(ArenaJoinEvent event) {
        String language = event.getPlayer().getClientOption(ClientOption.LOCALE);
        if (!event.getArena().isReadyToJoin())
            event.setCancelled(true);

        event.getArena().joinHiders(event.getPlayer());
    }
}
