package jp.norvell.treasurehunt.Managers;

import jp.norvell.treasurehunt.Objects.ScoreItem;
import jp.norvell.treasurehunt.Objects.ScorePlayer;
import jp.norvell.treasurehunt.Objects.ScoreTeam;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Collections;

public class TeamManager
{
    private final ScoreTeam _redTeam;
    private final ScoreTeam _blueTeam;

    private final Team _redInternalTeam;
    private final Team _blueInternalTeam;

    public TeamManager(Plugin plugin, ArrayList<ScoreItem> i) {
        _redTeam = new ScoreTeam("赤チーム", ChatColor.RED);
        _blueTeam = new ScoreTeam("青チーム", ChatColor.BLUE);
        _redTeam.SetItems(i);
        _blueTeam.SetItems(i);

        Scoreboard scoreBoard = plugin.getServer().getScoreboardManager().getNewScoreboard();
        _redInternalTeam = scoreBoard.registerNewTeam(_redTeam.GetName());
        _blueInternalTeam = scoreBoard.registerNewTeam(_blueTeam.GetName());

        _redInternalTeam.prefix(Component.text(_redTeam.GetColor().toString()));
        _redInternalTeam.suffix(Component.text(ChatColor.RESET.toString()));
        _blueInternalTeam.prefix(Component.text(_blueTeam.GetColor().toString()));
        _blueInternalTeam.suffix(Component.text(ChatColor.RESET.toString()));

        _redInternalTeam.displayName(Component.text(_redTeam.GetName()));
        _blueInternalTeam.displayName(Component.text(_blueTeam.GetName()));
    }

    public void SetPlayers(ArrayList<ScorePlayer> p) {
        Collections.shuffle(p);
        for (int i = 0; i < p.size(); i++) {
            if (i % 2 == 0) {
                _redTeam.SetPlayer(p.get(i));
                _redInternalTeam.addPlayer(p.get(i).GetPlayer());
                Component c = Component.text()
                                .append(p.get(i).GetPlayer().name()).color(TextColor.color(255,0,0))
                                .build();
                p.get(i).GetPlayer().playerListName(c);
            }
            else {
                _blueTeam.SetPlayer(p.get(i));
                _blueInternalTeam.addPlayer(p.get(i).GetPlayer());
                Component c = Component.text()
                        .append(p.get(i).GetPlayer().name()).color(TextColor.color(0,0,255))
                        .build();
                p.get(i).GetPlayer().playerListName(c);
            }
        }
    }

    public ScoreTeam GetRedTeam() {
        return _redTeam;
    }

    public ScoreTeam GetBlueTeam() {
        return _blueTeam;
    }

    public void Dispose() {
        _redInternalTeam.unregister();
        _blueInternalTeam.unregister();
    }
}
