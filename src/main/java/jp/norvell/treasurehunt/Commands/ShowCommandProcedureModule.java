package jp.norvell.treasurehunt.Commands;

import jp.norvell.treasurehunt.Helpers.MessageHelper;
import jp.norvell.treasurehunt.Objects.Command.CommandProcedureModuleBase;
import jp.norvell.treasurehunt.Objects.Enums.MessageLevel;
import jp.norvell.treasurehunt.Objects.ScoreItem;
import jp.norvell.treasurehunt.TreasureHunt;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class ShowCommandProcedureModule extends CommandProcedureModuleBase
{
    @Override
    public boolean Execute(CommandSender sender, Command command, String label, String[] args) {
        StringBuilder sb = new StringBuilder();

        sb.append(
            """
    
            ======= Items =======
    
            """);

        ArrayList<ScoreItem> pis = TreasureHunt.INSTANCE.GetConfigManager().GetItems();
        if (pis.size() == 0) {
            sender.sendMessage(MessageHelper.CreateMessage(MessageLevel.INFO, "Registered item does not exist"));
            return true;
        }
        for (ScoreItem pi: pis) {
            sb.append(pi.GetMaterial().name()).append(" : ").append(pi.GetScore()).append("\n");
        }
       sb.append("\n====================");

        sender.sendMessage(MessageHelper.CreateMessage(MessageLevel.INFO, sb.toString()));

        return true;
    }
}
