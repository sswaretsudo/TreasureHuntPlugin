package jp.norvell.treasurehunt.Commands;

import jp.norvell.treasurehunt.TreasureHunt;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GameCommandExecutor implements CommandReceiver
{
    private final CommandManager _cmdMgr;

    public GameCommandExecutor() {
        _cmdMgr = TreasureHunt.INSTANCE.GetCommandManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return _cmdMgr.Execute(sender, command, label, args);
    }


    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return _cmdMgr.Complete(command, args);
    }
}
