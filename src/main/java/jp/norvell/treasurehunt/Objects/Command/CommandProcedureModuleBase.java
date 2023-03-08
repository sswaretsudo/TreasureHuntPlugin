package jp.norvell.treasurehunt.Objects.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class CommandProcedureModuleBase
{
    public boolean Execute(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
