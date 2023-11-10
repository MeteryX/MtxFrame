package at.mtxframe.mtxframe.models;

import at.mtxframe.mtxframe.MtxFrame;
import at.mtxframe.mtxframe.database.DatabaseJobs;
import at.mtxframe.mtxframe.database.DatabasePlayerStats;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class LocalPlayerModel {
    MtxFrame plugin;
    DatabasePlayerStats statsDatabase = new DatabasePlayerStats();
    DatabaseJobs jobsDatabase = new DatabaseJobs();
    PlayerStatsModel pStats;
    PlayerJobStatModel jStats;

    public PlayerStatsModel getpStats() {
        return pStats;
    }

    public void setpStats(PlayerStatsModel pStats) {
        this.pStats = pStats;
    }

    public PlayerJobStatModel getjStats() {
        return jStats;
    }

    public void setjStats(PlayerJobStatModel jStats) {
        this.jStats = jStats;
    }

    public LocalPlayerModel(MtxFrame plugin, PlayerStatsModel pStats, PlayerJobStatModel jStats){
        this.plugin = plugin;
        this.pStats = pStats;
        this.jStats = jStats;
    }



}
