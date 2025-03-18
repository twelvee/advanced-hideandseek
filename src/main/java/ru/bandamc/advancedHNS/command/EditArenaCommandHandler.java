package ru.bandamc.advancedHNS.command;

import com.destroystokyo.paper.ClientOption;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.LocalizationManager;
import ru.bandamc.advancedHNS.api.events.ArenaEditEvent;
import ru.bandamc.advancedHNS.entities.Arena;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EditArenaCommandHandler implements CommandHandler {
    @Override
    public boolean Handle(CommandSender sender, Command command, String label, String[] args) throws SQLException {
        if (args.length < 4) {
            // arena name not specified.
            return false;
        }
        if (sender instanceof Player player) {
            String arenaName = args[3]; // hns admin arena edit ->name<-
            AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
            if (!arenaName.equalsIgnoreCase(" ")) {
                String language = player.getClientOption(ClientOption.LOCALE);
                if (plugin.arenaEdits.containsKey(player)) {
                    player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.user_already_edit_arena").replace("{name}", plugin.arenaEdits.get(player).getName()));
                    return false;
                }

                ResultSet resultSet;
                try {
                    resultSet = plugin.getArenaRepository().getArenaInfo(arenaName);
                    if (!resultSet.isBeforeFirst()) {
                        player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.arena_not_found").replace("{name}", arenaName));
                        return false;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                try {
                    plugin.getArenaRepository().changeArenaStatus(arenaName, 0);
                    Arena arena = new Arena();

                    if (resultSet.next()) {
                        arena.setName(resultSet.getString("name"));
                        arena.setWorld(resultSet.getString("world"));
                        arena.setMaxHiders(resultSet.getInt("max_hiders"));
                        arena.setMaxSeekers(resultSet.getInt("max_seekers"));
                        arena.setPos1(resultSet.getString("pos1"));
                        arena.setPos2(resultSet.getString("pos2"));
                        arena.setSpecPos(resultSet.getString("spec_pos"));
                        arena.setLobbyPos(resultSet.getString("lobby_pos"));
                        arena.setSeekersSpawnPos(resultSet.getString("seekers_spawn_point"));
                        arena.setHidersSpawnPos(resultSet.getString("hiders_spawn_point"));
                        arena.setStatus(0);
                    } else {
                        player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.arena_edit_start_failed").replace("{name}", arenaName));
                        return false;
                    }

                    ArenaEditEvent event = new ArenaEditEvent(player, arena);
                    Bukkit.getPluginManager().callEvent(event);

                    if (!event.isCancelled())
                        plugin.arenaEdits.put(event.getPlayer(), event.getArena());

                    return true;
                } catch (SQLException e) {
                    player.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " " + LocalizationManager.getInstance().getLocalization(LocalizationManager.getInstance().getLocale(language) + ".admin.arena_edit_start_failed").replace("{name}", arenaName));
                    Bukkit.getLogger().info(e.getMessage());
                    throw e;

                }
            }
        }
        return false;
    }
}
