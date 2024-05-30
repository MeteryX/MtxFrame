package at.mtxframe.mtxframe.customitems.items.tools;

import at.mtxframe.mtxframe.MtxFrame;
import at.mtxframe.mtxframe.colors.format.ColorFormat;
import at.mtxframe.mtxframe.customitems.*;
import at.mtxframe.mtxframe.customitems.items.ItemModel;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class DarkSword {
    private MtxFrame plugin = MtxFrame.getPlugin();
    private ColorFormat colorFormat = new ColorFormat(plugin.getColorAPI());

    public void createItem(Player player) {
            ItemStack customItem = new ItemStack(Material.NETHERITE_SWORD);
            String itemName = "Dunkles Schwert";
            String formattedItemName = colorFormat.formatText("&X" + itemName);

            ItemMeta itemMeta = customItem.getItemMeta();
            itemMeta.setDisplayName(formattedItemName);

            Boolean hasEffect = true;
            ItemEffectHandler itemEffect = new ItemEffectHandler();
            Boolean isStatTracker = false;
            ItemTrackingHandler trackedStat = new ItemTrackingHandler();
            Boolean hasRandomDrops = true;
            RandomDropHandler randomDropType = new RandomDropHandler();
            randomDropType.setRandomDropType(randomDropType.getDarkDrop());
            Integer dropChance = 1;
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            customItem.setItemMeta(itemMeta);

            persistentDataContainer.set(ItemKeys.CUSTOM_TEST, PersistentDataType.STRING, ItemKeys.CUSTOM_TEST.getKey());
            persistentDataContainer.set(ItemKeys.CI_RANDOM_DROP, PersistentDataType.STRING, ItemKeys.CI_RANDOM_DROP.getKey());
            persistentDataContainer.set(ItemKeys.CI_DROP_DARK, PersistentDataType.STRING, ItemKeys.CI_DROP_DARK.getKey());



            ItemModel customItemModel = new ItemModel(customItem, itemName, itemMeta, hasEffect, itemEffect, isStatTracker, trackedStat, hasRandomDrops, randomDropType, dropChance, persistentDataContainer);
            customItemModel.createItemTest(player, 1);

            // Debugging output
            player.sendMessage(ChatColor.GREEN + "Dark Sword has been created.");
            System.out.println("Dark Sword item: " + customItem.toString()); // Console debug message
            System.out.println("Dark Sword item meta: " + itemMeta.toString()); // Console debug message


    }



}
