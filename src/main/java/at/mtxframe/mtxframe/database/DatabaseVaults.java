package at.mtxframe.mtxframe.database;

import at.mtxframe.mtxframe.MtxFrame;
import at.mtxframe.mtxframe.database.handlers.DbVaultsHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseVaults {
    MtxFrame plugin = MtxFrame.getPlugin();
    DatabaseConnection connection = plugin.getDatabaseConnection();
    DbVaultsHandler handler = new DbVaultsHandler();
    //GuildVaults
    public void createGuildVault(String guildName, String content, String searchParameter) throws SQLException {
        PreparedStatement statement = connection.getConnection().prepareStatement(searchParameter);
        statement.setString(1,guildName);
        statement.setString(2,content);

        statement.executeUpdate();
        statement.close();

    }

    public void deleteGuildVault(String guildName, String searchParameter) throws SQLException {
        PreparedStatement statement = connection.getConnection().prepareStatement(searchParameter);
        statement.setString(1,guildName);

        statement.executeUpdate();
        statement.close();

    }
    public void updateGuildVault(String guildName, String content, String searchParameter) throws SQLException{
        PreparedStatement statement = connection.getConnection().prepareStatement(searchParameter);
        statement.setString(1, content); // Assuming content is the value you want to update
        statement.setString(2, guildName); // Assuming guildName is used in the WHERE clause for the update

        statement.executeUpdate();
        statement.close();
    }
    public void putGuildVault(String guildName, String content, String searchParameter) throws SQLException{
        PreparedStatement statement = connection.getConnection().prepareStatement(searchParameter);
        statement.setString(1,guildName);
        statement.setString(2, content);

        statement.executeUpdate();
        statement.close();

    }
    public  String getGuildVault(String guildName, String searchParameter) throws SQLException{
        String guildVault = "";
        PreparedStatement statement = connection.getConnection().prepareStatement(searchParameter);
        statement.setString(1,guildName);

        ResultSet results = statement.executeQuery();
        if (results.next()){
            guildVault = results.getString("content");
        }


        statement.close();


        return guildVault;
    }

}
