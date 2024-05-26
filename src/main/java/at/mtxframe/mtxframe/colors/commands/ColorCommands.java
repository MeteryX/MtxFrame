package at.mtxframe.mtxframe.colors.commands;

import at.mtxframe.mtxframe.MtxFrame;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ColorCommands implements CommandExecutor {

    private MtxFrame plugin;
    public ColorCommands(MtxFrame main) {
        this.plugin = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.hasPermission("colorapi.reload")) {
                    player.sendMessage("§cYou not have permission");
                    return true;
                }
            }
            plugin.reloadPlugin();
            sender.sendMessage("§a[ColorAPI] successfully reloaded.");
            return true;
        }
        return false;
    }

}
