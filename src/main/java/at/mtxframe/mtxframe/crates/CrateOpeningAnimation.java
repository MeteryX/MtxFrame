package at.mtxframe.mtxframe.crates;

import at.mtxframe.mtxframe.MtxFrame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CrateOpeningAnimation {
    private final List<ItemStack> lootTable;
    private final Player player;
    private final List<ArmorStand> itemStands;
    private int currentIndex;
    MtxFrame plugin = MtxFrame.getPlugin();

    public CrateOpeningAnimation(Player player, List<ItemStack> lootTable) {
        this.player = player;
        this.lootTable = lootTable;
        this.itemStands = new ArrayList<>();
        this.currentIndex = 0;
    }

    public void startAnimation() {
        // Spawn ArmorStands representing items above player's head
        spawnItemStands();

        // Start rotating items
        Bukkit.getScheduler().runTaskTimer(plugin, this::rotateItems, 0L, 5L); // Adjust delay and interval as needed
    }

    private void spawnItemStands() {
        Location itemsLocation = player.getLocation().add(0, 2.5, 0); // Adjust height
        for (int i = 0; i < lootTable.size(); i++) {
            ItemStack item = lootTable.get(i);
            ArmorStand itemStand = player.getWorld().spawn(itemsLocation, ArmorStand.class);
            itemStand.setSmall(true);
            itemStand.setGravity(false);
            itemStand.setVisible(false);
            itemStand.setHelmet(item);
            itemStands.add(itemStand);
            itemsLocation.add(0, 0.5, 0); // Adjust height between items
        }
    }

    private void rotateItems() {
        // Update helmet of each ArmorStand to next item in loot table
        for (ArmorStand itemStand : itemStands) {
            itemStand.setHelmet(lootTable.get(currentIndex));
        }

        // Increment currentIndex and loop back to start if necessary
        currentIndex++;
        if (currentIndex >= lootTable.size()) {
            currentIndex = 0;
        }
    }
}
