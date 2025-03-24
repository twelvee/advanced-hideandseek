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
import ru.bandamc.advancedHNS.api.events.PlayerOpenItemsMenu;
import ru.bandamc.advancedHNS.entities.Arena;

public class SelectBlockGUICommandHandler implements CommandHandler {

    @Override
    public boolean Handle(CommandSender sender, Command command, String label, String[] args) {
        AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
        if (sender instanceof Player player) {
            PlayerOpenItemsMenu event = new PlayerOpenItemsMenu(player);
            Bukkit.getPluginManager().callEvent(event);
            return true;
        }

        Bukkit.getLogger().info(AdvancedHNS.HNS_PREFIX + " you can join arena only if you're player.");
        return true;
    }
}
