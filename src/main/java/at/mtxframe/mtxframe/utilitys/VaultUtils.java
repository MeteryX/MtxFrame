package at.mtxframe.mtxframe.utilitys;

import at.mtxframe.mtxframe.database.DatabaseVaults;
import at.mtxframe.mtxframe.database.handlers.DbVaultsHandler;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class VaultUtils {
    private static DatabaseVaults database;
    private static DbVaultsHandler handler = new DbVaultsHandler();
    //Liest den String aus einem P
    // ersistentData
    public static ArrayList<ItemStack> getItemsPlayerVault(Player player,String pluginName, String dataKey){
        PersistentDataContainer data = player.getPersistentDataContainer();
        ArrayList<ItemStack> items = new ArrayList<>();
        String encodedItems = data.get(new NamespacedKey(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(pluginName)),dataKey), PersistentDataType.STRING);
        assert encodedItems != null;
        if (!encodedItems.isEmpty()){
            byte[] rawData = Base64.getDecoder().decode(encodedItems);
            try {
                ByteArrayInputStream io = new ByteArrayInputStream(rawData);
                BukkitObjectInputStream in = new BukkitObjectInputStream(io);
                int itemCount = in.readInt();
                for (int i = 0; i<itemCount; i++){
                    items.add((ItemStack) in.readObject());
                }
                in.close();
            } catch (IOException | ClassNotFoundException ex){
                System.out.println(ex);
            }
        }
        return items;
    }
    //Speichert das Inventar eines Spielers als ByteArray toString ab und speichert den String im persistentDataContainer des Spielers im angegebenen Plugin
    public  static  void storeItemsPlayerVault(List<ItemStack> items, Player player,String pluginName, String dataKey) throws IOException {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (items.size() == 0){
            data.set(new NamespacedKey(pluginName,dataKey), PersistentDataType.STRING, "");
        }else {
            try {
                ByteArrayOutputStream io = new ByteArrayOutputStream();
                BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
                os.writeInt(items.size());
                for (int i = 0; i< items.size(); i++){
                    os.writeObject(items.get(i));
                }
                os.flush();
                //Beispiel für Liste im ByteArray
                //[3]-Anzahl der Loops
                //[1]-[3]- items im Array
                //[3] [item1][item2][item3] = 01010101 zB
                byte[] rawData = io.toByteArray();
                String encodedData = Base64.getEncoder().encodeToString(rawData);
                data.set(new NamespacedKey(pluginName,dataKey), PersistentDataType.STRING, encodedData);
                os.close();
            }catch(IOException ex){
                System.out.println(ex);
            }
        }
    }
    //Wandelt ein Inventar zu einem ItemstackArray um und lässt dabei Slots aus die kein Item beinhalten
    public ArrayList<ItemStack> inventoryToArray(Inventory inventory){
        ArrayList<ItemStack> prunedItems = new ArrayList<>();
        Arrays.stream(inventory.getContents())
                .filter(itemStack -> {
                    if (itemStack == null) {
                        return false;
                    }
                    return  true;
                }).forEach(itemStack -> prunedItems.add(itemStack));
        return prunedItems;
    }



    public static void storeItemsGuildVault(List<ItemStack> items, String guildID) throws IOException, SQLException {
        String gName = guildID;
        String encodedVault = "";
        if (items.size() == 0){
        }else {
            try {
                ByteArrayOutputStream io = new ByteArrayOutputStream();
                BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
                os.writeInt(items.size());
                for (int i = 0; i< items.size(); i++){
                    os.writeObject(items.get(i));
                }
                os.flush();
                //Beispiel für Liste im ByteArray
                //[3]-Anzahl der Loops
                //[1]-[3]- items im Array
                //[3] [item1][item2][item3] = 01010101 zB
                byte[] rawData = io.toByteArray();
                String encodedData = Base64.getEncoder().encodeToString(rawData);
                encodedVault = encodedData;
                database.updateGuildVault(gName,encodedVault, handler.getUpdateGuildVault());
                os.close();
            }catch(IOException ex){
                System.out.println(ex);
            }
        }

    }

    public static ArrayList<ItemStack> getItemsGuildVault(String guildID) throws SQLException {


        ArrayList<ItemStack> items = new ArrayList<>();

        String encodedItems = database.getGuildVault(guildID, handler.getGetItemsFromVault());

        if (!encodedItems.isEmpty()){
            byte[] rawData = Base64.getDecoder().decode(encodedItems);

            try {

                ByteArrayInputStream io = new ByteArrayInputStream(rawData);
                BukkitObjectInputStream in = new BukkitObjectInputStream(io);

                int itemCount = in.readInt();

                for (int i = 0; i<itemCount; i++){
                    items.add((ItemStack) in.readObject());
                }

                in.close();

            } catch (IOException | ClassNotFoundException ex){
                System.out.println(ex);
            }

        }
        return items;

    }





}
