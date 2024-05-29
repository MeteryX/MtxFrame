package at.mtxframe.mtxframe.database.handlers;

public class DbJobsHandler {

    private String CreateJobStatsTable = "CREATE TABLE IF NOT EXISTS player_job_stats(uuid varchar(36) primary key, mining_level int, mining_xp double, farmer_level int, farmer_xp double, hunter_level int, hunter_xp double, woodcutter_level int, woodcutter_xp double, fisher_level int, fisher_xp double)";
    private String findPlayerJobStats = "SELECT * FROM player_job_stats WHERE uuid = ?"; //WICHTIG: + uuid

    private String createPlayerJobStats = "INSERT INTO player_job_stats(uuid, mining_level, mining_xp, farmer_level, farmer_xp, hunter_level, hunter_xp, woodcutter_level, woodcutter_xp, fisher_level, fisher_xp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; // WICHTIG: + Daten die verwendet werden sollen
    private String updatePlayerJobStats = "UPDATE player_job_stats SET mining_level = ?, mining_xp = ?, farmer_level = ?, farmer_xp = ?, hunter_level = ?, hunter_xp = ?, woodcutter_level = ?, woodcutter_xp = ?, fisher_level = ?, fisher_xp = ? WHERE uuid = ?";
    private String deletePlayerJobStats = "DELETE FROM player_ job_stats WHERE uuid = ?";


    public String getCreateJobStatsTable() {
        return CreateJobStatsTable;
    }

    public String getFindPlayerJobStats() {
        return findPlayerJobStats;
    }

    public String getCreatePlayerJobStats() {
        return createPlayerJobStats;
    }

    public String getUpdatePlayerJobStats() {
        return updatePlayerJobStats;
    }

    public String getDeletePlayerJobStats() {
        return deletePlayerJobStats;
    }
}
