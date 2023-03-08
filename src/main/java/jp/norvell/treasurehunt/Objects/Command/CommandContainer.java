package jp.norvell.treasurehunt.Objects.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class CommandContainer
{
    private final String _cmd;
    private final ArrayList<CommandArgumentModule> _args;
    private CommandProcedureModuleBase _module;

    public CommandContainer(@NotNull String cmd) {
        _cmd = cmd;
        _args = new ArrayList<>();
    }

    public boolean Execute(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || _args.size() == 0) {
            if (_module == null)
                return false;
            else
                return _module.Execute(sender, command, label, args);
        }

        for (String arg : args)
        {
            for (CommandArgumentModule commandArgumentModule : _args)
            {
                if (arg.equalsIgnoreCase(commandArgumentModule.GetName()))
                {
                    return commandArgumentModule.Execute(sender, command, label, args);
                }
            }
        }

        return false;
    }

    public List<String> Complete(String[] args){

        if (args.length == 0) return null;
        if (args.length == 1) {

            if (args[0].length() == 0) return _args.stream().map(CommandArgumentModule::GetName).collect(Collectors.toList());
            else {
                for (CommandArgumentModule arg : _args)
                {
                    if (arg.GetName().startsWith(args[0])) return Collections.singletonList(arg.GetName());
                }
            }
        } else {
            for (CommandArgumentModule arg : _args)
            {
                if (args[0].equalsIgnoreCase(arg.GetName()))
                    return arg.Complete(args, 0);
            }
        }

        return null;

    }

    public void RegisterArgument(CommandArgumentModule arg) {
        _args.add(arg);
    }

    public String GetName()
    {
        return _cmd;
    }

    @SuppressWarnings("unused")
    public void SetCommandProcedureModule(CommandProcedureModuleBase mdl) {
        _module = mdl;
    }
}
