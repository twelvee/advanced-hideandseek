package ru.bandamc.advancedHNS.events;

import com.destroystokyo.paper.ClientOption;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;
import ru.bandamc.advancedHNS.api.events.ArenaInitEvent;

public class OnArenaInitEvent implements Listener {
    @EventHandler
    public void onArenaInit(ArenaInitEvent event) {
        String language = event.getPlayer().getClientOption(ClientOption.LOCALE);
        event.getPlayer().sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.arena_create_success").replace("{name}", event.getArena().getName()));
    }
}
