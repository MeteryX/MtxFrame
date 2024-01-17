package at.mtxframe.mtxframe.database;

import at.mtxframe.mtxframe.MtxFrame;
import at.mtxframe.mtxframe.handlers.DbJobsHandler;
import at.mtxframe.mtxframe.models.PlayerJobStatModel;

import java.sql.*;

public class DatabaseJobs {
    MtxFrame plugin = MtxFrame.getPlugin();
    DatabaseConnection connection = plugin.getDatabaseConnection();
    DbJobsHandler handler = new DbJobsHandler();

    //CRUD- Create, Read, Update, Delete PLAYERS
    public PlayerJobStatModel findJobsDataByUUID(String uuid) throws SQLException {

        PreparedStatement statement = connection.getConnection().prepareStatement(handler.getFindPlayerJobStats());
        statement.setString(1, uuid);
        ResultSet results = statement.executeQuery();

        if (results.next()) {
            int miningLevel = results.getInt("mining_level");
            double miningXP = results.getDouble("mining_xp");

            int farmingLevel = results.getInt("farmer_level");
            double farmingXP = results.getDouble("farmer_xp");

            int huntingLevel = results.getInt("hunter_level");
            double huntingXP = results.getDouble("hunter_xp");

            int woodcutterLevel = results.getInt("woodcutter_level");
            double woodcutterXP = results.getDouble("woodcutter_xp");

            int fisherLevel = results.getInt("fisher_level");
            double fisherXP = results.getDouble("fisher_xp");


            PlayerJobStatModel jobstats = new PlayerJobStatModel(uuid, miningLevel, miningXP, farmingLevel, farmingXP, huntingLevel, huntingXP, woodcutterLevel, woodcutterXP, fisherLevel, fisherXP);

            results.close();
            statement.close();

            return jobstats;

        }
        statement.close();
        return  null;

    }


    public void createPlayerJobStats(PlayerJobStatModel stats, String uuid) throws SQLException {
        //Die Fragezeichen im preparedStatement (String ist in DataBaseHandler zu finden) werden im folgenden Schritt verarbeitet und mit Daten gefüllt bevor sie
        // auf in die Datenbank geladen und somit gespeichert werden.

        PreparedStatement statement = connection.getConnection().prepareStatement(handler.getCreatePlayerJobStats());

        statement.setString(1, uuid);
        statement.setInt(2, stats.getMiningLevel());
        statement.setDouble(3, stats.getMiningXP());
        statement.setInt(4, stats.getFarmerLevel());
        statement.setDouble(5, stats.getFarmerXP());
        statement.setInt(6,stats.getHunterLevel());
        statement.setDouble(7, stats.getHunterXP());
        statement.setInt(8, stats.getWoodcutterLevel());
        statement.setDouble(9, stats.getWoodcutterXP());
        statement.setInt(10, stats.getFisherLevel());
        statement.setDouble(11, stats.getFisherXP());

        statement.executeUpdate();

        statement.close();

    }





    public void updatePlayerJobStats(PlayerJobStatModel stats) throws SQLException {

        PreparedStatement statement = connection.getConnection().prepareStatement(handler.getUpdatePlayerJobStats());

        statement.setInt(1, stats.getMiningLevel());
        statement.setDouble(2, stats.getMiningXP());
        statement.setInt(3, stats.getFarmerLevel());
        statement.setDouble(4, stats.getFarmerXP());
        statement.setInt(5,stats.getHunterLevel());
        statement.setDouble(6, stats.getHunterXP());
        statement.setInt(7, stats.getWoodcutterLevel());
        statement.setDouble(8, stats.getWoodcutterXP());
        statement.setInt(9, stats.getFisherLevel());
        statement.setDouble(10, stats.getFisherXP());
        statement.setString(11, stats.getUuid());

        statement.executeUpdate();

        statement.close();
    }

    public void deletePlayerJobStats(String uuid) throws SQLException{

        PreparedStatement statement = connection.getConnection().prepareStatement(handler.getDeletePlayerJobStats());

        statement.setString(1, uuid);
        statement.executeUpdate();
        statement.close();

    }



}
