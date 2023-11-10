package at.mtxframe.mtxframe.handlers;

public class DbVaultsHandler {
    //guild Vaults
    private String createGuildVaultTable = "CREATE TABLE IF NOT EXISTS guild_vaults(guild_id varchar(50) PRIMARY KEY, content varchar(5000))";
    private String storeItemsInVault = "INSERT INTO guild_vaults (guild_id, content) VALUES (?,?)";

    private String getItemsFromVault = "SELECT content FROM guild_vaults WHERE guild_id = ?";

    private String updateGuildVault = "UPDATE guild_vaults SET content = ? WHERE guild_id = ?";
    private String removeVaultFromDataBase = "DELETE FROM guild_vaults WHERE guild_id = ?";

    public String getCreateGuildVaultTable() {
        return createGuildVaultTable;
    }

    public String getStoreItemsInVault() {
        return storeItemsInVault;
    }

    public String getGetItemsFromVault() {
        return getItemsFromVault;
    }

    public String getUpdateGuildVault() {
        return updateGuildVault;
    }

    public String getRemoveVaultFromDataBase() {
        return removeVaultFromDataBase;
    }



}
