package at.mtxframe.mtxframe.debugcommands;

import at.mtxframe.mtxframe.MtxFrame;
import at.mtxframe.mtxframe.colors.format.ColorFormat;
import at.mtxframe.mtxframe.customitems.*;
import at.mtxframe.mtxframe.customitems.items.ItemModel;
import at.mtxframe.mtxframe.customitems.items.tools.DarkAxe;
import at.mtxframe.mtxframe.customitems.items.tools.DarkPickaxe;
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

public class DebugCustomItem implements CommandExecutor {
    MtxFrame plugin = MtxFrame.getPlugin();
    private ColorFormat colorFormat = new ColorFormat(plugin.getColorAPI());
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;

            ItemStack customItem = new ItemStack(Material.DIAMOND_PICKAXE);
            String itemName = "Tracker Item Test";
            String formattedItemName = colorFormat.formatText("&Z" + itemName);


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
            customItem.setItemMeta(itemMeta);


            persistentDataContainer.set(ItemKeys.CUSTOM_TEST, PersistentDataType.STRING, ItemKeys.CUSTOM_TEST.getKey());
            persistentDataContainer.set(ItemKeys.CI_RANDOM_DROP,PersistentDataType.STRING,ItemKeys.CI_RANDOM_DROP.getKey());
            persistentDataContainer.set(ItemKeys.CI_TRACKER,PersistentDataType.STRING,ItemKeys.CI_TRACKER.getKey());
            persistentDataContainer.set(ItemKeys.CI_DROP_DARK,PersistentDataType.STRING,ItemKeys.CI_DROP_DARK.getKey());


            ItemModel customItemModel = new ItemModel(customItem,itemName,itemMeta,hasEffect,itemEffect,isStatTracker,trackedStat,hasRandomDrops,randomDrops,dropChance,persistentDataContainer);
            customItemModel.createItemTest(player, 1);

            //Todo: Debug items wo anders erstellen
            DarkSword darkSword = new DarkSword();
            darkSword.createItem(player);

            DarkAxe darkAxe = new DarkAxe();
            darkAxe.createItem(player);

            DarkPickaxe darkPickaxe = new DarkPickaxe();
            darkPickaxe.createItem(player);


        }


        return true;
    }
}
