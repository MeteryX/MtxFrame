package at.mtxframe.mtxframe.handlers;

public class DbClaimsHandler {
    //Player Claims
    //Creating a player_claims table
    private String createPlayerClaimTable = "CREATE TABLE IF NOT EXISTS player_claims(chunk_id varchar(50) primary key, uuid varchar(36), player_name varchar(50), searchkey varchar(10), claim_date date)";
    //Selecting the ChunkId from a player claim
    private String getChunkIDsPlayer = "SELECT chunk_id, uuid FROM player_claims WHERE searchkey = ?";
    //Adding a chunk id to player claims
    private String putChunkIDsPlayer = "INSERT INTO player_claims(chunk_id, uuid, player_name,searchkey,claim_date) VALUES (?,?,?,?,?)";
    //Get a claimed Chunks Owner name
    private String getChunkOwner = "SELECT player_name FROM player_claims WHERE chunk_id = ?";
    //Counting how many Player claims a user has allready
    private String countPlayerClaims = "SELECT COUNT(*) FROM player_claims WHERE player_name = ?";
    //Remove a players claim from the Database
    private String removePlayerClaim = "DELETE FROM player_claims WHERE chunk_id = ?";

    public String getCreatePlayerClaimTable() {
        return createPlayerClaimTable;
    }

    public String getGetChunkIDsPlayer() {
        return getChunkIDsPlayer;
    }

    public String getPutChunkIDsPlayer() {
        return putChunkIDsPlayer;
    }

    public String getGetChunkOwner() {
        return getChunkOwner;
    }

    public String getCountPlayerClaims() {
        return countPlayerClaims;
    }

    public String getRemovePlayerClaim() {
        return removePlayerClaim;
    }

    //Guild Claims
    private String createGuildClaimTable = "CREATE TABLE IF NOT EXISTS guild_claims(chunk_id varchar(50) primary key, guild_id varchar(36), guild_name varchar(50), claim_date date, searchkey varchar(10))";
    private String getGuildClaims = "SELECT chunk_id, guild_id FROM guild_claims WHERE searchkey = ?";
    private String putChunkIDsGuild = "INSERT INTO guild_claims(chunk_id,guild_id,guild_name,claim_date,searchkey) VALUES (?,?,?,?,?)";
    private String getClaimGuildName = "SELECT guild_name FROM player_claims WHERE chunk_id = ?";
    private String countGuildClaims = "SELECT COUNT(*) FROM guild_claims WHERE guild_id = ?";
    private String removeGuildClaim = "DELETE FROM guild_claims WHERE chunk_id = ?";

    public String getCreateGuildClaimTable() {
        return createGuildClaimTable;
    }

    public String getGetGuildClaims() {
        return getGuildClaims;
    }

    public String getPutChunkIDsGuild() {
        return putChunkIDsGuild;
    }

    public String getGetClaimGuildName() {
        return getClaimGuildName;
    }

    public String getCountGuildClaims() {
        return countGuildClaims;
    }

    public String getRemoveGuildClaim() {
        return removeGuildClaim;
    }



}
