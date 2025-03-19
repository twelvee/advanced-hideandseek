package ru.bandamc.advancedHNS.command;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.bandamc.advancedHNS.AdvancedHNS;
import ru.bandamc.advancedHNS.entities.Arena;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandExecutor implements org.bukkit.command.CommandExecutor, TabCompleter {
    private final AboutCommandHandler aboutCommandHandler;
    private final HelpCommandHandler helpCommandHandler;
    private final ReloadCommandHandler reloadCommandHandler;
    private final ChangeWorldCommandHandler worldTpCommandHandler;
    private final CreateArenaCommandHandler createArenaCommandHandler;
    private final DeleteArenaCommandHandler deleteArenaCommandHandler;
    private final EditArenaCommandHandler editArenaCommandHandler;
    private final SaveArenaCommandHandler saveArenaCommandHandler;
    private final SetPos1CommandHandler setPos1CommandHandler;
    private final SetPos2CommandHandler setPos2CommandHandler;
    private final SetMaxHidersCommandHandler setMaxHidersCommandHandler;
    private final SetMaxSeekersCommandHandler setMaxSeekersCommandHandler;
    private final SetHidersSpawnPosCommandHandler setHidersSpawnPosCommandHandler;
    private final SetSeekersSpawnPosCommandHandler setSeekersSpawnPosCommandHandler;
    private final SetLobbyPosCommandHandler setLobbyPosCommandHandler;
    private final SetSpecPosCommandHandler setSpecPosCommandHandler;
    private final SetMinHidersCommandHandler setMinHidersCommandHandler;
    private final SetMinSeekersCommandHandler setMinSeekersCommandHandler;
    private final SetMaxPlayersCommandHandler setMaxPlayersCommandHandler;
    private final StartArenaCommandHandler startArenaCommandHandler;

    private final JoinCommandHandler joinCommandHandler;
    private final LeaveCommandHandler leaveCommandHandler;
    private final SelectHiderCommandHandler selectHiderCommandHandler;
    private final SelectSeekerCommandHandler selectSeekerCommandHandler;

    public CommandExecutor() {
        aboutCommandHandler = new AboutCommandHandler();
        helpCommandHandler = new HelpCommandHandler();
        reloadCommandHandler = new ReloadCommandHandler();
        worldTpCommandHandler = new ChangeWorldCommandHandler();
        createArenaCommandHandler = new CreateArenaCommandHandler();
        deleteArenaCommandHandler = new DeleteArenaCommandHandler();
        editArenaCommandHandler = new EditArenaCommandHandler();
        saveArenaCommandHandler = new SaveArenaCommandHandler();
        setPos1CommandHandler = new SetPos1CommandHandler();
        setPos2CommandHandler = new SetPos2CommandHandler();
        setMaxHidersCommandHandler = new SetMaxHidersCommandHandler();
        setMaxSeekersCommandHandler = new SetMaxSeekersCommandHandler();
        setHidersSpawnPosCommandHandler = new SetHidersSpawnPosCommandHandler();
        setSeekersSpawnPosCommandHandler = new SetSeekersSpawnPosCommandHandler();
        setLobbyPosCommandHandler = new SetLobbyPosCommandHandler();
        setSpecPosCommandHandler = new SetSpecPosCommandHandler();
        setMinHidersCommandHandler = new SetMinHidersCommandHandler();
        setMinSeekersCommandHandler = new SetMinSeekersCommandHandler();
        setMaxPlayersCommandHandler = new SetMaxPlayersCommandHandler();
        startArenaCommandHandler = new StartArenaCommandHandler();

        joinCommandHandler = new JoinCommandHandler();
        leaveCommandHandler = new LeaveCommandHandler();
        selectHiderCommandHandler = new SelectHiderCommandHandler();
        selectSeekerCommandHandler = new SelectSeekerCommandHandler();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            return aboutCommandHandler.Handle(sender, command, label, args);
        }
        if (args[0].equalsIgnoreCase("help")) {
            return helpCommandHandler.Handle(sender, command, label, args);
        } else if (args[0].equalsIgnoreCase("reload")) {
            return reloadCommandHandler.Handle(sender, command, label, args);
        } else if (args[0].equalsIgnoreCase("join")) {
            return joinCommandHandler.Handle(sender, command, label, args);
        } else if (args[0].equalsIgnoreCase("leave")) {
            return leaveCommandHandler.Handle(sender, command, label, args);
        } else if (args[0].equalsIgnoreCase("select_hider")) {
            return selectHiderCommandHandler.Handle(sender, command, label, args);
        } else if (args[0].equalsIgnoreCase("select_seeker")) {
            return selectSeekerCommandHandler.Handle(sender, command, label, args);
        } else if (args[0].equalsIgnoreCase("admin")) {
            if (args.length >= 2) {
                if (args[1].equalsIgnoreCase("world")) {
                    // hns admin world
                    if (args.length >= 3) {
                        if (args[2].equalsIgnoreCase("tp")) {
                            return worldTpCommandHandler.Handle(sender, command, label, args);
                        }
                    }
                }
                if (args[1].equalsIgnoreCase("arena")) {
                    // hns admin arena
                    if (args.length >= 3) {
                        if (args[2].equalsIgnoreCase("create")) {
                            // hns admin arena create [name]
                            return createArenaCommandHandler.Handle(sender, command, label, args);
                        }
                        if (args[2].equalsIgnoreCase("delete")) {
                            return deleteArenaCommandHandler.Handle(sender, command, label, args);
                        }
                        if (args[2].equalsIgnoreCase("edit")) {
                            return editArenaCommandHandler.Handle(sender, command, label, args);
                        }
                        if (args[2].equalsIgnoreCase("save")) {
                            return saveArenaCommandHandler.Handle(sender, command, label, args);
                        }
                        if (args[2].equalsIgnoreCase("start")) {
                            return startArenaCommandHandler.Handle(sender, command, label, args);
                        }
                        if (args[2].equalsIgnoreCase("pos1")) {
                            return setPos1CommandHandler.Handle(sender, command, label, args);
                        }
                        if (args[2].equalsIgnoreCase("pos2")) {
                            return setPos2CommandHandler.Handle(sender, command, label, args);
                        }
                        if (args[2].equalsIgnoreCase("spec")) {
                            return setSpecPosCommandHandler.Handle(sender, command, label, args);
                        }
                        if (args[2].equalsIgnoreCase("lobby")) {
                            return setLobbyPosCommandHandler.Handle(sender, command, label, args);
                        }
                        if (args[2].equalsIgnoreCase("seekers_spawn")) {
                            return setSeekersSpawnPosCommandHandler.Handle(sender, command, label, args);
                        }
                        if (args[2].equalsIgnoreCase("hiders_spawn")) {
                            return setHidersSpawnPosCommandHandler.Handle(sender, command, label, args);
                        }
                        if (args[2].equalsIgnoreCase("max_hiders")) {
                            return setMaxHidersCommandHandler.Handle(sender, command, label, args);
                        }
                        if (args[2].equalsIgnoreCase("max_seekers")) {
                            return setMaxSeekersCommandHandler.Handle(sender, command, label, args);
                        }
                        if (args[2].equalsIgnoreCase("max_players")) {
                            return setMaxPlayersCommandHandler.Handle(sender, command, label, args);
                        }
                        if (args[2].equalsIgnoreCase("min_seekers")) {
                            return setMinSeekersCommandHandler.Handle(sender, command, label, args);
                        }
                        if (args[2].equalsIgnoreCase("min_hiders")) {
                            return setMinHidersCommandHandler.Handle(sender, command, label, args);
                        }
                    }
                }
            }
        }
        sender.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " Unknown command.");
        return false;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1 && args[0].isEmpty()) {
            return Arrays.asList("help", "admin", "reload", "join", "leave", "select_hider", "select_hider");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("help")) {
            return List.of();
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
            AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
            ArrayList<String> availableArenas = new ArrayList<>();
            for (Arena arena : plugin.arenas.values()) {
                availableArenas.add(arena.getName());
            }
            return availableArenas;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("admin")) {
            return Arrays.asList("world", "arena");
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("world")) {
            return List.of("tp");
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("arena")) {
            return Arrays.asList("start", "create", "delete", "edit", "save", "pos1", "pos2", "spec", "lobby", "seekers_spawn", "hiders_spawn", "max_hiders", "max_seekers", "max_players", "min_hiders", "min_seekers");
        }

        // create
        if (args.length == 4 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("arena") && (args[2].equalsIgnoreCase("edit") || args[2].equalsIgnoreCase("delete"))) {
            AdvancedHNS plugin = JavaPlugin.getPlugin(AdvancedHNS.class);
            // todo: may rewrite it with plugin.arenas hashmap?
            ArrayList<String> arenas = new ArrayList<>();
            ResultSet allArenas;
            try {
                allArenas = plugin.getArenaRepository().getAllArenas();
            } catch (SQLException e) {
                return List.of();
            }
            while (true) {
                try {
                    if (!allArenas.next()) break;
                    arenas.add(allArenas.getString("name"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            if (sender instanceof Player player && plugin.arenaEdits.containsKey(player)) {
                arenas.add(plugin.arenaEdits.get(player).getName());
            }
            return arenas;
        }

        if (args.length == 4 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("world") && args[2].equalsIgnoreCase("tp")) {
            ArrayList<String> worlds = new ArrayList<>();
            for (World world : Bukkit.getWorlds()) {
                worlds.add(world.getName());
            }
            return worlds;
        }

        return List.of();
    }
}