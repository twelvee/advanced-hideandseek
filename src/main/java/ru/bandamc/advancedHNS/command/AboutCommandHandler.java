package ru.bandamc.advancedHNS.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.bandamc.advancedHNS.AdvancedHNS;

public class AboutCommandHandler implements CommandHandler {

    @Override
    public boolean Handle(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(AdvancedHNS.HNS_PREFIX + " Server running Advanced Hide And Seek plugin.");
            return true;
        }

        Bukkit.getLogger().info(AdvancedHNS.HNS_PREFIX + " Server running Advanced Hide And Seek plugin.");
        return true;
    }
    
}
