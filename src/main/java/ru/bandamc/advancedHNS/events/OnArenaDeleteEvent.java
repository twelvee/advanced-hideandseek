package ru.bandamc.advancedHNS.events;

import com.destroystokyo.paper.ClientOption;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;
import ru.bandamc.advancedHNS.api.events.ArenaDeleteEvent;
import ru.bandamc.advancedHNS.api.events.ArenaInitEvent;

public class OnArenaDeleteEvent implements Listener {
    @EventHandler
    public void onArenaDelete(ArenaDeleteEvent event) {
        String language = event.getPlayer().getClientOption(ClientOption.LOCALE);
        event.getPlayer().sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.arena_delete_success").replace("{name}", event.getArena().getName()));
    }
}
