package ru.bandamc.advancedHNS.events;

import com.destroystokyo.paper.ClientOption;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;
import ru.bandamc.advancedHNS.api.events.ArenaJoinEvent;
import ru.bandamc.advancedHNS.api.events.JoinHiderEvent;

public class OnHiderJoinEvent implements Listener {
    @EventHandler
    public void onHiderJoinEvent(JoinHiderEvent event) {
        String language = event.getPlayer().getClientOption(ClientOption.LOCALE);

    }
}
