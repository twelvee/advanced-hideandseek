package ru.bandamc.advancedHNS.events;

import com.destroystokyo.paper.ClientOption;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;
import ru.bandamc.advancedHNS.api.events.ArenaEditEvent;

import java.sql.SQLException;

public class OnArenaEditEvent implements Listener {
    AdvancedHNS plugin;

    public OnArenaEditEvent(AdvancedHNS pl) {
        this.plugin = pl;
    }

    @EventHandler
    public void onArenaEdit(ArenaEditEvent event) {
        String language = event.getPlayer().getClientOption(ClientOption.LOCALE);
        try {
            plugin.getArenaRepository().changeArenaStatus(event.getArena().getName(), 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        event.getPlayer().sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.arena_edit_start_success").replace("{name}", event.getArena().getName()));
    }
}
