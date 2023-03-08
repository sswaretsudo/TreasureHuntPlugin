package jp.norvell.treasurehunt.Commands;

import jp.norvell.treasurehunt.Helpers.MessageHelper;
import jp.norvell.treasurehunt.Objects.Command.CommandContainer;
import jp.norvell.treasurehunt.Objects.Enums.MessageLevel;
import jp.norvell.treasurehunt.TreasureHunt;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandManager
{
    private final Plugin _plugin;
    private final ArrayList<CommandContainer> _cmds;

    public CommandManager(Plugin plugin) {
        _plugin = plugin;
        _cmds = new ArrayList<>();
    }

    public boolean Execute(CommandSender sender, Command command, String label, String[] args) {

        boolean res = false;
        for (CommandContainer cmd : _cmds)
        {
            if (cmd.GetName().equalsIgnoreCase(command.getName()))
            {
                res = cmd.Execute(sender, command, label, args);
                break;
            }
        }

        if (!res) {
            sender.sendMessage(MessageHelper.CreateMessage(MessageLevel.ERROR, "Invalid Arguments or Doesn't set ProcedureModule"));
        }

        return true;
    }

    public List<String> Complete(Command command, String[] args) {

        for (CommandContainer cmd : _cmds)
        {
            if (cmd.GetName().equalsIgnoreCase(command.getName()))
            {
                return cmd.Complete(args);
            }
        }

        return null;
    }

    public void RegisterCommand(CommandContainer cmdContainer, CommandReceiver executor) {
        _cmds.add(cmdContainer);
        Objects.requireNonNull(((TreasureHunt)_plugin).getCommand(cmdContainer.GetName())).setExecutor(executor);
    }
}
