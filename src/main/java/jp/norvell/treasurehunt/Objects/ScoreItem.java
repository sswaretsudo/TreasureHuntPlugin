package jp.norvell.treasurehunt.Objects;

import org.bukkit.Material;

public class ScoreItem {

    private final Material _m;
    private final int _scr;
    private boolean _isGot;

    public ScoreItem(Material m, int scr) {
        _m = m;
        _scr = scr;
    }

    public Material GetMaterial() {
        return _m;
    }

    public int GetScore() {
        return _scr;
    }

    public void SetIsGot(boolean v) {
        _isGot = v;
    }

    public boolean GetIsGot() {
        return _isGot;
    }
}
