package at.mtxframe.mtxframe.database;

import at.mtxframe.mtxframe.MtxFrame;
import at.mtxframe.mtxframe.database.handlers.DbPlayerStatsHandler;
import at.mtxframe.mtxframe.models.PlayerStatsModel;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabasePlayerStats {
    MtxFrame plugin;
    DatabaseConnection connection;
    DbPlayerStatsHandler handler = new DbPlayerStatsHandler();
    public DatabasePlayerStats(MtxFrame plugin){
        this.plugin = plugin;
    }


    //CRUD- Create, Read, Update, Delete PLAYERS
    public PlayerStatsModel findPlayerStatDataByUUID(String uuid) throws SQLException{
        connection = plugin.getDatabaseConnection();

        PreparedStatement statement = connection.getConnection().prepareStatement(handler.findPlayerStatTrackerData());
        statement.setString(1, uuid);
        ResultSet results = statement.executeQuery();

        if (results.next()) {
            int deaths = results.getInt("deaths");
            int kills = results.getInt("kills");
            long blocksBroken = results.getLong("blocks_broken");
            double balance = results.getDouble("balance");
            String playerGuild = results.getString("player_guild");
            Date lastLogin = results.getDate("last_login");
            Date lastLogout = results.getDate("last_logout");

            PlayerStatsModel playerstats = new PlayerStatsModel(uuid, deaths, kills, blocksBroken, balance,playerGuild, lastLogin, lastLogout);

            statement.close();

            return playerstats;

        }
        statement.close();
        return  null;

    }


    public void createPlayerStats(PlayerStatsModel stats) throws SQLException {
        //Die Fragezeichen im preparedStatement (String ist in DataBaseHandler zu finden) werden im folgenden Schritt verarbeitet und mit Daten gefüllt bevor sie
        // auf in die Datenbank geladen und somit gespeichert werden.
        connection = plugin.getDatabaseConnection();

        PreparedStatement statement = connection.getConnection().prepareStatement(handler.createPlayerStatTrackerData());

        statement.setString(1, stats.getUuid());
        statement.setInt(2, stats.getDeaths());
        statement.setInt(3, stats.getKills());
        statement.setLong(4, stats.getBlocksBroken());
        statement.setDouble(5, stats.getBalance());
        statement.setString(6,stats.getPlayerGuild());
        statement.setDate(7, new Date(stats.getLastLogin().getTime()));
        statement.setDate(8, new Date(stats.getLastLogout().getTime()));

        statement.executeUpdate();

        statement.close();

    }
    public void updatePlayerStats(PlayerStatsModel stats) throws SQLException {
        //Die Fragezeichen im preparedStatement (String ist in DataBaseHandler zu finden) werden im folgenden Schritt verarbeitet und mit Daten gefüllt bevor sie
        // auf in die Datenbank geladen und somit gespeichert werden.
        connection = plugin.getDatabaseConnection();

        PreparedStatement statement = connection.getConnection().prepareStatement(handler.updatePlayerStats());

        statement.setInt(1, stats.getDeaths());
        statement.setInt(2, stats.getKills());
        statement.setLong(3, stats.getBlocksBroken());
        statement.setDouble(4, stats.getBalance());
        statement.setString(5, stats.getPlayerGuild());
        statement.setDate(6, new Date(stats.getLastLogin().getTime()));
        statement.setDate(7, new Date(stats.getLastLogout().getTime()));
        statement.setString(8, stats.getUuid());

        statement.executeUpdate();

        statement.close();

    }

    public void deletePlayerStats(String uuid) throws SQLException{
        connection = plugin.getDatabaseConnection();

        PreparedStatement statement = connection.getConnection().prepareStatement(handler.deletePlayerStats());

        statement.setString(1, uuid);
        statement.executeUpdate();
        statement.close();

    }


}
