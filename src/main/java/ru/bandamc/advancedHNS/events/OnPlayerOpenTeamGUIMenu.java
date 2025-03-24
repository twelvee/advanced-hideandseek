package ru.bandamc.advancedHNS.events;

import com.destroystokyo.paper.ClientOption;
import fr.mrmicky.fastboard.adventure.FastBoard;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;
import ru.bandamc.advancedHNS.api.events.PlayerOpenTeamSelectMenu;
import ru.bandamc.advancedHNS.entities.Arena;

public class OnPlayerOpenTeamGUIMenu implements Listener {
    @EventHandler
    public void onPlayerOpenedMenu(PlayerOpenTeamSelectMenu event) {
        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        String language = event.getPlayer().getClientOption(ClientOption.LOCALE);
        Arena arena = plugin.playerArena.get(event.getPlayer());
        if (arena == null) {
            event.getPlayer().sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.not_in_arena"));
            return;
        }
        if (arena.getStatus() != 1 && arena.getStatus() != 2) {
            event.getPlayer().sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.change_team_error"));
            return;
        }

        Inventory menu = Bukkit.createInventory(event.getPlayer(), 9, Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".menus.select_team_menu")));
        // Add items to the menu
        ItemStack seekerTeam = new ItemStack(Material.IRON_SWORD);
        ItemMeta seekerMeta = seekerTeam.getItemMeta();
        seekerMeta.displayName(Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.seeker")));
        seekerTeam.setItemMeta(seekerMeta);
        menu.setItem(3, seekerTeam);

        ItemStack hiderTeam = new ItemStack(Material.BOOK);
        ItemMeta hiderMeta = seekerTeam.getItemMeta();
        hiderMeta.displayName(Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".scoreboard.hider")));
        hiderTeam.setItemMeta(hiderMeta);
        menu.setItem(5, hiderTeam);

        event.getPlayer().openInventory(menu);
        event.getPlayer().setMetadata("HNSOpenedInventory", new FixedMetadataValue(plugin, menu));
        event.getPlayer().setMetadata("HNSOpenedInventoryName", new FixedMetadataValue(plugin, "_select_team_gui_"));
    }
}
