package at.mtxframe.mtxframe.customitems;

import at.mtxframe.mtxframe.ranking.Ranks;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Random;

public class DroplistDarkDrop {

    private static HashMap<Material, Integer> dropList = new HashMap<>();
    private static HashMap<Integer, Material> linkedMap = new HashMap<>();

    // Initialize and populate the drop list
    static {
        dropList.put(Material.BEACON, 1);
        dropList.put(Material.NETHERITE_SCRAP, 1);
        dropList.put(Material.CRYING_OBSIDIAN, 8);
        dropList.put(Material.NETHER_STAR, 1);
        dropList.put(Material.GILDED_BLACKSTONE, 12);
    }

    // Initialize and populate the linked map
    static {
        linkedMap.put(0, Material.BEACON);
        linkedMap.put(1, Material.NETHERITE_SCRAP);
        linkedMap.put(2, Material.CRYING_OBSIDIAN);
        linkedMap.put(3, Material.NETHER_STAR);
        linkedMap.put(4, Material.GILDED_BLACKSTONE);
    }

    public static ItemStack getDropItem() {
        Material material = getMaterial();
        ItemStack item = new ItemStack(material);
        item.setAmount(dropList.get(material));
        return item;
    }

    public static Material getMaterial() {
        Random random = new Random();
        int index = random.nextInt(linkedMap.size());
        return linkedMap.get(index);
    }


}
