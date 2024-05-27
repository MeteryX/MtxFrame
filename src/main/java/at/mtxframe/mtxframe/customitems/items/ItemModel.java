package at.mtxframe.mtxframe.customitems.items;

import at.mtxframe.mtxframe.customitems.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemModel implements Serializable {


    ItemStack customItem;
    String itemName;

    ItemMeta itemMeta;
    Boolean hasEffect;
    ItemEffectHandler itemEffect;
    Boolean isStatTracker;
    ItemTrackingHandler trackedStat;
    Boolean hasRandomDrops;
    RandomDropHandler randomDrops;
    Integer dropChance;
    PersistentDataContainer persistentDataContainer;





    public ItemModel(ItemStack customItem, String itemName, ItemMeta itemMeta, Boolean hasEffect, ItemEffectHandler itemEffect, Boolean isStatTracker, ItemTrackingHandler trackedStat, Boolean hasRandomDrops, RandomDropHandler randomDrops, Integer dropChance,PersistentDataContainer persistentDataContainer) {
        this.customItem = customItem;
        this.itemName = itemName;
        this.itemMeta = itemMeta;
        this.hasEffect = hasEffect;
        this.itemEffect = itemEffect;
        this.isStatTracker = isStatTracker;
        this.trackedStat = trackedStat;
        this.hasRandomDrops = hasRandomDrops;
        this.randomDrops = randomDrops;
        this.dropChance = dropChance;
        this.persistentDataContainer = persistentDataContainer;

    }

    public void createItemTest(Player player, Integer amount){

        persistentDataContainer.set(ItemKeys.CUSTOM_TEST, PersistentDataType.BOOLEAN, true);
        if (isStatTracker){
            persistentDataContainer.set(ItemKeys.CI_TRACKER_VALUE,PersistentDataType.INTEGER,0);
            String woodTracker = "CI_WOOD_TRACKER";
            String oreTracker = "CI_ORE_TRACKER";
            String farmTracker = "CI_FARM_TRACKER";
            String fishTracker = "CI_FISH_TRACKER";
            switch (trackedStat.getTrackingType()){

                case "CI_WOOD_TRACKER" :
                    persistentDataContainer.set(ItemKeys.CI_TRACKER,PersistentDataType.STRING,woodTracker);
                    customItem.setAmount(amount);
                    customItem.getItemMeta().setDisplayName(itemName);
                    LorePresets lorePresets = new LorePresets();
                    ArrayList<String> lore = lorePresets.getTrackerLore();
                    itemMeta.setLore(lore);

                    customItem.setAmount(amount);
                    customItem.getItemMeta().setDisplayName(itemName);
                    customItem.setItemMeta(itemMeta);
                    player.getInventory().addItem(customItem);

                    break;
                case "CI_ORE_TRACKER" :
                    persistentDataContainer.set(ItemKeys.CI_TRACKER,PersistentDataType.STRING,oreTracker);
                    customItem.setAmount(amount);
                    customItem.getItemMeta().setDisplayName(itemName);
                    lorePresets = new LorePresets();
                    lore = lorePresets.getTrackerLore();
                    itemMeta.setLore(lore);

                    customItem.setAmount(amount);
                    customItem.getItemMeta().setDisplayName(itemName);
                    customItem.setItemMeta(itemMeta);
                    player.getInventory().addItem(customItem);

                    break;
                case "CI_FARM_TRACKER":
                    persistentDataContainer.set(ItemKeys.CI_TRACKER,PersistentDataType.STRING,farmTracker);
                    customItem.setAmount(amount);
                    customItem.getItemMeta().setDisplayName(itemName);
                    lorePresets = new LorePresets();
                    lore = lorePresets.getTrackerLore();
                    itemMeta.setLore(lore);

                    customItem.setAmount(amount);
                    customItem.getItemMeta().setDisplayName(itemName);
                    customItem.setItemMeta(itemMeta);
                    player.getInventory().addItem(customItem);

                    break;
                case "CI_FISH_TRACKER":
                    persistentDataContainer.set(ItemKeys.CI_TRACKER,PersistentDataType.STRING,fishTracker);
                    customItem.setAmount(amount);
                    customItem.getItemMeta().setDisplayName(itemName);
                    lorePresets = new LorePresets();
                    lore = lorePresets.getTrackerLore();
                    itemMeta.setLore(lore);

                    customItem.setAmount(amount);
                    customItem.getItemMeta().setDisplayName(itemName);
                    customItem.setItemMeta(itemMeta);
                    player.getInventory().addItem(customItem);

                    break;
            }




        }
        /*
        customItem.setAmount(amount);
        customItem.getItemMeta().setDisplayName(itemName);
        customItem.setItemMeta(itemMeta);
        player.getInventory().addItem(customItem);
         */

        }



}
