package ru.bandamc.advancedHNS.command;

import com.destroystokyo.paper.ClientOption;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;
import ru.bandamc.advancedHNS.entities.Arena;

public class SetHidersSpawnPosCommandHandler implements CommandHandler {
    @Override
    public boolean Handle(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
            if (plugin.arenaEdits.containsKey(player)) {
                Arena arena = plugin.arenaEdits.get(player);
                arena.setHidersSpawnPos(player.getLocation().getWorld().getName()+"|"+player.getLocation().getX()+"|"+player.getLocation().getY()+"|"+player.getLocation().getZ());
                String language = player.getClientOption(ClientOption.LOCALE);
                player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.arena_set_hiders_spawn_pos").replace("{name}", arena.getName()).replace("{pos}", player.getLocation().toString()));
                return true;
            }
        }
        return false;
    }
}
