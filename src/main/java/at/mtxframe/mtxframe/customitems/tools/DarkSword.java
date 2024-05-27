package at.mtxframe.mtxframe.customitems.tools;

import at.mtxframe.mtxframe.MtxFrame;
import at.mtxframe.mtxframe.colors.format.ColorFormat;
import at.mtxframe.mtxframe.customitems.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class DarkSword {
    private MtxFrame plugin = MtxFrame.getPlugin();
    private ColorFormat colorFormat = new ColorFormat(plugin.getColorAPI());

    public void createItem(Player player, ItemStack itemStack){
        ItemStack customItem = new ItemStack(Material.DIAMOND_PICKAXE);
        String itemName = "Schwert der Dunkelheit";
        String formattedItemName = colorFormat.formatText("&X" + itemName);


        ItemMeta itemMeta = customItem.getItemMeta();
        itemMeta.setDisplayName(formattedItemName);
        Boolean hasEffect = true;
        ItemEffectHandler itemEffect = new ItemEffectHandler();
        Boolean isStatTracker = true;
        ItemTrackingHandler trackedStat = new ItemTrackingHandler();
        trackedStat.setTrackingType(trackedStat.getWoodTracker());
        Boolean hasRandomDrops = true;
        RandomDropHandler randomDrops = new RandomDropHandler();
        Integer dropChance = 1;
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();


        persistentDataContainer.set(ItemKeys.CUSTOM_TEST, PersistentDataType.STRING, ItemKeys.CUSTOM_TEST.getKey());
        persistentDataContainer.set(ItemKeys.CI_RANDOM_DROP,PersistentDataType.STRING,ItemKeys.CI_RANDOM_DROP.getKey());
        persistentDataContainer.set(ItemKeys.CI_RD_TESTDROPS,PersistentDataType.STRING,ItemKeys.CI_RD_TESTDROPS.getKey());
        persistentDataContainer.set(ItemKeys.CI_TRACKER,PersistentDataType.STRING,ItemKeys.CI_TRACKER.getKey());


        ItemModel customItemModel = new ItemModel(customItem,itemName,itemMeta,hasEffect,itemEffect,isStatTracker,trackedStat,hasRandomDrops,randomDrops,dropChance,persistentDataContainer);
        customItemModel.createItemTest(player, 1);
    }

}
