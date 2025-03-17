package ru.bandamc.advancedHNS.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.bandamc.advancedHNS.AdvancedHNS;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {
    private final AboutCommandHandler aboutCommandHandler;
    private final HelpCommandHandler helpCommandHandler;
    private final ChangeWorldCommandHandler worldTpCommandHandler;

    public CommandExecutor() {
        aboutCommandHandler = new AboutCommandHandler();
        helpCommandHandler = new HelpCommandHandler();
        worldTpCommandHandler = new ChangeWorldCommandHandler();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            return aboutCommandHandler.Handle(sender, command, label, args);
        } else if (args[0].equalsIgnoreCase("help")) {
            return helpCommandHandler.Handle(sender, command, label, args);
        } else if (args[0].equalsIgnoreCase("admin")) {
            if (args[1].equalsIgnoreCase("world")) {
                // hns admin world
                if (args[2].equalsIgnoreCase("tp")) {
                    return worldTpCommandHandler.Handle(sender, command, label, args);
                }
            }
        }
        sender.sendMessage(AdvancedHNS.HNS_CHAT_PREFIX + " Unknown command.");
        return false;
    }
}