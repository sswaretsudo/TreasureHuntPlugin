package jp.norvell.treasurehunt.Events;

import jp.norvell.treasurehunt.Managers.GameManager;

import jp.norvell.treasurehunt.Objects.ScoreItem;
import jp.norvell.treasurehunt.Objects.ScorePlayer;
import jp.norvell.treasurehunt.Objects.ScoreTeam;
import jp.norvell.treasurehunt.TreasureHunt;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.plugin.Plugin;

public class PlayerEventHandler implements Listener {

    private final Plugin _plugin;

    public PlayerEventHandler(Plugin plugin) {
        _plugin = plugin;
    }

    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent e){

        GameManager gm = ((TreasureHunt)_plugin).GetGameManager();

        if (e.getEntity() instanceof Player p) {
            if (!gm.GetIsStarted()) return;
            ScorePlayer pl = null;

            for (ScorePlayer pp : gm.GetInGamePlayer()) {
                if (pp.GetPlayer() == p) pl = pp;
            }
            if (pl == null) return;
            for (ScoreItem pi : gm.GetItems()) {
                if (e.getItem().getItemStack().getType() == pi.GetMaterial()){
                    ScoreTeam t = pl.GetPointTeam();
                    ScoreItem i = t.GetItem(pi.GetMaterial());
                    if (!i.GetIsGot()) {
                        i.SetIsGot(true);
                        t.AddScore(pi.GetScore());
                    }
                }
            }
        }
    }
}
