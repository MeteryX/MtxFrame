package at.mtxframe.mtxframe.crates;

import at.mtxframe.mtxframe.MtxFrame;
import at.mtxframe.mtxframe.customitems.items.tools.DarkAxe;
import at.mtxframe.mtxframe.customitems.items.tools.DarkPickaxe;
import at.mtxframe.mtxframe.customitems.items.tools.DarkSword;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class TestCrateCommand implements CommandExecutor {
    private final MtxFrame plugin;
    public HashMap<Integer,Hologram> hologramMap;
    String currentName;
    public TestCrateCommand(MtxFrame plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        hologramMap = new HashMap<Integer, Hologram>();
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can open crates!");
            return true;
        }
        Player player = (Player) sender;


        // Spawn chest ArmorStand representing the floating chest
        Location chestLocation = player.getLocation().add(player.getLocation().getDirection().multiply(8));
        chestLocation.setYaw(player.getLocation().getYaw());
        chestLocation.setPitch(player.getLocation().getPitch());
        chestLocation.add(0, 0, 0); // Adjust height
        ArmorStand chestStand = (ArmorStand) chestLocation.getWorld().spawnEntity(chestLocation, EntityType.ARMOR_STAND);
        chestStand.setGravity(false);
        chestStand.setVisible(false);
        chestStand.setHelmet(getPlayerHead(player));

        // Start slot machine animation
        ArmorStand itemStand = startCrateOpeningAnimation(player, chestStand);

        // Start task to update head and item position based on player movement
        startPositionUpdateTask(player, chestStand, itemStand);

        return true;
    }

    private ItemStack getPlayerHead(Player player) {
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setOwningPlayer(player);
        playerHead.setItemMeta(skullMeta);
        return playerHead;
    }

    private ArmorStand startCrateOpeningAnimation(Player player, ArmorStand chestStand) {
        List<ItemStack> lootTable = createLootTable();
        Random random = new Random();
        int stopIndex = random.nextInt(lootTable.size());

        // Spawn another ArmorStand for the rotating item
        Location itemLocation = chestStand.getLocation().add(0, 0.8, 0); // Adjust height for the item
        ArmorStand itemStand = (ArmorStand) itemLocation.getWorld().spawnEntity(itemLocation, EntityType.ARMOR_STAND);
        itemStand.setGravity(false);
        itemStand.setVisible(false);

        new BukkitRunnable() {
            int tick = 0;
            int animationDuration = 200; // Run for 200 ticks (10 seconds)
            int itemIndex = 0;
            boolean animationFinished = false;
            private ArmorStand currentNameStand;

            @Override
            public void run() {
                if (tick >= animationDuration) { // Run for 200 ticks (10 seconds)
                    if (!animationFinished) {
                        ItemStack wintItem = lootTable.get(stopIndex);
                        String winName = wintItem.hasItemMeta() && wintItem.getItemMeta().hasDisplayName()
                                ? wintItem.getItemMeta().getDisplayName()
                                : wintItem.getType().name(); // Use item type name if display name is not set
                        setCurrentName(winName);
                        Location nameLocation = itemStand.getLocation().clone().add(0, 0.6, 0); // Adjust Y coordinate as needed
                        currentNameStand = (ArmorStand) itemStand.getWorld().spawnEntity(nameLocation, EntityType.ARMOR_STAND);
                        currentNameStand.setCustomNameVisible(true);
                        currentNameStand.setCustomName(getCurrentName());
                        currentNameStand.setGravity(false);
                        currentNameStand.setVisible(false);

                        // Show the final item for 3 seconds before removing
                        itemStand.setHelmet(lootTable.get(stopIndex));
                        chestStand.getLocation().getWorld().spawnParticle(Particle.DRAGON_BREATH, chestStand.getLocation(), 80);
                        player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1F, 0.5F);

                        animationFinished = true;
                        return;
                    } else if (tick >= animationDuration + 60) { // Wait for 3 seconds (60 ticks) after animation finishes
                        ItemStack winItem = lootTable.get(stopIndex);
                        Material itemMaterial = winItem.getType();
                        if (itemMaterial.equals(Material.NETHERITE_AXE)){
                            DarkAxe darkAxe = new DarkAxe();
                            darkAxe.createItem(player);
                            currentNameStand.remove();
                        } else if (itemMaterial.equals(Material.NETHERITE_PICKAXE)) {
                            DarkPickaxe darkPickaxe = new DarkPickaxe();
                            darkPickaxe.createItem(player);
                            currentNameStand.remove();
                        } else if (itemMaterial.equals(Material.NETHERITE_SWORD)) {
                            DarkSword darkSword = new DarkSword();
                            darkSword.createItem(player);
                            currentNameStand.remove();
                        } else {
                            player.getInventory().addItem(lootTable.get(stopIndex));
                            currentNameStand.remove();
                        }

                        currentNameStand.remove();
                        itemStand.remove(); // Remove the rotating item
                        chestStand.remove(); // Remove the floating head

                        // Remove the current name stand if it exists
                        if (currentNameStand != null) {
                            currentNameStand.remove();
                            currentNameStand = null;
                        }

                        cancel();
                        return;
                    }
                }

                if (!animationFinished && tick % 10 == 0) { // Update item every 10 ticks (0.5 seconds) during animation
                    ItemStack currentItem = lootTable.get(itemIndex);
                    itemStand.setHelmet(lootTable.get(itemIndex));
                    String itemName = currentItem.hasItemMeta() && currentItem.getItemMeta().hasDisplayName()
                            ? currentItem.getItemMeta().getDisplayName()
                            : currentItem.getType().name(); // Use item type name if display name is not set
                    setCurrentName(itemName);
                    itemIndex = (itemIndex + 1) % lootTable.size(); // Loop through items
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 0.8F, 0.8F);

                    // Remove the current name stand if it exists
                    if (currentNameStand != null) {
                        currentNameStand.remove();
                    }

                    // Naming armorstand
                    Location nameLocation = itemStand.getLocation().clone().add(0, 0.6, 0); // Adjust Y coordinate as needed
                    currentNameStand = (ArmorStand) itemStand.getWorld().spawnEntity(nameLocation,EntityType.ARMOR_STAND);
                    currentNameStand.setCustomNameVisible(true);
                    currentNameStand.setCustomName(ChatColor.translateAlternateColorCodes('&', "&4" + getCurrentName()));
                    currentNameStand.setGravity(false);
                    currentNameStand.setVisible(false);
                }
                tick++;
            }
        }.runTaskTimer(plugin, 0, 1);

        return itemStand;
    }

    private List<ItemStack> createLootTable() {
        // Define the loot table with example items
        List<ItemStack> lootTable = new ArrayList<>();
        lootTable.add(new ItemStack(Material.DIAMOND));
        lootTable.add(new ItemStack(Material.GOLDEN_APPLE));
        lootTable.add(new ItemStack(Material.ENCHANTED_BOOK));
        lootTable.add(new ItemStack(Material.NETHERITE_AXE));
        lootTable.add(new ItemStack(Material.NETHERITE_SWORD));
        lootTable.add(new ItemStack(Material.NETHERITE_PICKAXE));
        lootTable.add(new ItemStack(Material.EMERALD_BLOCK));
        return lootTable;
    }

    private void startPositionUpdateTask(Player player, ArmorStand chestStand, ArmorStand itemStand) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isValid()) {
                    cancel(); // Stop the task if the player is no longer valid
                    return;
                }
                // Update chest stand position to follow the player's movement
                Location newChestLocation = player.getLocation().add(player.getLocation().getDirection().multiply(5));
                newChestLocation.setYaw(player.getLocation().getYaw());
                newChestLocation.setPitch(player.getLocation().getPitch());
                newChestLocation.add(0, 0, 0); // Adjust height
                chestStand.teleport(newChestLocation);

                Location newItemLocation = chestStand.getLocation().add(0, 0.5, 0); // Adjust height for the item
                itemStand.teleport(newItemLocation);
            }
        }.runTaskTimer(plugin, 0, 1); // Run every tick
    }

    //Holograms


    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }
}