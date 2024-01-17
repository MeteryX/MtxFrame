package at.mtxframe.mtxframe.database;

import at.mtxframe.mtxframe.MtxFrame;
import at.mtxframe.mtxframe.handlers.DbGuildsHandler;
import at.mtxframe.mtxframe.models.GuildsModel;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseGuilds {
    MtxFrame plugin = MtxFrame.getPlugin();
    DatabaseConnection connection = plugin.getDatabaseConnection();
    DbGuildsHandler handler = new DbGuildsHandler();
    //Findet eine Gilde mit der angegebenen Gilden ID
    public GuildsModel findGuildByID(String guildID, String searchParameter) throws  SQLException{
        PreparedStatement statement = connection.getConnection().prepareStatement(handler.getSearchGuildByID());
        statement.setString(1,guildID);
        ResultSet results = statement.executeQuery();
        if (results.next()) {
            // Process the results and create a GuildsModel
            String guildName = results.getString("guild_name");
            String ownerName = results.getString("guild_owner_name");
            int guildLevel = results.getInt("guild_level");
            int memberCount = results.getInt("guild_member_count");
            int memberLimit = results.getInt("guild_member_limit");
            Date creationDate = results.getDate("guild_creation_date");
            // Create a GuildsModel using the retrieved data
            GuildsModel guild = new GuildsModel(guildID, guildName, ownerName, guildLevel, memberCount, memberLimit, creationDate);
            //close statement and results
            results.close();
            statement.close();
            // Return the guild
            return guild;
        } else {
            // No guild with that name found, return null or handle accordingly
            return null;
        }
    }

    //Findet eine Gilde mit dem angegebenen Namen
    public GuildsModel findGuildByName(String guildName, String searchParameter) throws  SQLException{
        PreparedStatement statement = connection.getConnection().prepareStatement(handler.getSearchGuildByName());
        statement.setString(1,guildName);
        ResultSet results = statement.executeQuery();
        if (results.next()) {
            // Process the results and create a GuildsModel
            String guildID = results.getString("guild_id");
            String ownerName = results.getString("guild_owner_name");
            int guildLevel = results.getInt("guild_level");
            int memberCount = results.getInt("guild_member_count");
            int memberLimit = results.getInt("guild_member_limit");
            Date creationDate = results.getDate("guild_creation_date");
            // Create a GuildsModel using the retrieved data
            GuildsModel guild = new GuildsModel(guildID, guildName, ownerName, guildLevel, memberCount, memberLimit, creationDate);
            // Return the guild
            results.close();
            statement.close();
            return guild;
        } else {
            // No guild with that name found, return null or handle accordingly
            return null;
        }
    }
    //Erstellt ein neues GuildsModel und speichert es in der Datenbank mit angegeben Owner Namen und Gilden Namen
    public void createGuild(String guildName,String guildOwner) throws SQLException {
        //Die Fragezeichen im preparedStatement (String ist in DataBaseHandler zu finden) werden im folgenden Schritt verarbeitet und mit Daten gefüllt bevor sie
        // auf in die Datenbank geladen und somit gespeichert werden.
        UUID randomUUID = UUID.randomUUID();
        String guildID = randomUUID.toString();
        GuildsModel guildData = new GuildsModel(guildID, guildName, guildOwner, 1, 1, 10, new java.util.Date());

        PreparedStatement statement = connection.getConnection().prepareStatement(handler.getCreateGuildData());

        statement.setString(1, guildData.getGuildID());
        statement.setString(2, guildData.getGuildName());
        statement.setString(3, guildData.getGuildOwnerName());
        statement.setInt(4, guildData.getGuildLevel());
        statement.setInt(5, guildData.getGuildMemberCount());
        statement.setInt(6, guildData.getGuildMaxMembers());
        statement.setDate(7, new Date(guildData.getGuildCreationDate().getTime()));

        statement.executeUpdate();

        statement.close();

    }
    //Update guild setzt Gildenstatistiken auf das angegebene GuildsModel das zuvor per get Guild erhalten und angepasst wurde
    public void updateGuild(GuildsModel guildData) throws SQLException{
        PreparedStatement statement = connection.getConnection().prepareStatement(handler.getUpdateGuildData());

        statement.setString(1, guildData.getGuildID());
        statement.setString(2,guildData.getGuildOwnerName());
        statement.setInt(3,guildData.getGuildLevel());
        statement.setInt(4,guildData.getGuildMemberCount());
        statement.setInt(5,guildData.getGuildMaxMembers());
        statement.setDate(6,new Date(guildData.getGuildCreationDate().getTime()));
        statement.setString(7,guildData.getGuildName());

        statement.executeUpdate();
        statement.close();

    }
    //deleteGuild löscht eine Gilde mit angegebenen Namen aus der Datenbank
    public void deleteGuild(String guildName) throws SQLException{

        PreparedStatement statement = connection.getConnection().prepareStatement(handler.getDeleteGuild());

        statement.setString(1, guildName);
        statement.executeUpdate();
        statement.close();

    }


}
