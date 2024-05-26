package at.mtxframe.mtxframe.afk;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugIsAFK implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player){
            if (args.length == 0){
                if (AFKManager.isAFK(player)){
                    player.sendMessage("Du bist gerade AFK.");
                } else {
                    player.sendMessage(" Du bist gerade nicht AFK.");
                }

            } else {
                Player target = Bukkit.getPlayerExact(args[0]);

                if (AFKManager.isAFK(target)){
                    player.sendMessage(target.getDisplayName() + " ist gerade AFK.");
                } else {
                    player.sendMessage(target.getDisplayName() + " ist gerade nicht AFK.");
                }

            }

        }

        return true;
    }
}
