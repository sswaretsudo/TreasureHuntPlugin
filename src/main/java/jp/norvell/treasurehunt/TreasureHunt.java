package jp.norvell.treasurehunt;

import jp.norvell.treasurehunt.Commands.*;
import jp.norvell.treasurehunt.Events.PlayerEventHandler;
import jp.norvell.treasurehunt.Managers.ConfigManager;
import jp.norvell.treasurehunt.Managers.GameManager;
import jp.norvell.treasurehunt.Objects.Command.CommandArgumentModule;
import jp.norvell.treasurehunt.Objects.Command.CommandContainer;
import org.bukkit.plugin.java.JavaPlugin;

public final class TreasureHunt extends JavaPlugin {

    public static TreasureHunt INSTANCE;
    private ConfigManager _cfgMgr;
    private GameManager _gmMgr;
    private CommandManager _cmdMgr;

    @Override
    public void onEnable() {

        INSTANCE = this;
        Initialize();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public GameManager GetGameManager() {
        return _gmMgr;
    }

    public ConfigManager GetConfigManager() {
        return _cfgMgr;
    }

    public CommandManager GetCommandManager() { return _cmdMgr; }

    private void Initialize() {
        _cfgMgr = new ConfigManager(this);
        _gmMgr = new GameManager(this);
        _cmdMgr = new CommandManager(this);

        InitializeCommand();

        this.getServer().getPluginManager().registerEvents(new PlayerEventHandler(this), this);

    }

    private void InitializeCommand() {
        CommandContainer treasureHuntCtn = new CommandContainer("treasurehunt");

        CommandArgumentModule startCommandArg = new CommandArgumentModule("start");
        CommandArgumentModule endCommandArg = new CommandArgumentModule("end");
        CommandArgumentModule addCommandArg = new CommandArgumentModule("add");
        CommandArgumentModule removeCommandArg = new CommandArgumentModule("remove");
        CommandArgumentModule showCommandArg = new CommandArgumentModule("show");
        CommandArgumentModule configCommandArg = new CommandArgumentModule("config");
        CommandArgumentModule gameTimeConfigCommandArg = new CommandArgumentModule("gameTime");

        addCommandArg.SetOptionalCompleteList(_gmMgr.GetMaterials());
        removeCommandArg.SetOptionalCompleteList(_cfgMgr.GetItemNames());

        startCommandArg.SetCommandProcedureModule(new StartCommandProcedureModule());
        endCommandArg.SetCommandProcedureModule(new EndCommandProcedureModule());
        addCommandArg.SetCommandProcedureModule(new AddCommandProcedureModule());
        removeCommandArg.SetCommandProcedureModule(new RemoveCommandProcedureModule());
        showCommandArg.SetCommandProcedureModule(new ShowCommandProcedureModule());
        gameTimeConfigCommandArg.SetCommandProcedureModule(new GameTimeConfigCommandProcedureModule());

        configCommandArg.RegisterArgument(gameTimeConfigCommandArg);

        treasureHuntCtn.RegisterArgument(startCommandArg);
        treasureHuntCtn.RegisterArgument(endCommandArg);
        treasureHuntCtn.RegisterArgument(addCommandArg);
        treasureHuntCtn.RegisterArgument(removeCommandArg);
        treasureHuntCtn.RegisterArgument(showCommandArg);
        treasureHuntCtn.RegisterArgument(configCommandArg);

        _cmdMgr.RegisterCommand(treasureHuntCtn, new GameCommandExecutor());
    }

}
