package ru.bandamc.advancedHNS.events;

import com.destroystokyo.paper.ClientOption;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;
import ru.bandamc.advancedHNS.api.events.JoinHiderEvent;
import ru.bandamc.advancedHNS.api.events.JoinSeekerEvent;
import ru.bandamc.advancedHNS.entities.Arena;

public class OnPlayerGUIEvents implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (player.hasMetadata("HNSOpenedInventory")) {
            event.setCancelled(true);
        }
        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        String language = player.getClientOption(ClientOption.LOCALE);
        if (event.getInventory() == player.getMetadata("HNSOpenedInventory").get(0).value() && event.getSlot() == 3) {
            // select seeker
            Arena arena = plugin.playerArena.get(player);
            if (arena == null) {
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.not_in_arena"));
                event.getInventory().close();
                return;
            }
            if (arena.getStatus() != 1 && arena.getStatus() != 2) {
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.change_team_error"));
                event.getInventory().close();
                return;
            }
            if (arena.getMaxSeekers() <= arena.getSeekers().size()) {
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.max_amount_of_seekers_reached"));
                event.getInventory().close();
                return;
            }
            JoinSeekerEvent e = new JoinSeekerEvent(player, arena);
            Bukkit.getPluginManager().callEvent(e);

            if (!e.isCancelled()) {
                arena.joinSeekers(e.getPlayer());
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.join_seeker"));
                event.getInventory().close();
                return;
            }
        }

        if (event.getInventory() == player.getMetadata("HNSOpenedInventory").get(0).value() && event.getSlot() == 5) {
            // select hider
            Arena arena = plugin.playerArena.get(player);
            if (arena == null) {
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.not_in_arena"));
                event.getInventory().close();
                return;
            }
            if (arena.getStatus() != 1 && arena.getStatus() != 2) {
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.change_team_error"));
                event.getInventory().close();
                return;
            }

            if (arena.getMaxHiders() <= arena.getHiders().size()) {
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.max_amount_of_hiders_reached"));
                event.getInventory().close();
                return;
            }
            JoinHiderEvent e = new JoinHiderEvent(player, arena);
            Bukkit.getPluginManager().callEvent(e);

            if (!e.isCancelled()) {
                arena.joinHiders(e.getPlayer());
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.join_hider"));
                event.getInventory().close();
                return;
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        Player player = (Player) event.getPlayer();
        if (player.hasMetadata("HNSOpenedInventory")) {
            Inventory menu = (Inventory) player.getMetadata("HNSOpenedInventory").get(0).value();
            player.removeMetadata("HNSOpenedInventory", plugin);
            if (menu != null) {
                menu.clear();
            }
        }
    }
}