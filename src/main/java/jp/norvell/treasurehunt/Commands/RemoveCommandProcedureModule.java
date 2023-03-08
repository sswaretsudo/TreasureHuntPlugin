package jp.norvell.treasurehunt.Commands;

import jp.norvell.treasurehunt.Helpers.MessageHelper;
import jp.norvell.treasurehunt.Managers.ConfigManager;
import jp.norvell.treasurehunt.Objects.Command.CommandProcedureModuleBase;
import jp.norvell.treasurehunt.Objects.Enums.MessageLevel;
import jp.norvell.treasurehunt.Objects.ScoreItem;
import jp.norvell.treasurehunt.TreasureHunt;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class RemoveCommandProcedureModule extends CommandProcedureModuleBase
{
    @Override
    public boolean Execute (CommandSender sender, Command command, String label, String[] args)
    {
        ConfigManager cfgMgr = TreasureHunt.INSTANCE.GetConfigManager();
        if (args.length == 2) {
            Material m = Material.getMaterial(args[1].toUpperCase());
            if (m == null) {
                sender.sendMessage(MessageHelper.CreateMessage(MessageLevel.ERROR, "The specified item cannot be found"));
                return true;
            }

            ScoreItem pi = cfgMgr.GetItem(m);
            if (pi == null){
                sender.sendMessage(MessageHelper.CreateMessage(MessageLevel.ERROR, "The specified item is not set in the config"));
                return true;
            }

            cfgMgr.RemoveItem(pi);

            sender.sendMessage(MessageHelper.CreateMessage(MessageLevel.SUCCESS, "Item removed successfully"));

        }
        else {
            sender.sendMessage(MessageHelper.CreateMessage(MessageLevel.ERROR, "Missing required arguments"));
            return true;
        }
        return true;
    }
}
