package jp.norvell.treasurehunt.Managers;

import jp.norvell.treasurehunt.Objects.*;
import jp.norvell.treasurehunt.Schedulers.CountDownScheduler;
import jp.norvell.treasurehunt.TreasureHunt;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.TitlePart;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class GameManager {

    private final Plugin _plugin;

    private boolean _isStarted;
    private int _gameTime;
    private ArrayList<ScoreItem> _items;
    private final ArrayList<String> _matrials;
    private TeamManager _teamMgr;
    private ArrayList<ScorePlayer> _inGamePlayers;
    private BossBar _timeBar;
    private CountDownScheduler _scheduler;

    public GameManager(Plugin plugin) {
        _plugin = plugin;
        _matrials = new ArrayList<>();

        for (Material m : Material.values()) {
            _matrials.add(m.name().toLowerCase());
        }
    }

    public ErrorInfo Start() {
        ConfigManager _cfgMgr = ((TreasureHunt) _plugin).GetConfigManager();

        _gameTime = _cfgMgr.GetTime();
        _items = _cfgMgr.GetItems();

        _teamMgr = new TeamManager(_plugin, _items);

        if (_items.size() == 0)
            return new ErrorInfo(true, "At least one item must be set");

        _inGamePlayers = new ArrayList<>();
        for (Player p : _plugin.getServer().getOnlinePlayers()) {
            _inGamePlayers.add(new ScorePlayer(p));
        }

        _isStarted = true;

        _timeBar = Bukkit.createBossBar("", BarColor.BLUE, BarStyle.SOLID);

        _scheduler = new CountDownScheduler(3, _cfgMgr.GetTime(), this);
        _scheduler.runTaskTimer(_plugin, 0, 20);

        return new ErrorInfo(false);
    }

    public void OnStartGame() {
        _teamMgr.SetPlayers(_inGamePlayers);
        for (ScorePlayer p : _inGamePlayers) {
            _timeBar.addPlayer(p.GetPlayer());
        }
        _timeBar.setVisible(true);

        for (ScorePlayer p : _teamMgr.GetRedTeam().GetPlayers()) {
            p.GetPlayer().sendTitlePart(TitlePart.TITLE, Component.text("スタート！"));
            Component st = Component.text()
                    .append(Component.text("あなたは"))
                    .append(Component.text("赤チーム").color(TextColor.color(255,0,0)))
                    .append(Component.text("です"))
                            .build();
            p.GetPlayer().sendTitlePart(TitlePart.SUBTITLE, st);
        }

        for (ScorePlayer p : _teamMgr.GetBlueTeam().GetPlayers()) {
            p.GetPlayer().sendTitlePart(TitlePart.TITLE, Component.text("スタート！"));
            Component st = Component.text()
                    .append(Component.text("あなたは"))
                    .append(Component.text("青チーム").color(TextColor.color(0,0,255)))
                    .append(Component.text("です"))
                        .build();
            p.GetPlayer().sendTitlePart(TitlePart.SUBTITLE, st);
        }
    }

    public void OnFinishGame() {
        ScoreTeam r = _teamMgr.GetRedTeam();
        ScoreTeam b = _teamMgr.GetBlueTeam();

        if (r.GetScore() > b.GetScore()) {
            for (ScorePlayer p : _inGamePlayers) {
                p.GetPlayer().sendTitlePart(TitlePart.TITLE, Component.text("タイムアップ！"));
                Component st = Component.text()
                        .append(Component.text("赤チーム").color(TextColor.color(255,0,0)))
                        .append(Component.text("の勝利！"))
                            .build();
                p.GetPlayer().sendTitlePart(TitlePart.SUBTITLE, st);
            }
        }
        else
        if (r.GetScore() == b.GetScore()) {
            for (ScorePlayer p : _inGamePlayers) {
                p.GetPlayer().sendTitlePart(TitlePart.TITLE, Component.text("タイムアップ！"));

                p.GetPlayer().sendTitlePart(TitlePart.SUBTITLE, Component.text("同点でした！"));
            }
        }
        else
        if (r.GetScore() < b.GetScore()) {
            for (ScorePlayer p : _inGamePlayers) {
                p.GetPlayer().sendTitlePart(TitlePart.TITLE, Component.text("タイムアップ！"));
                Component st = Component.text()
                        .append(Component.text("青チーム").color(TextColor.color(0,0,255)))
                        .append(Component.text("の勝利！"))
                        .build();
                p.GetPlayer().sendTitlePart(TitlePart.SUBTITLE, st);
            }
        }
    }

    public boolean GetIsStarted() {
        return _isStarted;
    }

    public ArrayList<String> GetMaterials() {
        return _matrials;
    }

    public ArrayList<ScoreItem> GetItems() {
        return _items;
    }

    public ArrayList<ScorePlayer> GetInGamePlayer() {
        return _inGamePlayers;
    }

    public void FinishForce() {
        SendTitle("終了", "コマンドにより強制的に終了しました");
        Dispose();
        _scheduler.cancel();
    }

    public void SendTitle(String title, String subTitle) {
        if (_isStarted) {
            for (ScorePlayer p : _inGamePlayers) {
                p.GetPlayer().sendTitlePart(TitlePart.TITLE, Component.text(title));
                p.GetPlayer().sendTitlePart(TitlePart.SUBTITLE, Component.text(subTitle));
            }
        }
    }

    public void Update(int time)
    {
        UpdateTimeBar(time);
        UpdateActionBars();
        UpdatePlayerList();
    }

    private void UpdateTimeBar(int remain) {
        if (!_isStarted) return;

        int m = remain / 60;
        int s = remain % 60;
        double p = (double)remain / (double)_gameTime;

        _timeBar.setTitle("残り時間 - " + m + "分" + s + "秒 -");

        if (remain == 0) p = 0;
        _timeBar.setProgress(p);

    }

    private void UpdatePlayerList() {
        if (!_isStarted) return;

        Component c = Component.text()
                .append(Component.text("赤チーム").color(TextColor.color(255,0,0)))
                .appendSpace()
                .append(Component.text(_teamMgr.GetRedTeam().GetScore()))
                .appendSpace()
                .append(Component.text("vs").color(TextColor.color(200,200,200)))
                .appendSpace()
                .append(Component.text("青チーム").color(TextColor.color(0,0,255)))
                .appendSpace()
                .append(Component.text(_teamMgr.GetBlueTeam().GetScore()))
                        .build();


        _plugin.getServer().sendPlayerListFooter(c);
    }

    private void UpdateActionBars() {
        if (!_isStarted) return;

        for (ScorePlayer p : _inGamePlayers) {

            p.GetPlayer().sendActionBar(Component.text("ポイント : " + p.GetPointTeam().GetScore()));
        }
    }

    public void Dispose()
    {
        _plugin.getServer().sendPlayerListFooter(Component.space());
        _teamMgr.Dispose();
        _timeBar.removeAll();
        _timeBar = null;
        _isStarted = false;
    }
}
