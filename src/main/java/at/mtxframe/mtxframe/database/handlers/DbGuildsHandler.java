package at.mtxframe.mtxframe.database.handlers;

public class DbGuildsHandler {
    private String createGuildDataTable = "CREATE TABLE IF NOT EXISTS guild_list(guild_id varchar(36) primary key, guild_name varchar(50), guild_owner_name varchar(25), guild_level int, guild_member_count int, guild_member_limit int, guild_creation_date date)";
    private String searchGuildByName = "SELECT * FROM guild_list WHERE guild_name = ?";
    private String searchGuildByID = "SELECT * FROM guild_list WHERE guild_id = ?";
    private String createGuildData = "INSERT INTO guild_list(guild_id, guild_name, guild_owner_name, guild_level, guild_member_count, guild_member_limit, guild_creation_date) VALUES (?, ?, ?, ?, ?, ?, ?)"; // WICHTIG: + Daten die verwendet werden sollen
    private String updateGuildData = "UPDATE guild_list SET guild_id = ?, guild_owner_name = ?, guild_level = ?, guild_member_count = ?, guild_member_limit = ?, guild_creation_date = ? WHERE guild_name = ?";
    private String deleteGuild = "DELETE FROM guild_list WHERE guild_id = ?";


    public String getCreateGuildDataTable() {
        return createGuildDataTable;
    }

    public String getSearchGuildByName() {
        return searchGuildByName;
    }

    public String getSearchGuildByID() {
        return searchGuildByID;
    }

    public String getCreateGuildData() {
        return createGuildData;
    }

    public String getUpdateGuildData() {
        return updateGuildData;
    }

    public String getDeleteGuild() {
        return deleteGuild;
    }



}
