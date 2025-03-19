package ru.bandamc.advancedHNS.command;

import com.destroystokyo.paper.ClientOption;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;
import ru.bandamc.advancedHNS.api.events.ArenaStartEvent;
import ru.bandamc.advancedHNS.entities.Arena;

public class SelectTeamGUICommandHandler implements CommandHandler {

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
            if (arena.getStatus() != 1 && arena.getStatus() != 2) {
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".general.change_team_error"));
                return true;
            }

            Inventory menu = Bukkit.createInventory(player, 9, Component.text(LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".menus.select_team_menu")));
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

            player.openInventory(menu);
            player.setMetadata("HNSOpenedInventory", new FixedMetadataValue(plugin, menu));
            return true;
        }

        Bukkit.getLogger().info(AdvancedHNS.HNS_PREFIX + " you can join arena only if you're player.");
        return true;
    }
}
