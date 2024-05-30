package at.mtxframe.mtxframe;

import at.mtxframe.mtxframe.colors.ColorAPI;
import at.mtxframe.mtxframe.colors.commands.ColorCommands;
import at.mtxframe.mtxframe.colors.signs.SignColorListener;
import at.mtxframe.mtxframe.crates.TestCrateCommand;
import at.mtxframe.mtxframe.database.DatabaseConnection;
import at.mtxframe.mtxframe.database.DatabasePlayerStats;
import at.mtxframe.mtxframe.debugcommands.DebugCustomItem;
import at.mtxframe.mtxframe.debugcommands.DebugDS;
import at.mtxframe.mtxframe.economy.BalanceHandler;
import at.mtxframe.mtxframe.files.ChatFilterConfig;
import at.mtxframe.mtxframe.gui.ScoreBoard;
import at.mtxframe.mtxframe.listeners.CustomItemListener;
import at.mtxframe.mtxframe.listeners.JoinQuitListener;
import at.mtxframe.mtxframe.models.PlayerStatsModel;

import at.mtxframe.mtxframe.penalites.ChatListener;
import at.mtxframe.mtxframe.penalites.commands.ReportCommand;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public final class MtxFrame extends JavaPlugin {

    public static MtxFrame plugin;
    public DatabaseConnection database;
    DatabasePlayerStats dbStats = new DatabasePlayerStats(this);

    public HashMap<Player, PlayerStatsModel> localPlayerStats;
    //VAULT
    private static Economy eco = null;

    //Tasks
    private BukkitTask taskSB;
    public BalanceHandler balanceHandler;
    //ColorAPi
    public ColorAPI colorAPI;



    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        this.database = new DatabaseConnection(
                getConfig().getString("database.host"),
                getConfig().getString("database.port"),
                getConfig().getString("database.user"),
                getConfig().getString("database.password"),
                getConfig().getString("database.database_name"));

        try {
            database.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //Penalties
        ChatFilterConfig.setup();
        ChatFilterConfig.get().options().copyDefaults(true);
        ChatFilterConfig.save();

        //ColorAPI
        colorAPI = new ColorAPI(this);
        getServer().getServicesManager().register(ColorAPI.class, colorAPI, this, ServicePriority.High);
        //HolographicDisplays
        //ProtocolLib

        //Tasks
        //Scoreboard task
        taskSB = getServer().getScheduler().runTaskTimer(this, ScoreBoard.getInstance(), 0, 20);

        //Listeners
        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(this),this);
        Bukkit.getPluginManager().registerEvents(new CustomItemListener(this),this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(),this);
        Bukkit.getPluginManager().registerEvents(new SignColorListener(),this);

        //Command registration
        getCommand("itemtest").setExecutor(new DebugCustomItem());
        getCommand("colorapi").setExecutor(new ColorCommands(this));
        getCommand("cratetest").setExecutor(new TestCrateCommand(this));
        getCommand("report").setExecutor(new ReportCommand());
        getCommand("dbds").setExecutor(new DebugDS());

        //In Memory Initialisierung
        this.localPlayerStats = new HashMap<>();


        //Shedulers/Runnables

        cLog("MtxFramework gestartet.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        cWarning("MtxFramework gestoppt.");
        for (Map.Entry<Player, PlayerStatsModel> entry : localPlayerStats.entrySet()) {
            Player player = entry.getKey();
            PlayerStatsModel playerStatModel = entry.getValue();

            // Hier den Code einfügen, um playerStatModel an die Datenbank zu senden
            try {
                dbStats.updatePlayerStats(playerStatModel);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            // Optional: Hier kannst du weitere Operationen für jeden Spieler durchführen, falls nötig
        }
        //ColorAPI
        getServer().getServicesManager().unregister(this.colorAPI);

        if (taskSB != null && !taskSB.isCancelled()){
            taskSB.cancel();
        }


    }

    //Reload Plugin
    public void reloadPlugin(){
        colorAPI = new ColorAPI(this);
    }

    public ColorAPI getColorAPI() {
        return colorAPI;
    }

    //VAULT
    public Economy getEco(){
        return eco;
    }


    public HashMap<Player, PlayerStatsModel> getLocalPlayerStats() {
        return localPlayerStats;
    }

    public void setLocalPlayerStats(HashMap<Player, PlayerStatsModel> localPlayerStats) {
        this.localPlayerStats = localPlayerStats;
    }

    public static MtxFrame getPlugin(){
        return plugin;
    }
    public DatabaseConnection getDatabaseConnection(){
        return database;
    }

    //Getters und Setters Listener



    public void cLog(String logMessage){
        plugin.getLogger().info("KONSOLE-MtxFramework: " + logMessage);

    }
    public void cWarning(String logMessage){
        plugin.getLogger().info("WARNUNG-MtxFramework: " + logMessage);

    }







}

