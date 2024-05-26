package at.mtxframe.mtxframe.colors.signs;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignColorListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        // Optional: Check if the player has permission to use color codes on signs
        if (player.hasPermission("signs.color") || player.isOp()) {
            for (int i = 0; i < event.getLines().length; i++) {
                String line = event.getLine(i);
                if (line != null) {
                    // Translate color codes
                    line = ChatColor.translateAlternateColorCodes('&', line);
                    event.setLine(i, line);
                }
            }
        } else {
            // If you want to send a message when a player does not have permission
            player.sendMessage(ChatColor.RED + "You do not have permission to use color codes on signs.");
        }
    }
}
