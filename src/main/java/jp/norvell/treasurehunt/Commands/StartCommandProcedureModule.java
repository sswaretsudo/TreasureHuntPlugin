package jp.norvell.treasurehunt.Commands;

import jp.norvell.treasurehunt.Helpers.MessageHelper;
import jp.norvell.treasurehunt.Managers.GameManager;
import jp.norvell.treasurehunt.Objects.Command.CommandProcedureModuleBase;
import jp.norvell.treasurehunt.Objects.Enums.MessageLevel;
import jp.norvell.treasurehunt.Objects.ErrorInfo;
import jp.norvell.treasurehunt.TreasureHunt;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommandProcedureModule extends CommandProcedureModuleBase
{
    @Override
    public boolean Execute (CommandSender sender, Command command, String label, String[] args)
    {
        GameManager gm = TreasureHunt.INSTANCE.GetGameManager();
        if (sender instanceof Player p)
        {
            ErrorInfo errInfo = gm.Start();
            if (errInfo.GetIsError())
            {
                p.sendMessage(MessageHelper.CreateMessage(MessageLevel.ERROR, errInfo.GetErrorMessage()));
                return true;
            }
        }
        return true;
    }

}
