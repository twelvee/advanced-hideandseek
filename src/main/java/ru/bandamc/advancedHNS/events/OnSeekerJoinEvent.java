package ru.bandamc.advancedHNS.events;

import com.destroystokyo.paper.ClientOption;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.bandamc.advancedHNS.api.events.JoinHiderEvent;
import ru.bandamc.advancedHNS.api.events.JoinSeekerEvent;

public class OnSeekerJoinEvent implements Listener {
    @EventHandler
    public void onSeekerJoinEvent(JoinSeekerEvent event) {
        String language = event.getPlayer().getClientOption(ClientOption.LOCALE);

    }
}
