package jp.norvell.treasurehunt.Schedulers;

import jp.norvell.treasurehunt.Managers.GameManager;
import org.bukkit.scheduler.BukkitRunnable;

public class CountDownScheduler extends BukkitRunnable {

    private int _readyTime;
    private int _gameTime;
    private boolean _isReadyEnded;
    private final GameManager _gameMgr;
    public CountDownScheduler(int readyTime, int gameTime , GameManager gm) {
        _readyTime = readyTime;
        _gameTime = gameTime;
        _gameMgr = gm;
    }

    @Override
    public void run() {
        if (_readyTime < 0 && !_isReadyEnded) {
            _gameMgr.OnStartGame();
            _isReadyEnded = true;
        }
        if (_isReadyEnded) {
            if (_gameTime < 0) {
                _gameMgr.OnFinishGame();
                _gameMgr.Dispose();
                cancel();
                return;
            }
            _gameMgr.Update(_gameTime);
            _gameTime--;
        } else {
            _gameMgr.SendTitle(String.valueOf(_readyTime), "");
            _readyTime--;
        }
    }
}
