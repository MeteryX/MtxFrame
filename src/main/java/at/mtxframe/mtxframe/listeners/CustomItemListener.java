package at.mtxframe.mtxframe.listeners;


import at.mtxframe.mtxframe.MtxFrame;
import at.mtxframe.mtxframe.customitems.ItemKeys;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CustomItemListener implements Listener {
    //Variablen
    private Map<Player, Location> playerTrailLocations = new HashMap<>();

    public HashMap<Location,Material> tempLocations = new HashMap<>();
    ArrayList<Location> locationArray = new ArrayList<>();
    private static final int RESET_DISTANCE = 5;
    MtxFrame plugin;
    public CustomItemListener(MtxFrame plugin){
        this.plugin = plugin;
    }

    //BlockBreakListener
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack usedItem = event.getPlayer().getInventory().getItemInMainHand();
        if (usedItem != null) {
            PersistentDataContainer persistentData = Objects.requireNonNull(usedItem.getItemMeta()).getPersistentDataContainer();
            if (persistentData != null) {
                if (persistentData.has(ItemKeys.CUSTOM_TEST, PersistentDataType.BOOLEAN)) {
                    //TODO: Random Drop Logik anpassen auf cases um es übersichtlicher zu machen

                    //Drops Listen usw
                    if (persistentData.has(ItemKeys.CI_RANDOM_DROP, PersistentDataType.STRING)) {
                        if (persistentData.has(ItemKeys.CI_RD_TESTDROPS, PersistentDataType.STRING)) {
                            ArrayList<ItemStack> debugDropList = new ArrayList<>();
                            ItemStack stoneDrop = new ItemStack(Material.STONE);
                            debugDropList.add(stoneDrop);
                            player.getInventory().addItem(stoneDrop);

                        }
                    }
                    //Tracker Werte
                    if (persistentData.has(ItemKeys.CI_TRACKER, PersistentDataType.STRING)) {

                        int currentTrackerValue = getStatValue(usedItem);
                        ItemMeta itemMeta = setStatValue(usedItem, currentTrackerValue);
                        ArrayList<String> lore = (ArrayList<String>) itemMeta.getLore();
                        assert lore != null;
                        lore.set(4, ChatColor.GRAY + "Abgebaut: " + ChatColor.GREEN + (currentTrackerValue));
                        if (itemMeta != null) {
                            usedItem.getItemMeta().getLore().clear();
                            itemMeta.setLore(lore);
                        }
                        usedItem.setItemMeta(itemMeta);

                    }

                }

            } else {
                player.sendMessage("Persistant Data on Item is null");
            }
        }

    }




        public int getStatValue (ItemStack itemStack){
            ItemMeta itemMeta = itemStack.getItemMeta();
            PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();

            return pdc.get(ItemKeys.CI_TRACKER_VALUE, PersistentDataType.INTEGER);
        }
        public ItemMeta setStatValue (ItemStack item,int value){
            ItemMeta itemMeta = item.getItemMeta();
            PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
            int currentStat = value;
            int incrementedStat = currentStat + 1;
            pdc.set(ItemKeys.CI_TRACKER_VALUE, PersistentDataType.INTEGER, incrementedStat);
            return itemMeta;

        }


    //andere Events


















    //TODO: Move Event einarbeiten
    /*
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getItemInHand() != null) {
            if (player.getItemInHand().getItemMeta().getPersistentDataContainer().has(ItemKeys.CI_FLOOR_EFFECT, PersistentDataType.STRING)) {

                // Überprüfen, ob der Spieler sich vertikal bewegt hat (nicht springt oder fällt)
                if (event.getFrom().getBlockY() != event.getTo().getBlockY()) {
                    return;
                }

                // Überprüfen, ob der Spieler genug Abstand bewegt hat, um die Spur zurückzusetzen
                if (playerTrailLocations.containsKey(player)) {
                    Location lastLocation = playerTrailLocations.get(player);
                    if (lastLocation.distance(event.getTo()) >= RESET_DISTANCE) {
                        resetPlayerTrail(player);

                        playerTrailLocations.put(player, event.getTo());
                    }
                } else {
                    Location playerLocation = player.getLocation();
                    World world = playerLocation.getWorld();
                    Location blockLocation = new Location(world, playerLocation.getBlockX(), playerLocation.getBlockY() - 1, playerLocation.getBlockZ());

                    playerTrailLocations.put(player, blockLocation);

                }

                // Block unter dem Spieler platzieren
                placeBlockUnderPlayer(player);
            }
        }

    }

    private void placeBlockUnderPlayer(Player player) {
        Location playerLocation = player.getLocation();
        World world = playerLocation.getWorld();
        Location blockLocation = new Location(world,playerLocation.getBlockX(),playerLocation.getBlockY()-1,playerLocation.getBlockZ());
        Material block = Material.OBSIDIAN;

        // Block unter dem Spieler setzen (z.B. GRASS_BLOCK)
        if (world.getBlockAt(blockLocation).getType() != Material.AIR && world.getBlockAt(blockLocation).getType() != Material.WATER && world.getBlockAt(blockLocation).getType() != Material.LAVA) {
            locationArray.add(blockLocation);
            tempLocations.put(blockLocation, world.getBlockAt(blockLocation).getType());



            world.getBlockAt(blockLocation).setType(block);

        }

    }

    private void resetPlayerTrail(Player player) {
        // Spur zurücksetzen, indem alle Blöcke der Spur entfernt werden
        int RESET_DISTANCE = 5;
        Location lastLocation = playerTrailLocations.get(player);
        Location currentPlayerLocation = player.getLocation();
        World world = player.getWorld();

        if (lastLocation != null && lastLocation.distance(currentPlayerLocation) >= RESET_DISTANCE) {

            int minX = Math.min(lastLocation.getBlockX(), currentPlayerLocation.getBlockX());
            int maxX = Math.max(lastLocation.getBlockX(), currentPlayerLocation.getBlockX());
            int minZ = Math.min(lastLocation.getBlockZ(), currentPlayerLocation.getBlockZ());
            int maxZ = Math.max(lastLocation.getBlockZ(), currentPlayerLocation.getBlockZ());

            for (int x = minX; x <= maxX; x++) {
                for (int z = minZ; z <= maxZ; z++) {
                    //for (int i = tempBlocks.size(); i > 0; i--) {
                    //for (Location location : tempLocations.keySet()){
                    for (int i = 0; i < locationArray.size(); i++) {
                        Material material = tempLocations.get(locationArray.get(i));

                        world.getBlockAt(locationArray.get(i)).setType(material);
                    }

                }
            }
            tempLocations.clear();
        }


    }
    */




}
