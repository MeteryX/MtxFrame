package at.mtxframe.mtxframe.customitems;

import at.mtxframe.mtxframe.ranking.Ranks;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Random;

public class DroplistDarkDrop {

    private static HashMap<Material, Integer> dropList;
    private static HashMap<Integer, Material> linkedMap;

    public static HashMap<Material, Integer> getDropList() {
        dropList.put(Material.BEACON,1);
        dropList.put(Material.NETHERITE_SCRAP,1);
        dropList.put(Material.CRYING_OBSIDIAN,8);
        dropList.put(Material.NETHER_STAR,1);
        dropList.put(Material.GILDED_BLACKSTONE,12);

        return dropList;
    }


    public static HashMap<Integer,Material> getType() {
        linkedMap.put(0,Material.BEACON);
        linkedMap.put(1,Material.NETHERITE_SCRAP);
        linkedMap.put(2,Material.CRYING_OBSIDIAN);
        linkedMap.put(3,Material.NETHER_STAR);
        linkedMap.put(4,Material.GILDED_BLACKSTONE);

        return linkedMap;
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
        Material material = linkedMap.get(index);

        return material;
    }


}
