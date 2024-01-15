package at.mtxframe.mtxframe.listeners;

import at.mtxframe.mtxframe.MtxFrame;
import at.mtxframe.mtxframe.database.DatabaseJobs;
import at.mtxframe.mtxframe.database.DatabasePlayerStats;
import at.mtxframe.mtxframe.gui.ScoreBoard;
import at.mtxframe.mtxframe.gui.Tablist;
import at.mtxframe.mtxframe.models.PlayerJobStatModel;
import at.mtxframe.mtxframe.models.PlayerStatsModel;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

public class JoinQuitListener implements Listener {
    DatabasePlayerStats database = new DatabasePlayerStats(MtxFrame.getPlugin());
    DatabaseJobs jobsDatabase = new DatabaseJobs();
    Tablist tabList = new Tablist();
    HashMap<Player,PlayerStatsModel> localPlayerStats;
    MtxFrame plugin;
    public JoinQuitListener(MtxFrame plugin){
        this.plugin = plugin;
    }
    ScoreBoard scoreBoard = new ScoreBoard(MtxFrame.getPlugin());

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {
        Player p = event.getPlayer();
        localPlayerStats = plugin.getLocalPlayerStats();
        PersistentDataContainer data = p.getPersistentDataContainer();
        if (!data.has(new NamespacedKey("ultima","playervault"), PersistentDataType.STRING)){
            data.set(new NamespacedKey("ultima","playervault"), PersistentDataType.STRING, "");
        }
        if (!p.hasPlayedBefore()) {
            event.setJoinMessage(ChatColor.GREEN + "Willkommen auf dem Server " + ChatColor.GOLD + p.getName());
            database.createPlayerStats(new PlayerStatsModel(String.valueOf(p.getUniqueId()),0,0,0,00.00, "No Guild", new Date(), new Date()));
            localPlayerStats = plugin.getLocalPlayerStats();
            localPlayerStats.put(p,database.findPlayerStatDataByUUID(String.valueOf(p.getUniqueId())));
            plugin.setLocalPlayerStats(localPlayerStats);
            getPlayerJobStatsFromDatabase(p);
            scoreBoard.setBelowName(p);
        } else {

        event.setJoinMessage(ChatColor.BLUE + p.getName() + ChatColor.GREEN + " +");

            try {
                localPlayerStats = plugin.getLocalPlayerStats();
                PlayerStatsModel playerStats = getPlayerStatsFromDatabase(p);
                localPlayerStats.put(p,playerStats);
                plugin.setLocalPlayerStats(localPlayerStats);
                playerStats.setLastLogin(new Date());
                scoreBoard.setBelowName(p);

                // scoreboardUI.createScoreboard(event.getPlayer());
                tabList.setTabList(p);
                // p.setScoreboard(scoreboardUI.createScoreboard(p));
            } catch (SQLException exception) {
                exception.printStackTrace();
                //Debug
                plugin.cWarning("Spielerstats konnten nicht aktualisiert werden: " + exception.getMessage());
            }
        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        event.setQuitMessage(ChatColor.BLUE + p.getName() + ChatColor.RED + " -");
        //if (!Bukkit.getOnlinePlayers().isEmpty()){
        //Bukkit.getOnlinePlayers().forEach(player -> scoreboardUI.updateAllScoreboards(player));
        // }
        try {
            localPlayerStats = plugin.getLocalPlayerStats();
            PlayerStatsModel playerStats = localPlayerStats.get(p);
            playerStats.setLastLogout(new Date());
            localPlayerStats.remove(p);
            plugin.setLocalPlayerStats(localPlayerStats);
            database.updatePlayerStats(playerStats);

        }catch(SQLException exception){
            exception.printStackTrace();
            //Debug
            plugin.cWarning("Spielerstats konnten nicht aktualisiert werden: " + exception.getMessage());
        }

    }

    private PlayerStatsModel getPlayerStatsFromDatabase(Player p) throws SQLException{
        PlayerStatsModel stats = database.findPlayerStatDataByUUID(p.getUniqueId().toString());
        if (stats == null) {
            stats = new PlayerStatsModel(p.getUniqueId().toString(), 0, 0, 0, 0, "No Guild" , new Date(), new Date());
            database.createPlayerStats(stats);
            //TODO: Später löschen wegen Performance Gründen
            plugin.cLog("Stats für Spieler " + p.getDisplayName() + "wurden erstellt.");
            return stats;
        }
        return stats;
    }
    private PlayerJobStatModel getPlayerJobStatsFromDatabase(Player p) throws SQLException{
        PlayerJobStatModel jobStats = jobsDatabase.findJobsDataByUUID(p.getUniqueId().toString());
        if (jobStats == null) {
            jobStats = new PlayerJobStatModel(String.valueOf(p.getUniqueId()), 1, 00.00, 1, 00.00, 1, 00.00, 1, 00.00, 1, 00.00);
            jobsDatabase.createPlayerJobStats(jobStats, String.valueOf(p.getUniqueId()));
            //TODO: Später löschen wegen Performance Gründen
            plugin.cLog("Stats für Spieler " + p.getDisplayName() + "wurden erstellt.");
            return jobStats;
        }
        return jobStats;
    }


}
