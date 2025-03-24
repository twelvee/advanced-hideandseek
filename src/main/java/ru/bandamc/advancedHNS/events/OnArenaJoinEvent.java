package ru.bandamc.advancedHNS.events;

import com.destroystokyo.paper.ClientOption;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;
import ru.bandamc.advancedHNS.api.events.ArenaDeleteEvent;
import ru.bandamc.advancedHNS.api.events.ArenaJoinEvent;

import static org.bukkit.Bukkit.getServer;

public class OnArenaJoinEvent implements Listener {
    @EventHandler
    public void onArenaJoin(ArenaJoinEvent event) {
        if (!event.getArena().isReadyToJoin()) {
            event.setCancelled(true);
            return;
        }

        if(event.getArena().getPlayers().isEmpty()) {
            event.getArena().setStatus(2); // set "Waiting for players status"
        }

        event.getArena().joinHiders(event.getPlayer());
        event.getPlayer().setMetadata("currentArena", new FixedMetadataValue(JavaPlugin.getPlugin(AdvancedHNS.class), event.getArena()));
        String language = event.getPlayer().getClientOption(ClientOption.LOCALE);

        // working around items for players
        event.getPlayer().getInventory().clear(); // clear inventory
        ItemStack selectTeamItem = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta selectTeamMeta = selectTeamItem.getItemMeta();
        selectTeamMeta.setCustomModelData(990099);

        selectTeamMeta.displayName(Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".menus.select_team_menu")));
        selectTeamItem.setItemMeta(selectTeamMeta);
        event.getPlayer().getInventory().setItem(0, selectTeamItem);

        ItemStack leaveArenaItem = new ItemStack(Material.OAK_DOOR);
        ItemMeta leaveArenaItemMeta = leaveArenaItem.getItemMeta();
        leaveArenaItemMeta.setCustomModelData(990098);

        leaveArenaItemMeta.displayName(Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".menus.leave_arena")));
        leaveArenaItem.setItemMeta(leaveArenaItemMeta);
        event.getPlayer().getInventory().setItem(8, leaveArenaItem);
    }
}
