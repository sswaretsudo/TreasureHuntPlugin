package jp.norvell.treasurehunt.Objects.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandArgumentModule
{
    private final String _arg;
    private final ArrayList<CommandArgumentModule> _args;
    private ArrayList<String> _optCmp;
    private CommandProcedureModuleBase _module;

    public CommandArgumentModule(@NotNull String arg) {
        _arg = arg;
        _args = new ArrayList<>();
    }

    public boolean Execute(CommandSender sender, Command command, String label, String[] args) {
        if (_args.size() == 0) {
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

    public List<String> Complete(String[] args, int argc) {
        int argcc = argc + 1;
        if (_args.size() == 0)
        {
            ArrayList<String> dest = new ArrayList<>();
            for (String s : _optCmp)
            {
                if (s.startsWith(args[argcc])) dest.add(s);
            }
            return dest;
        }
        else {
            if (args.length - 1 == argcc){
                for (CommandArgumentModule arg : _args)
                {
                    if (arg.GetName().startsWith(args[argcc]))
                        return Collections.singletonList(arg.GetName());
                }
            }else {
                for (CommandArgumentModule arg : _args)
                {
                    if (arg.GetName().equalsIgnoreCase(args[argcc]))
                        return arg.Complete(args, argcc);
                }
            }
        }
        return null;
    }

    public void RegisterArgument(CommandArgumentModule arg) {
        _args.add(arg);
    }

    public String GetName() {
        return _arg;
    }

    public void SetCommandProcedureModule(CommandProcedureModuleBase mdl) {
        _module = mdl;
    }

    public void SetOptionalCompleteList(ArrayList<String> strs) {
        _optCmp = strs;
    }
}
