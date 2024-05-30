package at.mtxframe.mtxframe.customitems;

import at.mtxframe.mtxframe.MtxFrame;
import at.mtxframe.mtxframe.colors.format.ColorFormat;
import org.bukkit.ChatColor;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class LorePresets {
    MtxFrame plugin = MtxFrame.getPlugin();
    ColorFormat colorFormat = new ColorFormat(MtxFrame.getPlugin().getColorAPI());

    public ArrayList<String> getTrackerLore(){
        ArrayList<String> trackerItemLore = new ArrayList<>();
        if (trackerItemLore.isEmpty()){
            for (int i = 0; i < 5; i++){
                trackerItemLore.add("");
            }
        }
        trackerItemLore.set(0,ChatColor.GRAY + "Dieses Item trackt deine Statistiken für abgebaute Items!");
        trackerItemLore.set(1,ChatColor.GRAY + "Die Custom Items befinden sich gerade in Entwicklung");
        trackerItemLore.set(2,ChatColor.GRAY + "");
        trackerItemLore.set(3,ChatColor.GRAY + "");

        trackerItemLore.set(4,ChatColor.GRAY + "Abgebaut: " + 0);
        return trackerItemLore;
    }


    public ArrayList<String> getDarkDropLore(){
        ArrayList<String> darkDropLore = new ArrayList<>();
        if (darkDropLore.isEmpty()){
            for (int i = 0; i < 5; i++){
                darkDropLore.add("");
            }
        }
        darkDropLore.set(0,ChatColor.GRAY + "Dieses Item ist mit dunkler Magie gefüllt!");
        darkDropLore.set(1,ChatColor.GRAY + "Bei der Nutzung hast du eine kleine Chance");
        darkDropLore.set(2,ChatColor.GRAY + "auf Item Drops aus der");
        darkDropLore.set(3,ChatColor.translateAlternateColorCodes('&', colorFormat.formatText("&Xdunklen Dimension ")));

        return darkDropLore;
    }



}
