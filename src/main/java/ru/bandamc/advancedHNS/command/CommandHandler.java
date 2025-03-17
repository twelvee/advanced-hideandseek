package ru.bandamc.advancedHNS.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.sql.SQLException;

public interface CommandHandler
{
    public boolean Handle(CommandSender sender, Command command, String label, String[] args) throws SQLException;
}
