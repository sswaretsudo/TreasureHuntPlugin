package jp.norvell.treasurehunt.Helpers;

import jp.norvell.treasurehunt.Objects.Enums.MessageLevel;
import org.bukkit.ChatColor;

public class MessageHelper {
    public static String CreateMessage(MessageLevel level, String str) {
        StringBuilder sb = new StringBuilder();
        switch (level)
        {
            case SUCCESS -> sb.append(ChatColor.GREEN).append("[TreasureHunt Plugin > SUCCESS] : ");
            case INFO -> sb.append(ChatColor.WHITE).append("[TreasureHunt Plugin > INFO] : ");
            case WARN -> sb.append(ChatColor.YELLOW).append("[TreasureHunt Plugin > WARN] : ");
            case ERROR -> sb.append(ChatColor.RED).append("[TreasureHunt Plugin > ERROR] : ");
        }
        sb.append(str);
        return sb.toString();
    }
}
