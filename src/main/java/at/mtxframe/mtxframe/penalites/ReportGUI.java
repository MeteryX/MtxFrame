package at.mtxframe.mtxframe.penalites;

import at.mtxframe.mtxframe.MtxFrame;
import at.mtxframe.mtxframe.colors.format.ColorFormat;
import at.mtxframe.mtxframe.gui.MenuManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ReportGUI {
    MtxFrame plugin = MtxFrame.getPlugin();
    ColorFormat colorFormat = new ColorFormat(plugin.getColorAPI());
    MenuManager menuManager = new MenuManager();

    public void openMenu(Player player){
        String inventoryName = colorFormat.formatText("&2Report Menü");
        HashMap<Integer, ItemStack> inventoryItems = new HashMap<>();
        inventoryItems.put(0,menuManager.getCloseButton());



        menuManager.buildMenu(menuManager.getTypeChest(), inventoryName, inventoryItems, 27, player);

    }

}
