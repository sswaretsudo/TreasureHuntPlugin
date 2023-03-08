package jp.norvell.treasurehunt.Commands;

import jp.norvell.treasurehunt.Helpers.MessageHelper;
import jp.norvell.treasurehunt.Managers.ConfigManager;
import jp.norvell.treasurehunt.Objects.Command.CommandProcedureModuleBase;
import jp.norvell.treasurehunt.Objects.Enums.MessageLevel;
import jp.norvell.treasurehunt.TreasureHunt;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class GameTimeConfigCommandProcedureModule extends CommandProcedureModuleBase
{
    @Override
    public boolean Execute (CommandSender sender, Command command, String label, String[] args)
    {
        ConfigManager cfgMgr = TreasureHunt.INSTANCE.GetConfigManager();
        int t;
        try {
            t = Integer.parseInt(String.valueOf(args[2]));
        }catch (NumberFormatException e) {
            sender.sendMessage(MessageHelper.CreateMessage(MessageLevel.ERROR, "GameTime must be specified numerically"));
            return true;
        }

        cfgMgr.SetTime(t);

        sender.sendMessage(MessageHelper.CreateMessage(MessageLevel.SUCCESS, "Config updated successfully"));
        return true;
    }
}
