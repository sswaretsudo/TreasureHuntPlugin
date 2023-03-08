package jp.norvell.treasurehunt.Objects;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;

public class ScoreTeam
{
    private final String _name;
    private final ChatColor _color;

    private final ArrayList<ScorePlayer> _players;
    private ArrayList<ScoreItem> _items;

    private int _score;

    public ScoreTeam(String name, ChatColor color) {
        _name = name;
        _color = color;
        _players = new ArrayList<>();
    }

    public String GetName() {
        return _name;
    }

    public ChatColor GetColor() {
        return _color;
    }

    public void SetPlayer(ScorePlayer p) {
        _players.add(p);
        p.SetScoreTeam(this);
    }

    public ArrayList<ScorePlayer> GetPlayers() {
        return _players;
    }

    public void SetItems(ArrayList<ScoreItem> i) {
        _items = new ArrayList<>(i);
    }

    public ScoreItem GetItem(Material m) {
        for (ScoreItem i : _items) {
            if (i.GetMaterial() == m)
                return i;
        }
        return null;
    }

    public void AddScore(int v) {
        _score += v;
    }

    public int GetScore() {
        return _score;
    }
}
