package at.mtxframe.mtxframe.penalites.commands;

import at.mtxframe.mtxframe.penalites.ReportGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class ReportCommand implements TabExecutor {
    ReportGUI reportGUI = new ReportGUI();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage("Du musst einen Namen angeben: /report <Name>");
            }
            else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("report")) {
                    reportGUI.openMenu(player);
                }
            }

        }



        return false;
    }




    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return List.of();
    }
}
