package ru.bandamc.advancedHNS.events;

import com.destroystokyo.paper.ClientOption;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;
import ru.bandamc.advancedHNS.api.events.PlayerOpenItemsMenu;
import ru.bandamc.advancedHNS.api.events.PlayerOpenTeamSelectMenu;
import ru.bandamc.advancedHNS.entities.Arena;

public class OnPlayerOpenItemsGUIMenu implements Listener {
    @EventHandler
    public void onPlayerOpenedMenu(PlayerOpenItemsMenu event) {
        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        Player player = event.getPlayer();
        String language = player.getClientOption(ClientOption.LOCALE);
        Arena arena = plugin.playerArena.get(player);
        if (arena == null) {
            player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.not_in_arena"));
            return;
        }
        if (arena.getStatus() != 3) {
            player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.select_block_error"));
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
            return;
        }

        Inventory menu = Bukkit.createInventory(player, 2 * 9, Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".menus.select_block_menu")));
        // Add items to the menu

        for (var block : arena.getAvailableBlocks()) {
            ItemStack is = new ItemStack(block);
            menu.addItem(is);
        }

        player.openInventory(menu);
        player.setMetadata("HNSOpenedInventory", new FixedMetadataValue(plugin, menu));
        player.setMetadata("HNSOpenedInventoryName", new FixedMetadataValue(plugin, "_select_block_gui_"));
    }
}
