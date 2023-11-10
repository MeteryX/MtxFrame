package at.mtxframe.mtxframe.handlers;

public class DbPlayerStatsHandler {
    private String CreateStatTrackerTable = "CREATE TABLE IF NOT EXISTS player_stats(uuid varchar(36) primary key, deaths int, kills int, blocks_broken long, balance double, player_guild varchar(50), last_login date, last_logout date)";
    private String findPlayerStatTrackerData = "SELECT * FROM player_stats WHERE uuid = ?"; //WICHTIG: + uuid

    private String createPlayerStatTrackerData = "INSERT INTO player_stats(uuid, deaths, kills, blocks_broken, balance, player_guild, last_login, last_logout) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"; // WICHTIG: + Daten die verwendet werden sollen
    private String updatePlayerStats = "UPDATE player_stats SET deaths = ?, kills = ?, blocks_broken = ?, balance = ?, player_guild = ?, last_login = ?, last_logout = ? WHERE uuid = ?";
    private String deletePlayerStats = "DELETE FROM player_stats WHERE uuid = ?";


    public String createStatTrackerTable() {
        return CreateStatTrackerTable;
    }

    public String findPlayerStatTrackerData() {
        return findPlayerStatTrackerData;
    }

    public String createPlayerStatTrackerData() {
        return createPlayerStatTrackerData;
    }

    public String updatePlayerStats() {
        return updatePlayerStats;
    }

    public String deletePlayerStats() {
        return deletePlayerStats;
    }



}
