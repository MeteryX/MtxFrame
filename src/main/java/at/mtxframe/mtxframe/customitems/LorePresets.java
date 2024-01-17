package at.mtxframe.mtxframe.customitems;

import org.bukkit.ChatColor;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class LorePresets {
    public ArrayList<String> trackerItemLore = new ArrayList<>();
    public ArrayList<String> getTrackerLore(){
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


}
