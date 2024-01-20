package at.mtxframe.mtxframe.gui;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.lang.model.element.Name;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuManager {
    //Main Variables.
    /*
    Die Variablen im oberen Segment werden über die reusables gesetzt und sind für den Code verantwortlich.
    Diese Klasse soll die Erstellung von Menüs der anderen Plugins erleichtern, indem sie als Kernmodul Methoden
    bereitstellt, die ein gleichbleibendes handling gewährleisten.
    */

    String inventoryType = "";
    String inventoryName = "";
    HashMap<Integer,ItemStack> inventoryItems;
    Integer inventorySize = 0;
    Player player = null;
    ItemStack fillItem;
    ItemStack fillItemDark;
    ItemStack backButton;
    //Später gegen Heads tauschen
    ItemStack nextPage;
    ItemStack closeButton;
    ItemStack upgradesButton;
    ItemStack capacityButton;



    public void buildMenu(String inventoryType,String inventoryName,HashMap<Integer,ItemStack> inventoryItems, Integer inventorySize, Player player){
        this.inventoryType = inventoryType;
        this.inventoryName = inventoryName;
        this.inventoryItems = inventoryItems;
        this.inventorySize = inventorySize;
        fillItem = getFillItem();
        fillItemDark = getFillItemDark();
        //TODO: DEBUG LÖSCHEN
        player.sendMessage("Inventar build angefragt");
        Inventory inventory = getInventoryType(inventoryType,inventoryName,inventoryItems,inventorySize,player);

        //for (Map.Entry<Integer,ItemStack> entry : inventoryItems.entrySet()) {
           // Integer index = entry.getKey();
          //  ItemStack item = entry.getValue();

           // inventory.setItem(index,item);
        //}
        for (int i = 0; i < inventoryItems.size(); i++){
            if (inventoryItems.containsKey(i)){
                inventory.setItem(i,inventoryItems.get(i));
            }
        }
        for (int i = 0; i < inventory.getSize(); i++){
            if (inventory.getItem(i) == null){
                inventory.setItem(i,fillItem);
            }
        }


        player.openInventory(inventory);
        //TODO: Debug löschen
        player.sendMessage("Inventar geöffnet");

    }




    //Reusable Methoden um den workflow angenehmer zu machen
    private Inventory getInventoryType(String inventoryType,String inventoryName,HashMap<Integer,ItemStack> inventoryItems, Integer inventorySize, Player player){
        switch (inventoryType){
            case "CHEST" :
                InventoryType type = InventoryType.CHEST;
                Inventory inv = Bukkit.createInventory(player,inventorySize,inventoryName);
                for (Map.Entry<Integer,ItemStack> entry : inventoryItems.entrySet()) {
                    Integer index = entry.getKey();
                    ItemStack item = entry.getValue();

                    inv.setItem(index,item);
                }
                return inv;

            case "HOPPER" :
                InventoryType type2 = InventoryType.HOPPER;
                Inventory inv2 = Bukkit.createInventory(player,type2, inventoryName);
                for (Map.Entry<Integer,ItemStack> entry : inventoryItems.entrySet()) {
                    Integer index = entry.getKey();
                    ItemStack item = entry.getValue();

                    inv2.setItem(index,item);
                }
                return inv2;

            case "WORKBENCH" :
                InventoryType type3 = InventoryType.WORKBENCH;
                Inventory inv3 = Bukkit.createInventory(player,type3);
                for (Map.Entry<Integer,ItemStack> entry : inventoryItems.entrySet()) {
                    Integer index = entry.getKey();
                    ItemStack item = entry.getValue();

                    inv3.setItem(index,item);
                }
                return inv3;

            case "ANVIL" :
                InventoryType type4 = InventoryType.ANVIL;
                Inventory inv4 = Bukkit.createInventory(player,type4);
                for (Map.Entry<Integer,ItemStack> entry : inventoryItems.entrySet()) {
                    Integer index = entry.getKey();
                    ItemStack item = entry.getValue();

                    inv4.setItem(index,item);
                }
                return inv4;


        }

        return null;
    }



    //Main Variable Getters und Setters

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ItemStack getFillItem() {
        fillItem = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta fillMeta = fillItem.getItemMeta();
        fillMeta.setDisplayName(" ");
        fillItem.setItemMeta(fillMeta);
        return fillItem;
    }

    public ItemStack getFillItemDark() {
        fillItemDark = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta darkMeta = fillItemDark.getItemMeta();
        darkMeta.setDisplayName(" ");
        fillItemDark.setItemMeta(darkMeta);
        return fillItemDark;
    }

    public ItemStack getBackButton() {
        backButton  = new ItemStack(Material.SPRUCE_DOOR);
        ItemMeta backButtonMeta = backButton.getItemMeta();
        backButtonMeta.setDisplayName(ChatColor.GOLD + "Zurück");
        ArrayList<String> backLore = new ArrayList<>();
        backLore.add(ChatColor.GRAY + "Hier klicken, um in das vorherige");
        backLore.add(ChatColor.GRAY + "Menü zurück zu kommen.");
        backButtonMeta.setLore(backLore);
        backButton.setItemMeta(backButtonMeta);
        return backButton;
    }

    public ItemStack getNextPage() {
        nextPage = new ItemStack(Material.ARROW);
        ItemMeta nextMeta = nextPage.getItemMeta();
        nextMeta.setDisplayName(ChatColor.GOLD + "Nächste Seite");
        ArrayList<String> nextLore = new ArrayList<>();
        nextLore.add(ChatColor.GRAY + "Hier klicken, um zur nächsten");
        nextLore.add(ChatColor.GRAY + "Seite zu gelangen.");
        nextMeta.setLore(nextLore);
        nextPage.setItemMeta(nextMeta);
        return nextPage;
    }

    public ItemStack getCloseButton() {
        closeButton = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = closeButton.getItemMeta();
        closeMeta.setDisplayName(ChatColor.RED + "Menü schließen");
        ArrayList<String> closeLore = new ArrayList<>();
        closeLore.add(ChatColor.GRAY + "Hier klicken, um das Menü");
        closeLore.add(ChatColor.GRAY + "zu schließen.");
        closeMeta.setLore(closeLore);
        closeButton.setItemMeta(closeMeta);
        return closeButton;
    }

    public ItemStack getUpgradesButton() {
        upgradesButton = new ItemStack(Material.BEACON);
        ItemMeta upgradesMeta = upgradesButton.getItemMeta();
        upgradesMeta.setDisplayName(ChatColor.GREEN + "Kaufbare Upgrades");
        ArrayList<String> upgradesLore = new ArrayList<>();
        upgradesLore.add(ChatColor.GRAY + "Hier klicken, um alle ");
        upgradesLore.add(ChatColor.GRAY + "kaufbaren Upgrades ");
        upgradesLore.add(ChatColor.GRAY + "zu sehen.");
        upgradesMeta.setLore(upgradesLore);
        upgradesButton.setItemMeta(upgradesMeta);

        return upgradesButton;
    }

    public ItemStack getCapacityButton() {
        capacityButton = new ItemStack(Material.CHEST);
        ItemMeta capacityMeta = upgradesButton.getItemMeta();
        capacityMeta.setDisplayName(ChatColor.GREEN + "Rucksack Kapazität");
        ArrayList<String> capacityLore = new ArrayList<>();
        capacityLore.add(ChatColor.GRAY + "Die Kapazität des Rucksacks ");
        capacityLore.add(ChatColor.GRAY + "wird sich bald erhöhen lassen ");
        capacityLore.add(ChatColor.GRAY + "MtxFrame wird grade entwickelt");
        capacityMeta.setLore(capacityLore);
        capacityButton.setItemMeta(capacityMeta);

        return capacityButton;
    }

    //Reusable Variables
    String typeChest = "CHEST";
    String typeHopper = "HOPPER";
    String typeWorkBench = "WORKBENCH";
    String typeAnvil = "ANVIL";

    //Getters and Setters für die reusables

    public String getTypeChest() {
        return typeChest;
    }

    public String getTypeHopper() {
        return typeHopper;
    }

    public String getTypeWorkBench() {
        return typeWorkBench;
    }

    public String getTypeAnvil() {
        return typeAnvil;
    }


    //Create ItemStack eigene methode
    public ItemStack createItemStack(Material material, String itemName, ArrayList<String> itemLore){
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(itemName);
        itemMeta.setLore(itemLore);
        item.setItemMeta(itemMeta);

        return item;
    }


    //Ende

}
