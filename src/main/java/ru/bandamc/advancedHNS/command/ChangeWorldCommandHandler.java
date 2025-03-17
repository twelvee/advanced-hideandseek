package ru.bandamc.advancedHNS.command;

import com.destroystokyo.paper.ClientOption;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;

public class ChangeWorldCommandHandler implements CommandHandler {
    @Override
    public boolean Handle(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 4) {
            // world not specified.
            return false;
        }
        if (sender instanceof Player player) {
            World world = Bukkit.getWorld(args[3]);
            if (world != null) {
                player.teleport(world.getSpawnLocation());
                String language = player.getClientOption(ClientOption.LOCALE);
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.teleported_to_world").replace("{world}", args[3]));
                return true;
            }
        }
        return false;
    }
}
