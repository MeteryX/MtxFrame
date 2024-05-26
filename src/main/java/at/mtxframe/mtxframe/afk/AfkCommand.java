package at.mtxframe.mtxframe.afk;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AfkCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player){
            if (AFKManager.toggleAFKStatus(player)){
                player.sendMessage("Du bist jetzt AFK.");
            } else {
                player.sendMessage("Du bist jetzt nicht mehr AFK.");
            }

        }


        return true;
    }


}
