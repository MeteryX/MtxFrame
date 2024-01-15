package at.mtxframe.mtxframe.gui;

import at.mtxframe.mtxframe.MtxFrame;
import at.mtxframe.mtxframe.database.DatabasePlayerStats;
import at.mtxframe.mtxframe.models.PlayerStatsModel;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class Tablist {
    DatabasePlayerStats database = new DatabasePlayerStats(MtxFrame.getPlugin());

    public void setTabList(Player player){
        player.setPlayerListHeader(ChatColor.AQUA + "EridiumRPG.net");
        try {
            PlayerStatsModel stats = database.findPlayerStatDataByUUID(String.valueOf(player.getUniqueId()));
            if (!stats.getPlayerGuild().equals("No Guild")) {
                player.setPlayerListName(ChatColor.GOLD + "[GILDE: " + stats.getPlayerGuild() + "] " + ChatColor.AQUA + " | " + ChatColor.WHITE + player.getDisplayName());
            }
            else if (!player.isOp()){
                player.setPlayerListName(ChatColor.GREEN + "[SOLO SPIELER] " + ChatColor.AQUA + " | " + ChatColor.WHITE + player.getName());
            } else if (player.isOp()) {
                player.setPlayerListName(ChatColor.DARK_PURPLE + "[Server Team] " + ChatColor.AQUA + " | " + ChatColor.WHITE + player.getName());
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        player.setPlayerListFooter(ChatColor.GREEN + "Viel Spaß beim spielen!");

    }


}
