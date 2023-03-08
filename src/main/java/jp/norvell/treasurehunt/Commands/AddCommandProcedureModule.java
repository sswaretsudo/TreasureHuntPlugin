package jp.norvell.treasurehunt.Commands;

import jp.norvell.treasurehunt.Helpers.MessageHelper;
import jp.norvell.treasurehunt.Managers.ConfigManager;
import jp.norvell.treasurehunt.Objects.Command.CommandProcedureModuleBase;
import jp.norvell.treasurehunt.Objects.Enums.MessageLevel;
import jp.norvell.treasurehunt.TreasureHunt;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class AddCommandProcedureModule extends CommandProcedureModuleBase
{
    @Override
    public boolean Execute (CommandSender sender, Command command, String label, String[] args)
    {
        ConfigManager cfgMgr = TreasureHunt.INSTANCE.GetConfigManager();
        if (args.length == 3) {
            Material m = Material.getMaterial(args[1].toUpperCase());
            int pts;
            if (m == null) {
                sender.sendMessage(MessageHelper.CreateMessage(MessageLevel.ERROR, "The specified item cannot be found"));
                return true;
            }
            try {
                pts = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage(MessageHelper.CreateMessage(MessageLevel.ERROR, "Points must be specified numerically"));
                return true;
            }

            cfgMgr.SetItem(m, pts);
            sender.sendMessage(MessageHelper.CreateMessage(MessageLevel.SUCCESS, "Item added successfully"));

        }
        else {
            sender.sendMessage(MessageHelper.CreateMessage(MessageLevel.ERROR, "Missing required arguments"));
            return true;
        }
        return true;
    }
}
