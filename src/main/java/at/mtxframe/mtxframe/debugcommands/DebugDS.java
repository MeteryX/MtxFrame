package at.mtxframe.mtxframe.debugcommands;

import at.mtxframe.mtxframe.MtxFrame;
import at.mtxframe.mtxframe.colors.format.ColorFormat;
import at.mtxframe.mtxframe.customitems.ItemEffectHandler;
import at.mtxframe.mtxframe.customitems.ItemKeys;
import at.mtxframe.mtxframe.customitems.ItemTrackingHandler;
import at.mtxframe.mtxframe.customitems.RandomDropHandler;
import at.mtxframe.mtxframe.customitems.items.ItemModel;
import at.mtxframe.mtxframe.customitems.items.tools.DarkSword;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class DebugDS implements CommandExecutor{
        MtxFrame plugin = MtxFrame.getPlugin();
        private ColorFormat colorFormat = new ColorFormat(plugin.getColorAPI());
        @Override
        public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
            if (sender instanceof Player){
                Player player = (Player) sender;
                ItemStack customItem = new ItemStack(Material.DIAMOND_AXE);
                String itemName = "Dark Sword";
                String formattedItemName = colorFormat.formatText("&X" + itemName);

                ItemMeta itemMeta = customItem.getItemMeta();
                itemMeta.setDisplayName(formattedItemName);

                Boolean hasEffect = true;
                ItemEffectHandler itemEffect = new ItemEffectHandler();
                Boolean isStatTracker = false;
                ItemTrackingHandler trackedStat = new ItemTrackingHandler();
                Boolean hasRandomDrops = true;
                RandomDropHandler randomDrops = new RandomDropHandler();
                randomDrops.setRandomDropType(ItemKeys.CI_DROP_DARK.getKey());
                Integer dropChance = 1;
                PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
                customItem.setItemMeta(itemMeta);

                persistentDataContainer.set(ItemKeys.CUSTOM_TEST, PersistentDataType.STRING, ItemKeys.CUSTOM_TEST.getKey());
                persistentDataContainer.set(ItemKeys.CI_RANDOM_DROP, PersistentDataType.STRING, ItemKeys.CI_RANDOM_DROP.getKey());
                persistentDataContainer.set(ItemKeys.CI_DROP_DARK, PersistentDataType.STRING, ItemKeys.CI_DROP_DARK.getKey());



                ItemModel customItemModel = new ItemModel(customItem, itemName, itemMeta, hasEffect, itemEffect, isStatTracker, trackedStat, hasRandomDrops, randomDrops, dropChance, persistentDataContainer);
                customItemModel.createItemTest(player, 1);

            }


            return true;
        }


}
