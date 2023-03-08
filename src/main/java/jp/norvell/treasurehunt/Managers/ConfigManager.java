package jp.norvell.treasurehunt.Managers;

import jp.norvell.treasurehunt.Objects.ScoreItem;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class ConfigManager {

    private final Plugin _plugin;
    private FileConfiguration _cfg;

    public ConfigManager(Plugin plugin){
        _plugin = plugin;

        this.Load();
    }

    public void Load(){
        _cfg = _plugin.getConfig();

        if (!_cfg.contains("gameTime")) {
            _cfg.set("gameTime", 10);
        }

        if (_cfg.getInt("gameTime") == 0){
            _cfg.set("gameTime", 10);
        }



        this.Save();
    }

    public int GetTime() {
        return _cfg.getInt("gameTime");
    }

    public void SetTime(int t) {
        _cfg.set("gameTime", t);
        this.Save();
    }


    public void Save() {
        _plugin.saveConfig();
    }

    public void SetItem(Material item, int pts) {
        if (_cfg == null) return;

        ConfigurationSection itemSec = _cfg.getConfigurationSection("Items");
        if (itemSec == null) {
            itemSec = _cfg.createSection("Items");
        }

        ConfigurationSection targetItem = itemSec.createSection(item.name());
        targetItem.set("Score", pts);

        this.Save();

    }

    public ArrayList<ScoreItem> GetItems() {
        if (_cfg == null) return new ArrayList<>();

        ConfigurationSection itemSec = _cfg.getConfigurationSection("Items");
        if (itemSec == null) {
            return new ArrayList<>();
        }

        ArrayList<ScoreItem> dest = new ArrayList<>();

        for (String ms : itemSec.getKeys(false)) {
            Material m = Material.getMaterial(ms);
            int pts = itemSec.getInt(ms + ".Score");
            dest.add(new ScoreItem(m, pts));
        }

        return dest;
    }

    public ScoreItem GetItem(Material m) {
        ArrayList<ScoreItem> items = GetItems();
        for (ScoreItem i : items) {
            if (i.GetMaterial() == m) return i;
        }
        return null;
    }

    public ArrayList<String> GetItemNames() {
        return new ArrayList<>(GetItems().stream().map(n -> n.GetMaterial().name().toLowerCase()).toList());
    }

    public void RemoveItem(ScoreItem p) {
        if (_cfg == null) return;

        ConfigurationSection itemSec = _cfg.getConfigurationSection("Items");
        if (itemSec == null) {
            return;
        }

        itemSec.set(p.GetMaterial().name(), null);
        this.Save();
    }
}
