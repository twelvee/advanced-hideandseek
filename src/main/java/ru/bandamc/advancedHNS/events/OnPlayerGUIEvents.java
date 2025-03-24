package ru.bandamc.advancedHNS.events;

import com.destroystokyo.paper.ClientOption;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;
import ru.bandamc.advancedHNS.api.events.*;
import ru.bandamc.advancedHNS.entities.Arena;

import java.util.Locale;
import java.util.Objects;

public class OnPlayerGUIEvents implements Listener {
    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        if (!player.hasMetadata("HNSOpenedInventoryName")) {
            if (plugin.playerArena.get(player).isReadyToJoin()) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    ItemStack item = event.getItem();
                    if (item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 990099) {
                        event.setCancelled(true);
                        PlayerOpenTeamSelectMenu e = new PlayerOpenTeamSelectMenu(player);
                        Bukkit.getPluginManager().callEvent(e);
                    }
                    if (item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 990098) {
                        event.setCancelled(true);
                        ArenaLeaveEvent e = new ArenaLeaveEvent(player, plugin.playerArena.get(player));
                        Bukkit.getPluginManager().callEvent(e);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        // disable all clicks in inventories
        event.setCancelled(true);

        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        String language = player.getClientOption(ClientOption.LOCALE);
        if (player.hasMetadata("HNSOpenedInventoryName")) {
            String menuName = (String) player.getMetadata("HNSOpenedInventoryName").get(0).value();

            if (menuName != null && menuName.equalsIgnoreCase("_select_team_gui_")) {
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

            if (menuName != null && menuName.equalsIgnoreCase("_select_block_gui_")) {

                if (event.getInventory() == player.getMetadata("HNSOpenedInventory").get(0).value()) {
                    // select seeker
                    Arena arena = plugin.playerArena.get(player);
                    if (arena == null) {
                        player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.not_in_arena"));
                        event.getInventory().close();
                        return;
                    }
                    if (arena.getStatus() != 3) {
                        player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.change_team_error"));
                        event.getInventory().close();
                        return;
                    }
                    boolean found = false;
                    for (var hider : arena.getHiders()) {
                        if (hider.getPlayer() == player) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.select_block_error"));
                        event.getInventory().close();
                        return;
                    }

                    HiderSelectBlockEvent e = new HiderSelectBlockEvent(player, arena, event.getCurrentItem().getType());
                    Bukkit.getPluginManager().callEvent(e);

                    if (!e.isCancelled()) {
                        player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.select_block_success").replace("{material}", e.getMaterial().name()));
                        event.getInventory().close();
                    }
                }
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
            player.removeMetadata("HNSOpenedInventoryName", plugin);
            if (menu != null) {
                menu.clear();
            }
        }
    }
}