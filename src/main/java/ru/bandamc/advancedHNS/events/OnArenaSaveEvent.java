package ru.bandamc.advancedHNS.events;

import com.destroystokyo.paper.ClientOption;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;
import ru.bandamc.advancedHNS.api.events.ArenaSaveEvent;

import java.sql.SQLException;

public class OnArenaSaveEvent implements Listener {

    AdvancedHNS plugin;

    public OnArenaSaveEvent(AdvancedHNS pl) {
        this.plugin = pl;
    }

    @EventHandler
    public void onArenaSave(ArenaSaveEvent event) {
        String language = event.getPlayer().getClientOption(ClientOption.LOCALE);
        try {
            plugin.getArenaRepository().updateArena(event.getArena());
            event.getPlayer().sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.arena_save_success").replace("{name}", event.getArena().getName()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
