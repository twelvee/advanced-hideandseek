package ru.bandamc.advancedHNS.command;

import com.destroystokyo.paper.ClientOption;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;
import ru.bandamc.advancedHNS.entities.Arena;

public class SelectBlockGUICommandHandler implements CommandHandler {

    @Override
    public boolean Handle(CommandSender sender, Command command, String label, String[] args) {
        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        if (sender instanceof Player player) {
            String language = player.getClientOption(ClientOption.LOCALE);
            Arena arena = plugin.playerArena.get(player);
            if (arena == null) {
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.not_in_arena"));
                return true;
            }
            if (arena.getStatus() != 3) {
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.select_block_error"));
                return true;
            }
            boolean found = false;
            for(var hider : arena.getHiders()) {
                if (hider.getPlayer() == player) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.select_block_error"));
                return true;
            }

            Inventory menu = Bukkit.createInventory(player, 2*9, Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".menus.select_block_menu")));
            // Add items to the menu

            for (var block : arena.getAvailableBlocks()) {
                ItemStack is = new ItemStack(block);
                menu.addItem(is);
            }

            player.openInventory(menu);
            player.setMetadata("HNSOpenedInventory", new FixedMetadataValue(plugin, menu));
            player.setMetadata("HNSOpenedInventoryName", new FixedMetadataValue(plugin, "_select_block_gui_"));
            return true;
        }

        Bukkit.getLogger().info(AdvancedHNS.HNS_PREFIX + " you can join arena only if you're player.");
        return true;
    }
}
