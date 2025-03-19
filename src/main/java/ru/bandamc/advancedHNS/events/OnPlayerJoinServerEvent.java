package ru.bandamc.advancedHNS.events;

import com.destroystokyo.paper.ClientOption;
import fr.mrmicky.fastboard.adventure.FastBoard;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;

public class OnPlayerJoinServerEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        // teleport players to spawn on every join.
        if (plugin.getConfig().getBoolean("teleport_to_spawn_on_join"))
            event.getPlayer().teleport(Bukkit.getWorld(plugin.getConfig().getString("spawn_world", "world")).getSpawnLocation());

        event.getPlayer().removeMetadata("currentArena", plugin);

        FastBoard board = new FastBoard(event.getPlayer());
        String language = event.getPlayer().getClientOption(ClientOption.LOCALE);
        board.updateTitle(Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.header")));
/*        board.updateLines(
                Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.youre_in_arena")),
                Component.text(name),
                Component.text(""),
                Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.status").replace("{status}", LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.status_"+status))),
                Component.text(""),
                Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.hider")),
                Component.text(""),
                Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.footer"))
        );*/

        plugin.boards.put(event.getPlayer(), board);
    }
}
