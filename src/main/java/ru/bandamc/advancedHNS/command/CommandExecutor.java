package ru.bandamc.advancedHNS.command;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.bandamc.advancedHNS.AdvancedHNS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandExecutor implements org.bukkit.command.CommandExecutor, TabCompleter {
    private final AboutCommandHandler aboutCommandHandler;
    private final HelpCommandHandler helpCommandHandler;
    private final ChangeWorldCommandHandler worldTpCommandHandler;
    private final CreateArenaCommandHandler createArenaCommandHandler;
    private final DeleteArenaCommandHandler deleteArenaCommandHandler;
    private final EditArenaCommandHandler editArenaCommandHandler;
    private final SaveArenaCommandHandler saveArenaCommandHandler;

    public CommandExecutor() {
        aboutCommandHandler = new AboutCommandHandler();
        helpCommandHandler = new HelpCommandHandler();
        worldTpCommandHandler = new ChangeWorldCommandHandler();
        createArenaCommandHandler = new CreateArenaCommandHandler();
        deleteArenaCommandHandler = new DeleteArenaCommandHandler();
        editArenaCommandHandler = new EditArenaCommandHandler();
        saveArenaCommandHandler = new SaveArenaCommandHandler();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            return aboutCommandHandler.Handle(sender, command, label, args);
        }
        if (args[0].equalsIgnoreCase("help")) {
            return helpCommandHandler.Handle(sender, command, label, args);
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
            return Arrays.asList("help", "admin");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("help")) {
            return List.of();
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("admin")) {
            return Arrays.asList("world", "arena");
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("world")) {
            return List.of("tp");
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("arena")) {
            return Arrays.asList("create", "delete", "edit", "save");
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