package at.mtxframe.mtxframe;

import at.mtxframe.mtxframe.database.DatabaseConnection;
import at.mtxframe.mtxframe.gui.ScoreBoard;
import at.mtxframe.mtxframe.listeners.JoinQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.sql.SQLException;

public final class MtxFrame extends JavaPlugin {

    private static MtxFrame plugin;
    public DatabaseConnection database;

    //Tasks
    private BukkitTask taskSB;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        cLog("MtxFramework gestartet.");

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
        //Tasks
        taskSB = getServer().getScheduler().runTaskTimer(this, ScoreBoard.getInstance(), 0, 20);

        //Listeners
        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(this),this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        cLog("MtxFramework gestoppt.");

        if (taskSB != null && !taskSB.isCancelled()){
        taskSB.cancel();
        }


    }





    public  static  MtxFrame getPlugin(){
        return plugin;
    }
    public DatabaseConnection getDatabaseConnection(){
        return database;
    }




    public void cLog(String logMessage){
        plugin.getLogger().info("KONSOLE-MtxFramework: " + logMessage);

    }
    public void cWarning(String logMessage){
        plugin.getLogger().info("WARNUNG-MtxFramework: " + logMessage);

    }







}
