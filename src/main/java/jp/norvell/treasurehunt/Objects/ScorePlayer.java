package jp.norvell.treasurehunt.Objects;

import org.bukkit.entity.Player;

public class ScorePlayer {

    private final Player _p;
    private ScoreTeam _team;
    public ScorePlayer(Player p) {
        _p = p;
    }

    public Player GetPlayer() {
        return _p;
    }

    public ScoreTeam GetPointTeam() {
        return _team;
    }

    public void SetScoreTeam(ScoreTeam t) {
        _team = t;
    }
}
