package ru.bandamc.advancedHNS.command;

import com.destroystokyo.paper.ClientOption;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;
import ru.bandamc.advancedHNS.api.events.ArenaInitEvent;
import ru.bandamc.advancedHNS.database.ArenaRepository;
import ru.bandamc.advancedHNS.entities.Arena;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateArenaCommandHandler implements CommandHandler {
    @Override
    public boolean Handle(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 4) {
            // arena name not specified.
            return false;
        }
        if (sender instanceof Player player) {
            String arenaName = args[3]; // hns admin arena create ->name<-
            AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
            if (!arenaName.equalsIgnoreCase(" ")) {
                String language = player.getClientOption(ClientOption.LOCALE);

                if (plugin.arenaEdits.containsKey(player)) {
                    player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.user_already_edit_arena").replace("{name}", plugin.arenaEdits.get(player).getName()));
                    return false;
                }

                try {
                    ResultSet arena = plugin.getArenaRepository().getArenaInfo(arenaName);
                    if (arena.isBeforeFirst()) {
                        player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.arena_already_exists").replace("{name}", arenaName));
                        return false;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                //plugin.getArenaRepository().createArena(player.getWorld().getName(), arenaName);

                Arena arena = new Arena();
                arena.setName(arenaName);
                arena.setWorld(player.getWorld().getName());
                arena.setMaxHiders(1);
                arena.setMaxSeekers(1);
                arena.setStatus(0);
                arena.setPos1(player.getWorld().getName() + "|0|0|0");
                arena.setPos2(player.getWorld().getName() + "|0|0|0");
                arena.setLobbyPos(player.getWorld().getName() + "|0|0|0");
                arena.setSpecPos(player.getWorld().getName() + "|0|0|0");
                arena.setHidersSpawnPos(player.getWorld().getName() + "|0|0|0");
                arena.setSeekersSpawnPos(player.getWorld().getName() + "|0|0|0");

                ArenaInitEvent event = new ArenaInitEvent(player, arena);
                Bukkit.getPluginManager().callEvent(event);

                if (!event.isCancelled())
                    plugin.arenaEdits.put(event.getPlayer(), event.getArena());

                return true;

            }
        }
        return false;
    }
}
