package ru.bandamc.advancedHNS.events;

import com.destroystokyo.paper.ClientOption;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.bandamc.advancedHNS.api.events.ArenaJoinEvent;
import ru.bandamc.advancedHNS.api.events.ArenaLeaveEvent;

public class OnArenaLeaveEvent implements Listener {
    @EventHandler
    public void onArenaLeave(ArenaLeaveEvent event) {
        String language = event.getPlayer().getClientOption(ClientOption.LOCALE);
        // todo: validate if it was the last player to fix arena
    }
}
